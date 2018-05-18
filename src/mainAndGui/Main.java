package mainAndGui;

import support.Config;
import support.XML_Editor;

public class Main {

	public static void main(String[] args) {
		XML_Editor xml = new XML_Editor();
		Config config = new Config();
		
		new Interface(config.getEmailAdmin());
	}

}
