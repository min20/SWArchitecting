package sort;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class SorterFactory {
	
	public static ISorter getSorter(File sourceXml) {
		ISorter sorter = null;
		Serializer serializer = new Persister();
		
		try {
			XMLParser parser = serializer.read(XMLParser.class, sourceXml);
			String sortType = parser.getSortType();
			System.out.println("[SorterFactory] sortType: " + sortType);
			
			sorter = (ISorter) Class.forName("sort." + sortType).newInstance();
		} catch (Exception e) {
			System.err.println("[SorterFactory] CAN NOT CREATE SORT CLASS: \n" + e);
		}

		
		return sorter;
	}
}
