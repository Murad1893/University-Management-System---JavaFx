package application;

public class attendanceview {
	String lec;
	String date;
	String presensce;
	public attendanceview(String lec, String date, String presensce) {
		super();
		this.lec = lec;
		this.date = date;
		this.presensce = presensce;
	}
	/**
	 * @return the lec
	 */
	public String getLec() {
		return lec;
	}
	/**
	 * @param lec the lec to set
	 */
	public void setLec(String lec) {
		this.lec = lec;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the presensce
	 */
	public String getPresensce() {
		return presensce;
	}
	/**
	 * @param presensce the presensce to set
	 */
	public void setPresensce(String presensce) {
		this.presensce = presensce;
	}
	
	
}
