package basicServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @class ThreadPerDispatcher
 * @date 2014-09-24
 * @author min
 * @brief single thread dispatcher. Demultiplexer를 실행시킨다.
 */
public class ThreadPerDispatcher implements Dispatcher {

	public void dispatch(ServerSocket serverSocket, HandleMap handleMap) {
		while (true) {
			try {
				Socket socket = serverSocket.accept();

				Demultiplexer demultiplexer = new Demultiplexer(socket, handleMap);
				Thread thread = new Thread(demultiplexer);

				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
