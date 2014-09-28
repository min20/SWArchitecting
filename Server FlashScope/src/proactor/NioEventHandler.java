package proactor;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
* @brief EventHandler 인터페이스 정의
* @details 정의된 헤더를 반환하는 getHandler()함수와 
* 읽어야 되는 데이터 사이즈를 반환하는 getDataSize()함수,
* 핸들러를 실행하는 handleEvent함수로 이루어지도록 정의
* @author flashscope
* @date 2014-05-17
* @version 0.0.1
*/
public interface NioEventHandler extends CompletionHandler<Integer, ByteBuffer> {
	
    public String getHandle();
    
    public int getDataSize();
    
	public void initialize(AsynchronousSocketChannel channel);
	
}
