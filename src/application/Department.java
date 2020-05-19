package application;

public class Department{
	String deptid, deptname, hod, DeptShortName;

	public Department(String deptid, String deptname,String DeptShortName, String hod) {
		super();
		this.deptid = deptid;
		this.deptname =deptname;
		this.DeptShortName = DeptShortName;
		this.hod = hod;
		
	}

	/**
	 * @return the deptid
	 */
	public String getDeptid() {
		return deptid;
	}

	/**
	 * @param deptid the deptid to set
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	/**
	 * @return the deptname
	 */
	public String getDeptname() {
		return deptname;
	}

	/**
	 * @param deptname the deptname to set
	 */
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	/**
	 * @return the hod
	 */
	public String getHod() {
		return hod;
	}

	/**
	 * @param hod the hod to set
	 */
	public void setHod(String hod) {
		this.hod = hod;
	}

	/**
	 * @return the deptShortName
	 */
	public String getDeptShortName() {
		return DeptShortName;
	}

	/**
	 * @param deptShortName the deptShortName to set
	 */
	public void setDeptShortName(String deptShortName) {
		DeptShortName = deptShortName;
	}

	
	}