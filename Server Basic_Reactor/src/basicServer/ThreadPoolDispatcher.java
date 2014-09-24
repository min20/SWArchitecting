package basicServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPoolDispatcher implements Dispatcher {

	static final String NUM_THREADS = "8";
	static final String THREAD_PROP = "Threads";

	private int numThreads;

	public ThreadPoolDispatcher() {
		// 시스템 정보의 스레드 개수를 가져오거나, 없으면 우리가 설정한 값을 사용함
		numThreads = Integer.parseInt(System.getProperty(THREAD_PROP, NUM_THREADS));
	}

	@Override
	public void dispatch(final ServerSocket serverSocket, final HandleMap handleMap) {
		for (int idx_thread = 0; idx_thread < (numThreads - 1); idx_thread++) {
			Thread thread = new Thread() {
				public void run() {
					dispatchLoop(serverSocket, handleMap);
				}
			};
			thread.start();
			
			System.out.println("CREATE / START Thread: " + thread.getName());
		}
		System.out.println("INTERATIVE SERVER STARTED IN MAIN THREAD: " + Thread.currentThread().getName());
		
		dispatchLoop(serverSocket, handleMap);
	}

	private void dispatchLoop(ServerSocket serverSocket, HandleMap handleMap) {
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
