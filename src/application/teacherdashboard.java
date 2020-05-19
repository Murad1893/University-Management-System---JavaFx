package application;

public class teacherdashboard{
	String sectionID,courseID;

	public  teacherdashboard(String sectionID,String courseID) {
		super();

		this.sectionID =sectionID;
		this.courseID = courseID;
		
		
	}


	/**
	 * @return the sectionID
	 */
	public String getSectionID() {
		return sectionID;
	}

	/**
	 * @param sectionID the sectionID to set
	 */
	public void setSectionID(String sectionID) {
		this.sectionID = sectionID;
	}

	/**
	 * @return the courseID
	 */
	public String getCourseID() {
		return courseID;
	}

	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
}