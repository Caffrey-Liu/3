#include <WiFi.h>
#include <ESPmDNS.h>
#include <WebServer.h>
#include <WiFiMulti.h>
#include "Arduino.h"
#include <Wire.h>
 #include "MLX90640_API.h"
#include "MLX90640_I2C_Driver.h"
#define EMMISIVITY 0.95
#define TA_SHIFT 8 
WiFiClient client;
paramsMLX90640 mlx90640;
const byte MLX90640_address = 0x33; //Default 7-bit unshifted address of the MLX90640
static float tempValues[32 * 24];
const char* AP_SSID  = "ESP32_Config"; //热点名称
const char* AP_PASS  = "12345678";  //密码
#define ROOT_HTML  "<!DOCTYPE html><html><head><title>ESP32wifi配置</title><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"></head><style type=\"text/css\">.input{display: block; margin-top: 10px;}.input span{width: 100px; float: left; float: left; height: 36px; line-height: 36px;}.input input{height: 30px;width: 200px;}.btn{width: 120px; height: 35px; background-color: #000000; border:0px; color:#ffffff; margin-top:15px; margin-left:100px;}</style><body><form method=\"GET\" action=\"connect\"><label class=\"input\"><span>WiFi SSID</span><input type=\"text\" name=\"ssid\"></label><label class=\"input\"><span>WiFi PASS</span><input type=\"text\"  name=\"pass\"></label><input class=\"btn\" type=\"submit\" name=\"submit\" value=\"Submie\"></form></body></html>"
WebServer server(80);
WiFiMulti wifiMulti;
uint8_t resr_count_down = 120;//重启倒计时s
TimerHandle_t xTimer_rest;
void restCallback(TimerHandle_t xTimer );
void setup() {
          Serial.begin(115200);
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
          WiFi.mode(WIFI_AP);//配置为AP模式
          boolean result = WiFi.softAP(AP_SSID, AP_PASS);//开启WIFI热点
          if (result)
          {
            IPAddress myIP = WiFi.softAPIP();
            //打印相关信息
            Serial.println("");
            Serial.print("Soft-AP IP address = ");
            Serial.println(myIP);
            Serial.println(String("MAC address = ")  + WiFi.softAPmacAddress().c_str());
            Serial.println("waiting ...");
            xTimer_rest = xTimerCreate("xTimer_rest", 1000 / portTICK_PERIOD_MS, pdTRUE, ( void * ) 0, restCallback);
            xTimerStart( xTimer_rest, 0 );  //开启定时器
          } else {  //开启热点失败
            Serial.println("WiFiAP Failed");
            delay(3000);
            ESP.restart();  //复位esp32
          }
          if (MDNS.begin("esp32")) {
            Serial.println("MDNS responder started");
          }
          //首页
          server.on("/", []() {
            server.send(200, "text/html", ROOT_HTML);
          });
         //连接
          server.on("/connect", []() {
            server.send(200, "text/html", "<html><body><h1>successd,conning...</h1></body></html>");
            WiFi.softAPdisconnect(true);
            //获取输入的WIFI账户和密码
            String ssid = server.arg("ssid");
            String pass = server.arg("pass");
            Serial.println("WiFi Connect SSID:" + ssid + "  PASS:" + pass);
            //设置为STA模式并连接WIFI
            WiFi.mode(WIFI_STA);
            WiFi.begin(ssid.c_str(), pass.c_str());
            resr_count_down = 120;
            xTimerStop(xTimer_rest, 0);
            uint8_t Connect_time = 0; //用于连接计时，如果长时间连接不成功，复位设备
            while (WiFi.status() != WL_CONNECTED) {  //等待WIFI连接成功
              delay(500);
              Serial.print(".");
              Connect_time ++;
              if (Connect_time > 80) {  //长时间连接不上，复位设备
                Serial.println("Connection timeout, check input is correct or try again later!");
                delay(3000);
                ESP.restart();
              }
           }
           Serial.println("success connection!");
          });
          server.begin();
          
         
  
        }
void loop() {
          server.handleClient();
          if (!client.connect("47.100.63.226", 10001))//连接的IP地址和端口
    {
        Serial.println("Connection to host failed");
        delay(1000);
        return;
    }
          while (WiFi.status() == WL_CONNECTED) {
            //WIFI已连接 Serial.println("daozhele");
          
            readTempValues();
          }
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
  
  String str="b";
  for (int i = 0; i < 768; i++) {
    str = str+(int)(tempValues[i] *100);
    }
    client.println(str);
    Serial.println(str);
  
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
        void restCallback(TimerHandle_t xTimer ) {  //长时间不访问WIFI Config 将复位设备
          resr_count_down --;
          Serial.print("resr_count_down: ");
          Serial.println(resr_count_down);
          if (resr_count_down < 1) {
            ESP.restart();
          }
        }
        
