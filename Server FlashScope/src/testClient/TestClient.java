package testClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
* @brief 테스트 클라이언트 구동 클래스
* @details 서버 포트 설정과 HandlerList.xml의 데이터를 읽어들여
* JAR파일에 있는 EventHandler클래스를 불러와 HandleMap에 등록한다.
* 현재는 Reactor방식의 서버를 구동시킨다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public class TestClient {

    /**
     * @brief 테스트 소켓 메시지 전송
	 * @details 두 핸들러에 대한 0x5001, 0x6001에 대한 메시지를 반복적으로(100ms) 전송한다.
	 * Demultiplexer에게 전달한다.
     */
	public static void main(String[] args) {
		System.out.println("Client ON");
		while (true) {
			try {
				String message;
	
				Socket socket = new Socket("127.0.0.1", 5000);
				OutputStream out = socket.getOutputStream();
				message = "0x5001|홍길동|22";
				out.write(message.getBytes());
				socket.close();
				
				Socket socket2 = new Socket("127.0.0.1", 5000);
				OutputStream out2 = socket2.getOutputStream();
				message = "0x6001|hong|1234|홍길동|22|남성";
				out2.write(message.getBytes());
				socket2.close();
				
				Thread.sleep(100);
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
