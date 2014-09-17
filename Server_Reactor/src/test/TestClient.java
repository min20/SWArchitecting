package test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import basicServer.Main;

public class TestClient {

	public static void main(String[] args) {
		System.out.println("TestClient START");

		try {
			StringBuffer message = new StringBuffer();

			Socket socket = new Socket("127.0.0.1", Main.PORT);
			OutputStream out = socket.getOutputStream();

			message.append("0x5001|");
			message.append("뽀삐");
			message.append("|21");

			out.write(message.toString().getBytes());

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
