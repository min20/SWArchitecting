package reactor;

import java.net.ServerSocket;

/**
* @brief Dispatcher 인터페이스 정의
* @details ServerSocket과 HandleMap을 전달하는 dispatch()함수를 정의
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public interface Dispatcher {
	public void dispatch(ServerSocket serverSocket, HandleMap handlers);
}
