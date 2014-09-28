package reactor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @brief SayHello하는 이벤트 핸들러
 * @details 헤더를 제외한 데이터를 파이프 단위로 2개를 끊어 메시지로 출력을 한다.
 * @author flashscope
 * @date 2014-05-11
 * @version 0.0.1
 */
public class FileWriteEventHandler implements EventHandler {

	private static final int DATA_SIZE = (1024*200);

	/**
	 * @brief 이벤트 핸들러의 header를 반환한다.
	 * @return header String
	 */
	@Override
	public String getHandler() {
		return "0x9003";
	}

	/**
	 * @brief 데이터를 읽어서 파싱한 후 sayHello 이벤트를 실행한다.
	 * @param inputStream
	 */
	public void handleEvent(InputStream inputStream) {
		
		try {
			byte[] buffer = new byte[DATA_SIZE];
			inputStream.read(buffer);
			
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");
			String writeTime = dayTime.format(new Date(time));
			
				
			String filename= "/Users/flashscope/Desktop/test/reactor-200kb_write_" + writeTime + ".dat";
			
			//create an object of FileOutputStream
			FileOutputStream fos = new FileOutputStream(new File(filename));

			fos.write(buffer);
			System.out.println("WriteComplete");
			fos.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			serverStarter.ServerInitializer.logger.error(e.getMessage());
		}
		
	}

}
