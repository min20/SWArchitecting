import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class EchoHandler implements CompletionHandler<Integer, ByteBuffer> {

	private AsynchronousSocketChannel channel;

	public EchoHandler(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}

	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		if (result == -1) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (result > 0) {
			buffer.flip();
			
			String message = new String(buffer.array());
			System.out.println("ECHO: " + message);
			
			// ECHO 시작
			buffer = ByteBuffer.wrap(message.getBytes());
			Future<Integer> w = channel.write(buffer);
			
			try {
				w.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			buffer.clear();
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer buffer) {
		System.out.println("EchoHandler에서 뭔가 실패했군요. 애석하게 생각합니다.");
	}

}
