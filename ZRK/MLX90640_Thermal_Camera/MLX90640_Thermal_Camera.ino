#include "Arduino.h"
#include <Wire.h>
#include<WiFi.h>
#include "MLX90640_API.h"
#include "MLX90640_I2C_Driver.h"
const char* ssid ="Caffrey";
const char*password =  "Lgl3317768";
#define EMMISIVITY 0.95
#define TA_SHIFT 8 
WiFiClient client;
paramsMLX90640 mlx90640;
const byte MLX90640_address = 0x33; //Default 7-bit unshifted address of the MLX90640
static float tempValues[32 * 24];
void connectToNetwork(){
  WiFi.begin(ssid,password);
  while (WiFi.status()!= WL_CONNECTED) {
    delay(1000);
    Serial.println("Establishingconnection to WiFi..");
  }
  Serial.println("Connectedto network");
}
void setup() {
  Serial.begin(115200);
  connectToNetwork();
  Serial.println(WiFi.macAddress());
  Serial.println(WiFi.localIP());

  if (!client.connect("192.168.43.45", 10001))//连接的IP地址和
    {
        Serial.println("Connection to host failed");
        delay(1000);
        return;
    }
  delay(1000);  
  Wire.begin();
  Wire.setClock(3400000); 
  Wire.beginTransmission((uint8_t)MLX90640_address);
  if (Wire.endTransmission() != 0) {
    Serial.println("MLX90640 not detected at default I2C address. Starting scan the device addr...");
    Device_Scan();
//    while(1);
  }
  else {
    Serial.println("MLX90640 online!");
  }
  int status;
  uint16_t eeMLX90640[832];
  status = MLX90640_DumpEE(MLX90640_address, eeMLX90640);
  if (status != 0) Serial.println("Failed to load system parameters");
  status = MLX90640_ExtractParameters(eeMLX90640, &mlx90640);
  if (status != 0) Serial.println("Parameter extraction failed");
  MLX90640_SetRefreshRate(MLX90640_address, 0x06); 
  Wire.setClock(800000);
}

void loop(void) {
  readTempValues();
  //delay(2000);
}

void readTempValues() {
  for (byte x = 0 ; x < 2 ; x++) 
  {
    uint16_t mlx90640Frame[834];
    int status = MLX90640_GetFrameData(MLX90640_address, mlx90640Frame);
    if (status < 0)
    {
      Serial.print("GetFrame Error: ");
      Serial.println(status);
    }

    float vdd = MLX90640_GetVdd(mlx90640Frame, &mlx90640);
    float Ta = MLX90640_GetTa(mlx90640Frame, &mlx90640);

    float tr = Ta - TA_SHIFT; 

    MLX90640_CalculateTo(mlx90640Frame, &mlx90640, EMMISIVITY, tr, tempValues);
  }
  String str = "begin";
  for (int i = 0; i < 768; i++) {
//    if (((i % 32) == 0) && (i != 0)) {
//      Serial.println(" ");
//    }
   //Serial.print((int)tempValues[i]);
   // Serial.print(" ");
     str = str+",";
    str = str+tempValues[i];
      
    
      
  }
  //Serial.println(str);
  client.println(str);
  
}

void Device_Scan() {
  byte error, address;
  int nDevices;
  Serial.println("Scanning...");
  nDevices = 0;
  for (address = 1; address < 127; address++ )
  {
    Wire.beginTransmission(address);
    error = Wire.endTransmission();
    if (error == 0)
    {
      Serial.print("I2C device found at address 0x");
      if (address < 16)
        Serial.print("0");
      Serial.print(address, HEX);
      Serial.println("  !");
      nDevices++;
    }
    else if (error == 4)
    {
      Serial.print("Unknow error at address 0x");
      if (address < 16)
        Serial.print("0");
      Serial.println(address, HEX);
    }
  }
  if (nDevices == 0)
    Serial.println("No I2C devices found");
  else
    Serial.println("done");
}
