package BackEnd;


public class Date {
	int year, day, month;
	
	public Date() {};
	
	public Date(int month, int day, int year) {
		this.year = year;
		this.day = month;
		this.month = month;
	}
	public String dateAsString() {
		return String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	
	
	
}
