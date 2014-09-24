package eventHandler;

import java.io.InputStream;

/**
 * @interface EventHandler
 * @date 2014-09-24
 * @author min
 * @brief event handler의 메소드를 정의한 interface.
 */
public interface EventHandler {

	public String getHandler();

	public void handleEvent(InputStream inputStream);

}
