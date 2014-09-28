package reactor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
* @brief ThreadPerDispatcher
* @details 한 연결(Socket)에 스레드를 하나씩 할당하여 Demultiplexing하도록 전달한다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public class ThreadPerDispatcher implements Dispatcher {
	
    /**
     * @brief ThreadPer 생성
	 * @details 접속이 들어오면 소켓을 생성 후에 각각의 소켓에 Demultiplexer를 스레드로 띄워
	 * Demultiplexer에게 전달한다.
	 * @param serverSocket 
	 * @param handleMap 
     */
	public void dispatch(ServerSocket serverSocket, HandleMap handleMap) {
		while( true ) {
			try {
				Socket socket = serverSocket.accept();
				
				Runnable demultiplexer = new Demultiplexer(socket, handleMap);
	            Thread thread = new Thread(demultiplexer);
	            thread.start();
	            
			} catch (IOException e) {
				e.printStackTrace();
				serverStarter.ServerInitializer.logger.error(e.getMessage());
			}
		}
	}
}
