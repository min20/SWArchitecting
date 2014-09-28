package reactor;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
* @brief 헤더값에 따른 이벤트 핸들러 분류
* @details 소켓으로 넘어온 메시지를 헤더사이즈만큼 읽어서 헤더에 맞는 이벤트 핸들러를 실행시킨다.
* 스레드로 구성되어있다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public class Demultiplexer implements Runnable {

	private final int HEADER_SIZE = 6;
	
	private Socket socket;
	private HandleMap handleMap;
	
    /**
     * @brief Demultiplexer 생성자
	 * @details 생성자로 소켓과 핸들맵을 미리 받아온다.
     * @param socket
     * @param handleMap
     */
	public Demultiplexer(Socket socket, HandleMap handleMap) {
		this.socket = socket;
		this.handleMap = handleMap;
	}
	
	/**
     * @brief Demultiplexer 스레드
	 * @details 소켓에서 헤더사이즈 만큼 읽어와서
	 * 그 헤더를 이용하여 handleMap에서 EventHandler를 찾아와 실행한다. 
     * @param socket
     * @param handleMap
     */
	@Override
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			
			byte[] buffer = new byte[HEADER_SIZE];
			inputStream.read(buffer);
			String header = new String(buffer);
			//ServerInitializer.logger.fatal("MESSAGE:"+header);
			handleMap.get(header).handleEvent(inputStream);
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			serverStarter.ServerInitializer.logger.error(e.getMessage());
		}
	}

}
