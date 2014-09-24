package test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TestClient {
	
	private static final String ADDRESS = "127.0.0.1";
	private static final int PORT = 5000;

	public static void main(String[] args) {
		System.out.println("TestClient START");

		try {
			StringBuffer message_5001 = new StringBuffer();
			Socket socket_5001 = new Socket(ADDRESS, PORT);
			OutputStream out_5001 = socket_5001.getOutputStream();
			message_5001.append("0x5001|");
			message_5001.append("뽀삐|");
			message_5001.append("21");
			out_5001.write(message_5001.toString().getBytes());
			socket_5001.close();
			
			StringBuffer message_6001 = new StringBuffer();
			Socket socket_6001 = new Socket(ADDRESS, PORT);
			OutputStream out_6001 = socket_6001.getOutputStream();
			message_6001.append("0x6001|");
			message_6001.append("min|");
			message_6001.append("password|");
			message_6001.append("뽀삐|");
			message_6001.append("21|");
			message_6001.append("male");
			out_6001.write(message_6001.toString().getBytes());
			socket_6001.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
