import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class Demultiplexer implements CompletionHandler<Integer, ByteBuffer> {
	private AsynchronousSocketChannel channel;
	private NioHandleMap handleMap;
	
	public Demultiplexer(AsynchronousSocketChannel channel, NioHandleMap handleMap) {
		this.channel = channel;
		this.handleMap = handleMap;
	}
	
	@Override
	public void completed(Integer result, ByteBuffer buffer) {
	}
	
	@Override
	public void failed(Throwable exc, ByteBuffer buffer) {
	}
	

}
