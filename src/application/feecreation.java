package application;

public class feecreation {
	
	String studid, total, due, status;

	public feecreation(String studid, String total, String due, String status) {
		super();
		this.studid = studid;
		this.total = total;
		this.due = due;
		this.status = status;
	}

	/**
	 * @return the studid
	 */
	public String getStudid() {
		return studid;
	}

	/**
	 * @param studid the studid to set
	 */
	public void setStudid(String studid) {
		this.studid = studid;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the due
	 */
	public String getDue() {
		return due;
	}

	/**
	 * @param due the due to set
	 */
	public void setDue(String due) {
		this.due = due;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
