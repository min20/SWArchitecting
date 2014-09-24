package basicServer;

import java.io.IOException;
import java.net.ServerSocket;

import eventHandler.EventHandler;

/**
 * @class Reactor
 * @date 2014-09-24
 * @author min
 * @brief dispatcher 관리, header와 대응되는 event handler를 관리.  
 */
public class Reactor {
	private ServerSocket serverSocket;
	private HandleMap handleMap;

	public Reactor(int port) {
		handleMap = new HandleMap();

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer() {
		// ThreadPerDispatcher dispatcher = new ThreadPerDispatcher();
		ThreadPoolDispatcher dispatcher = new ThreadPoolDispatcher();
		dispatcher.dispatch(serverSocket, handleMap);
	}

	public void registerHandler(EventHandler handler) {
		handleMap.put(handler.getHandler(), handler);
	}

	public void removeHandler(EventHandler handler) {
		handleMap.remove(handler.getHandler());
	}

}
