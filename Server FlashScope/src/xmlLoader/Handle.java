package xmlLoader;

public class Handle {
	private String header;
	private String className;
	
	public Handle(String header, String className) {
		this.header = header;
		this.className = className;
	}

	public String getHeader() {
		return header;
	}

	public String getClassName() {
		return className;
	}
	
}
