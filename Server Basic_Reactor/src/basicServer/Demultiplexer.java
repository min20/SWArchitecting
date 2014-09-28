package basicServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


/**
 * @class Demultiplexer
 * @date 2014-09-24
 * @author min
 * @brief Input Stream에서 header를 읽고 적절한 event handler를 부른다.  
 */
public class Demultiplexer implements Runnable {
	//private static Logger logger = Logger.getLogger(Demultiplexer.class.getName());
	
	//private final int MAX_DATA_SIZE = 512 + 6;
	private final int HEADER_SIZE = 6;

	private Socket socket;
	private HandleMap handleMap;
	
	public Demultiplexer(Socket socket, HandleMap handleMap) {
		this.socket = socket;
		this.handleMap = handleMap;
	}

	@Override
	public void run() {
		InputStream inputStream = null;
		try {
			//InputStream originalInputStream = socket.getInputStream();

			//ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			//byte[] fullBuffer = new byte[MAX_DATA_SIZE];
			byte[] headerBuffer = new byte[HEADER_SIZE];
			
			//originalInputStream.read(fullBuffer);
			//byteArrayOutputStream.write(fullBuffer);
			
			//byteArrayOutputStream.flush();

			//inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray()); 
			inputStream = socket.getInputStream();
			//logger.fatal("INPUT: " + new String(fullBuffer));
			
			inputStream.read(headerBuffer);
			String header = new String(headerBuffer);

			handleMap.get(header).handleEvent(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
