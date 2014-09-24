package basicServer;

import java.net.ServerSocket;

/**
 * @interface Dispatcher
 * @date 2014-09-24
 * @author min
 * @brief dispatcher의 DI를 위한 인터페이스
 */
public interface Dispatcher {
	public void dispatch(ServerSocket serverSocket, HandleMap handleMap);
}
