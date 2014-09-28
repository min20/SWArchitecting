package reactor;

import java.io.InputStream;

/**
* @brief EventHandler 인터페이스 정의
* @details 정의된 헤더를 반환하는 getHandler()함수와 핸들러를 실행하는 handleEvent함수로 이루어지도록 정의
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public interface EventHandler {
	
	public String getHandler();
	
	public void handleEvent(InputStream inputStream);
}
