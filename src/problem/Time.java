package problem;

/**
 * Class that represents time, used for max and ideal duration of waiting for a problem
 * contains total waiting value of:
 * 		- days
 * 		- hours
 * 		- minutes
 * 
 * @author Kevin Corrales nº 73529
 *
 */
public class Time {
	private int days;
	private int hours;
	private int minutes;
	
	/**
	 * Class constructor
	 * 
	 * @param days
	 * @param hours
	 * @param minutes
	 */
	public Time(int days, int hours, int minutes) {
		super();
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
	}


	public int getDays() {
		return days;
	}


	public int getHours() {
		return hours;
	}


	public int getMinutes() {
		return minutes;
	}
	
	public String toString() {
		return "Days:"+getDays()+"-Hours:"+getHours()+"-Minutes:"+getMinutes()
		+System.getProperty("line.separator");
	}
}
