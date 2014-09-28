package reactor;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

/**
* @brief UpdateProfile하는 이벤트 핸들러 
* @details 헤더를 제외한 데이터를 파이프 단위로 5개를 끊어 메시지로 출력을 한다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
public class StreamUpdateProfileEventHandler implements EventHandler {
	
	private static final int DATA_SIZE = 1024;
    private static final int TOKEN_NUM = 5;
    
    /**
     * @brief 이벤트 핸들러의 header를 반환한다.
     * @return header String
     */
	@Override
	public String getHandler() {
		return "0x6001";
	}
	
	/**
     * @brief 데이터를 읽어서 파싱한 후 sayHello 이벤트를 실행한다.
     * @param inputStream
     */
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
			serverStarter.ServerInitializer.logger.error(e.getMessage());
		}
		
	}
	
	/**
     * @brief 받은 데이터를 콘솔창에 출력한다.
     * @param params
     */
	private void updateProfile(String[] params) {
		System.out.println("[REA]UpdateProfile -> " +
                " id :" + params[0] +
                " password : " + params[1] +
                " name : " + params[2] +
                " age : " + params[3] +
                " gender: " + params[4]);
    }

}
