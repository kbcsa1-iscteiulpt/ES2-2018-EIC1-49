package support;

/**
 * Class that represents system configuration
 * contains:
 * 		- email of the administrator

 * 
 * @author Kevin Corrales n� 73529
 *
 */
public class Config {
	
	private final String emailAdmin;
		
	public Config(String emailAdmin){
		this.emailAdmin = emailAdmin;
	}

	public String getEmailAdmin() {
		return emailAdmin;
	}
	
}
