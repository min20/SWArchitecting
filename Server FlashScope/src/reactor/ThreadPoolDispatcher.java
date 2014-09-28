package reactor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
* @brief ThreadPoolDispatcher
* @details 스레드 풀을 생성하여 스레드를 미리 생성하여 속도를 절약하고
* 스레드가 동작하는 개수를 제한할 수 있다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public class ThreadPoolDispatcher implements Dispatcher {
	
    static final String NUMTHREADS = "8";
    static final String THREADPROP = "Threads";

    private int numThreads;

    /**
     * @brief ThreadPoolDispatcher 생성자
	 * @details 시스템 정보의 스레드 수를 가져오거나 기본 값을 가져와 사용한다.
     */
    public ThreadPoolDispatcher() {
        numThreads = Integer.parseInt(System.getProperty(THREADPROP, NUMTHREADS));
    }
    
    

    /**
     * @brief ThreadPool 생성
	 * @details numThreads-1 수만큼 스레드를 생성하고 블록상태로 대기한다.
	 * @param serverSocket 
	 * @param handleMap 
     */
	public void dispatch(final ServerSocket serverSocket, final HandleMap handleMap) {
        for (int i = 0; i < (numThreads - 1); i++) {
            Thread thread = new Thread() {
                public void run() {
                    dispatchLoop(serverSocket, handleMap);
                }
            };
            thread.start();
            serverStarter.ServerInitializer.logger.fatal("Created and started Thread = " + thread.getName());
        }
        
        serverStarter.ServerInitializer.logger.fatal("Iterative server starting in main thread " +
                Thread.currentThread().getName());
        
        dispatchLoop(serverSocket, handleMap);
	}
	
    /**
     * @brief 연결 데이터 처리
	 * @details 스레드풀에서 만들어진 스레드들이 이 함수의 accept()의 블록을 물고 대기를 하고 있다.
	 * 접속이 들어오면 소켓을 생성후에 Demultiplexer가 일을 할수있도록 전달한다.
	 * @param serverSocket 
	 * @param handleMap 
     */
    private void dispatchLoop(ServerSocket serverSocket, HandleMap handleMap) {
    	
    	while( true ) {
    		
			try {
				Socket socket = serverSocket.accept();
				Runnable demultiplexer = new Demultiplexer(socket, handleMap);
	        	demultiplexer.run();
			} catch (IOException e) {
				e.printStackTrace();
				serverStarter.ServerInitializer.logger.error(e.getMessage());
			}
        	
        }
    }
}
