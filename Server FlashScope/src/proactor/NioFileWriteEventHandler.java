package proactor;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.concurrent.Future;

public class NioFileWriteEventHandler implements NioEventHandler {

	private static final String FILE_PATH = "./tmp/";

	private AsynchronousSocketChannel channel;

	// 512B
//	private static final int DATA_BYTE_SIZE = 512;

	// 3KB
//	private static final int DATA_BYTE_SIZE = 1024 * 3;

	// 200KB
//	private static final int DATA_BYTE_SIZE = 1024 * 200;

	// 2MB
//	private static final int DATA_BYTE_SIZE = 1024 * 1024 * 2;

	// 20MB
	private static final int DATA_BYTE_SIZE = 1024 * 1024 * 20;

	// 기본 처리단위가 Byte
	private static final int BUFFER_SIZE = (DATA_BYTE_SIZE <= 1024 * 200) ? 1 : 512;

	@Override
	public void completed(Integer result, ByteBuffer attachment) {
		if (result == -1) {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (result > 0) {
			String fileName = "proactor_"
					+ new SimpleDateFormat("yyyy-MM-dd_HH:mm:SSS").format(System.currentTimeMillis()) + ".dat";
			Path filePath = Paths.get(FILE_PATH + File.separator + fileName);
			AsynchronousFileChannel asynchronousFileChannel = null;
			try {

				asynchronousFileChannel = AsynchronousFileChannel.open(filePath, StandardOpenOption.CREATE,
						StandardOpenOption.WRITE);

				for (int i = 0; i < getDataSize() / BUFFER_SIZE; i++) {
					Future<Integer> future = asynchronousFileChannel.write(ByteBuffer.allocate(BUFFER_SIZE), i
							* BUFFER_SIZE);
					while (!future.isDone()) {
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (asynchronousFileChannel != null)
						asynchronousFileChannel.close();

					if (channel != null)
						channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
	}

	@Override
	public int getDataSize() {
		return DATA_BYTE_SIZE;
	}

	@Override
	public String getHandle() {
		return "0x7001";
	}

	@Override
	public void initialize(AsynchronousSocketChannel channel) {
		this.channel = channel;
	}

}
