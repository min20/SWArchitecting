package eventHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class ReactorFileWriteEventHandler implements EventHandler {

	private static final String FILE_PATH = "./tmp/";

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
	
	
	// Array를 쓰기 때문에 기본 데이터 처리 단위는 bit. Byte로 변환하기 위해 8을 곱했다. 
	private static final int BUFFER_SIZE = (DATA_BYTE_SIZE <= 1024 * 200) ? 1 : 512;
	
	public void handleEvent(InputStream inputStream) {
		FileOutputStream fos = null; 
		byte[] buffer = new byte[BUFFER_SIZE];

		String fileName = "reactor_"
				+ new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SSSS").format(System.currentTimeMillis()) + ".dat";
		try {
			fos = new FileOutputStream(new File(FILE_PATH + File.separator + fileName));
			inputStream.read(buffer);

			for (int i = 0; i < DATA_BYTE_SIZE / BUFFER_SIZE; i++) {
				fos.write(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	public String getHandler() {
		return "0x7001";
	}
}
