package application;

public class Course{
	String id, cname, credit, deptcode, preq1, preq2, preq3, preq4;

	public Course(String id, String cname, String credit, String deptcode, String preq1,String preq2,String preq3,String preq4) {
		super();
		this.id = id;
		this.cname = cname;
		this.credit = credit;
		this.deptcode = deptcode;
		this.preq1 = preq1;
		this.preq2 = preq2;
		this.preq3 = preq3;
		this.preq4 = preq4;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @return the credit
	 */
	public String getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(String credit) {
		this.credit = credit;
	}

	/**
	 * @return the deptcode
	 */
	public String getDeptcode() {
		return deptcode;
	}

	/**
	 * @param deptcode the deptcode to set
	 */
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	/**
	 * @return the preq1
	 */
	public String getPreq1() {
		return preq1;
	}

	/**
	 * @param preq1 the preq1 to set
	 */
	public void setPreq1(String preq1) {
		this.preq1 = preq1;
	}

	/**
	 * @return the preq2
	 */
	public String getPreq2() {
		return preq2;
	}

	/**
	 * @param preq2 the preq2 to set
	 */
	public void setPreq2(String preq2) {
		this.preq2 = preq2;
	}

	/**
	 * @return the preq3
	 */
	public String getPreq3() {
		return preq3;
	}

	/**
	 * @param preq3 the preq3 to set
	 */
	public void setPreq3(String preq3) {
		this.preq3 = preq3;
	}

	/**
	 * @return the preq4
	 */
	public String getPreq4() {
		return preq4;
	}

	/**
	 * @param preq4 the preq4 to set
	 */
	public void setPreq4(String preq4) {
		this.preq4 = preq4;
	}

	
	
	
	
	
}
