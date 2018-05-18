package mainAndGui;

import support.Config;
import support.XMLEditor;

public class Main {

	public static void main(String[] args) {
		XMLEditor xml = new XMLEditor();
		Config config = new Config();
		
		new Interface(config.getEmailAdmin());
	}

}
