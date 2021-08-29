public class ServerTest {
    public static void main(String[] args) {
        MyServer Server = new MyServer();
        Server.startListen(10001);
    }
}
