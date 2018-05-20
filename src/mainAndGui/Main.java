package mainAndGui;

import support.Config;

public class Main {

	public static void main(String[] args) {
		Config config = new Config();
		new Interface(config.getEmailAdmin());
		
	
	}

}
