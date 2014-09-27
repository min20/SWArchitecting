import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.StringTokenizer;

public class NioUpdateProfileEventHandler implements NioEventHandler {
	private static final int NUM_TOKEN = 6;

	private AsynchronousSocketChannel channel;
	private ByteBuffer buffer;

	@Override
	public String getHeader() {
		return "0x6001";
	}

	@Override
	public int getDataSize() {
		return 1024;
	}

	@Override
	public void initialize(AsynchronousSocketChannel channel, ByteBuffer buffer) {
		this.channel = channel;
		this.buffer = buffer;
	}

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		if (result == -1) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (result > 0) {
			buffer.flip();
			String message = new String(buffer.array());
			
			String[] params = new String[NUM_TOKEN];
			StringTokenizer token = new StringTokenizer(message, "|");
			
			int i = 0;
			while (token.hasMoreTokens()) {
				params[i] = token.nextToken();
				i++;
			}
			
			updateProfile(params);
			
			buffer.clear();
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
	}
	
	private void updateProfile(String[] params) {
		System.out.println("UpdateProfile / " + "ID: " + params[0] + " / " + "PASSWORD: " + params[1] + " / "
				+ "NAME: " + params[2] + " / " + "AGE: " + params[3] + " / " + "GENDER: " + params[4]);
	}
}
