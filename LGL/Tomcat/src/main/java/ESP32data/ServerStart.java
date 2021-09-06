package ESP32data;

import ESP32data.Server.*;
public class ServerStart {
    public static void main(String[] args) {
        MyServer Server = new MyServer();
        Server.startListen(10001);
    }
}
