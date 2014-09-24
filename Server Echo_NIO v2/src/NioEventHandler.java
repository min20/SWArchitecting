import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public interface NioEventHandler {
	
	public String getHandler();
	public int getDataSize();
	public void initialize(AsynchronousSocketChannel channel, ByteBuffer buffer);

}
