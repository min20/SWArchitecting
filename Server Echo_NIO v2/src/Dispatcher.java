import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class Dispatcher implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

	private int HEADER_SIZE = 6;
	private NioHandleMap handleMap;
	
	public Dispatcher(NioHandleMap handleMap) {
		this.handleMap = handleMap;
	}

	@Override
	public void completed(AsynchronousSocketChannel channel, AsynchronousServerSocketChannel listener) {
		listener.accept(listener, this);

		ByteBuffer buffer = ByteBuffer.allocate(HEADER_SIZE);
		channel.read(buffer, buffer, new Demultiplexer(channel, handleMap));
	}

	@Override
	public void failed(Throwable exc, AsynchronousServerSocketChannel listener) {
		System.out.println("Dispatcher에서 뭔가 실패했군요. 애석하게 생각합니다.");
	}

}
