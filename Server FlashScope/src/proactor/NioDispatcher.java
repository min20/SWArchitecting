package proactor;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
* @brief  CompletionHandler로 accept를 받아 그 결과를 NioDemultiplexer가 Demultiplexing하도록 전달한다.
* @author flashscope
* @date 2014-05-17
* @version 0.0.1
*/
public class NioDispatcher implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

	private int HEADER_SIZE = 6;
	private NioHandleMap handleMap;
	
	/**
     * @brief NioDispatcher 생성자
	 * @details NioHandleMap을 받아서 보관한다.
	 * @param handleMap 
     */
	public NioDispatcher(NioHandleMap handleMap) {
		this.handleMap = handleMap;
	}

	/**
     * @brief accept결과를 받아 demultiplexer에게 전달한다.
	 * @param channel
	 * @param listener  
     */
	@Override
	public void completed(AsynchronousSocketChannel channel,
			AsynchronousServerSocketChannel listener) {
		
		listener.accept(listener, this);

		// 버퍼를 만들고 비동기 콜백 방식으로 읽은 결과를 받아온다.
		ByteBuffer buffer = ByteBuffer.allocate(HEADER_SIZE);
		channel.read(buffer, buffer, new NioDemultiplexer(channel, handleMap));
	}

	/**
     * @brief CompletionHandler가 실패되었을 떄 호출된다.
	 * @param exc
	 * @param listener  
     */
	@Override
	public void failed(Throwable exc, AsynchronousServerSocketChannel listener) {
		serverStarter.ServerInitializer.logger.error("NioDispatcher Failed");
	}
	
}
