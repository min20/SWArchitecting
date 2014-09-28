package proactor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
* @brief 헤더값에 따른 이벤트 핸들러 분류
* @details CompletionHandler로 넘어온 메시지를 헤더사이즈만큼 읽어서 헤더에 맞는 이벤트 핸들러를 실행시킨다.
* @author flashscope
* @date 2014-05-17
* @version 0.0.1
*/
public class NioDemultiplexer implements CompletionHandler<Integer, ByteBuffer> {
	
	private AsynchronousSocketChannel channel;
	private NioHandleMap handleMap;
	
	/**
     * @brief NioDemultiplexer 생성자
	 * @details channel과 NioHandleMap을 받아서 보관한다.
	 * @param channel 
	 * @param handleMap 
     */
	public NioDemultiplexer(AsynchronousSocketChannel channel, NioHandleMap handleMap) {
		this.channel = channel;
		this.handleMap = handleMap;
	}

	/**
     * @brief 헤더만큼 읽은 결과를 핸들맵에서 찾아 해당하는 이벤트 핸들러를 실행한다.
	 * @param result
	 * @param buffer  
     */
	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		
		if (result == -1) {
			serverStarter.ServerInitializer.logger.error("completed failed");
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
				serverStarter.ServerInitializer.logger.error(e.getMessage());
			}
		} else if (result > 0) {
			buffer.flip();

			String header = new String(buffer.array());
			
			try {
				NioEventHandler handler = (NioEventHandler)Class.forName( handleMap.get(header) ).newInstance();

				ByteBuffer newBuffer = ByteBuffer.allocate(handler.getDataSize());
				handler.initialize(channel);
				channel.read(newBuffer, newBuffer, handler);
				
			} catch (InstantiationException e) {
				e.printStackTrace();
				serverStarter.ServerInitializer.logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				serverStarter.ServerInitializer.logger.error(e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				serverStarter.ServerInitializer.logger.error(e.getMessage());
			}
			
			
			
		}
		
	}

	/**
     * @brief CompletionHandler가 실패되었을 떄 호출된다.
	 * @param exc
	 * @param listener  
     */
	@Override
	public void failed(Throwable exc, ByteBuffer buffer) {
		serverStarter.ServerInitializer.logger.error("NioDemultiplexer Failed");
	}
	
}
