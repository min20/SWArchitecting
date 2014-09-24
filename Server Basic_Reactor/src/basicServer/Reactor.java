package basicServer;

import java.io.IOException;
import java.net.ServerSocket;

import eventHandler.EventHandler;

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
