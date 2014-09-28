package reactor;

import java.io.IOException;
import java.net.ServerSocket;

/**
* @brief 리엑터 방식 서버 클래스
* @details 리엑터 방식으로 구현된 서버 구동 클래스로
* 헤더와 핸들러를 관리하는 HandleMap과 ServerSocket을 관리한다.
* 이 클래스는 ThreadPer와 ThreadPool방식을 변경하여 적용 가능하다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public class Reactor {
    private ServerSocket serverSocket;
    private HandleMap handleMap;
    
    /**
     * @brief 리엑터 생성자
	 * @details 포트번호를 인자로 받아 서버 소켓을 생성하고
	 * HandleMap을 하나 만들어 둔다.
     * @param port
     */
    public Reactor(int port) {
    	handleMap = new HandleMap();
        try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			
			serverStarter.ServerInitializer.logger.error(e.getMessage());
		}
    }
    
    /**
     * @brief 서버 실행
	 * @details Dispatcher를 ThreadPerDispatcher로 할지 ThreadPoolDispatcher로 할지에 따라
	 * 스레드 운용 정책을 정할 수 있다.
     */
    public void startServer() {
    	
		Dispatcher dispatcher = new ThreadPerDispatcher();
		//Dispatcher dispatcher = new ThreadPoolDispatcher();
    	dispatcher.dispatch(serverSocket, handleMap);
    	
    }
    
    /**
     * @brief HandleMap에 EventHandler등록
	 * @details 헤더와 이벤트 핸들러를 각각 인자로 받아 HandleMap에 등록한다.
     * @param header
     * @param handler
     */
    public void registerHandler(String header, EventHandler handler) {
    	handleMap.put(header, handler);
    }
    
    /**
     * @brief HandleMap에 EventHandler등록
	 * @details EventHandler러를 받아 HandleMap에 등록한다. 헤더는 EventHandler에 내재되어있는 헤더값을 사용한다.
     * @param handler
     */
    public void registerHandler(EventHandler handler) {
    	handleMap.put(handler.getHandler(), handler);
    }

    /**
     * @brief HandleMap에있는 EventHandler 등록 제거
	 * @details 동일한 이벤트 핸들러가 있으면 그 핸들러를 HandleMap에서 삭제한다.
     * @param handler
     */
    public void removeHandler(EventHandler handler) {
    	handleMap.remove(handler.getHandler());
    }
    
}

