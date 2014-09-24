package eventHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * @class StreamUpdateProfileEventHandler
 * @date 2014-09-24
 * @author min
 * @brief 0x6001 헤더를 받았을 때 실행되는 event handler. updateProfile()를 갖고 있다.
 */
public class StreamUpdateProfileEventHandler implements EventHandler {
	private static Logger logger = Logger.getLogger(StreamUpdateProfileEventHandler.class.getName());
	
	private static final int DATA_SIZE = 1024;
	private static final int TOKEN_NUM = 5;

	@Override
	public String getHandler() {
		return "0x6001";
	}

	public void handleEvent(InputStream inputStream) {
		try {
			byte[] buffer = new byte[DATA_SIZE];
			inputStream.read(buffer);

			String data = new String(buffer);

			String[] params = new String[TOKEN_NUM];
			StringTokenizer token = new StringTokenizer(data, "|");

			int i = 0;
			while (token.hasMoreTokens()) {
				params[i] = token.nextToken();
				++i;
			}

			updateProfile(params);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateProfile(String[] params) {
		//System.out.println("UpdateProfile / " + "ID: " + params[0] + " / " + "PASSWORD: " + params[1] + " / "
		//		+ "NAME: " + params[2] + " / " + "AGE: " + params[3] + " / " + "GENDER: " + params[4]);
		
		logger.fatal("UpdateProfile / " + "ID: " + params[0] + " / " + "PASSWORD: " + params[1] + " / "
				+ "NAME: " + params[2] + " / " + "AGE: " + params[3] + " / " + "GENDER: " + params[4]);
	}

}
