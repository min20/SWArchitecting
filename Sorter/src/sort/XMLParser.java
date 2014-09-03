package sort;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="SortStrategy")
public class XMLParser {
	@Element
	private String sortType;
	
	public XMLParser() {
		super();
	}
	
	public String getSortType(){
		return sortType;
	}

}
