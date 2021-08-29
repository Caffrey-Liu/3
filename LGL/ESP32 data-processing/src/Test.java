
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			socket = new Socket("127.0.0.1", 10001);
			out = new java.io.PrintWriter(socket.getOutputStream());
			// 获得输入流
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String line = "hello";
			out.write(line + "\r\n");
			out.flush();

			line = "haha";
			out.write(line + "\r\n");
			out.flush();

			// 向服务器端输入bye，断开表示断开连接
			out.write("bye" + "\r\n");
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(in);
			close(out);
			close(socket);
		}
	}

	public static void close(Closeable inout) {
		if (inout != null) {
			try {
				inout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
