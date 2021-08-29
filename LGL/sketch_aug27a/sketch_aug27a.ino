#include<WiFi.h>
const char* ssid ="Caffrey";
const char*password =  "Lgl3317768";
WiFiClient client;

 
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

  if (!client.connect("192.168.31.248", 10001))//连接的IP地址和
    {
        Serial.println("Connection to host failed");
        delay(1000);
        return;
    }
  delay(1000);  
  
  
  //WiFi.disconnect(true);
}
void loop() {
 for (int i = 0; i<= 20; i++){
  client.println(i);
  client.flush(); 
  }
}
