package application;

public class Teachercourseallotment{
	String staffID, sectionID,courseID;

	public Teachercourseallotment(String staffID, String sectionID,String courseID) {
		super();
		this.staffID = staffID;
		this.sectionID =sectionID;
		this.courseID = courseID;
		
		
	}

	/**
	 * @return the staffID
	 */
	public String getStaffID() {
		return staffID;
	}

	/**
	 * @param staffID the staffID to set
	 */
	public void setStaffID(String staffID) {
		this.staffID = staffID;
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