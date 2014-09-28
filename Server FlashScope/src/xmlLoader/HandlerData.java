package xmlLoader;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
* @brief HandlerList데이터에서 각각 handler의 헤더 속성값과 jar파일 경로를 반환한다.
* @author flashscope
* @date 2014-05-11
* @version 0.0.1
*/
@Root
public class HandlerData {
	@Attribute(required=false)  
    private String header;
	
	@Text 
	private String text;
	
	public String getHeader() {
		return header;
	}
	
	public String getHandler() {
		return text;
	}
}
