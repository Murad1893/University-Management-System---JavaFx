package application;

public class Staff{
	String id, fname, mname, lname, gender, c_addr, p_addr, mobileno, homeno, altno, emailh, emailw, cnic, dob, qualification, deptcode, jobtitle, salary;

	public Staff(String id, String fname, String mname, String lname, String gender, String c_addr, String p_addr,
			String mobileno, String homeno, String altno, String emailh, String emailw, String cnic, String dob,
			String qualification, String deptcode, String jobtitle, String salary) {
		super();
		this.id = id;
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
		this.gender = gender;
		this.c_addr = c_addr;
		this.p_addr = p_addr;
		this.mobileno = mobileno;
		this.homeno = homeno;
		this.altno = altno;
		this.emailh = emailh;
		this.emailw = emailw;
		this.cnic = cnic;
		this.dob = dob;
		this.qualification = qualification;
		this.deptcode = deptcode;
		this.jobtitle = jobtitle;
		this.salary = salary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getC_addr() {
		return c_addr;
	}

	public void setC_addr(String c_addr) {
		this.c_addr = c_addr;
	}

	public String getP_addr() {
		return p_addr;
	}

	public void setP_addr(String p_addr) {
		this.p_addr = p_addr;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getHomeno() {
		return homeno;
	}

	public void setHomeno(String homeno) {
		this.homeno = homeno;
	}

	public String getAltno() {
		return altno;
	}

	public void setAltno(String altno) {
		this.altno = altno;
	}

	public String getEmailh() {
		return emailh;
	}

	public void setEmailh(String emailh) {
		this.emailh = emailh;
	}

	public String getEmailw() {
		return emailw;
	}

	public void setEmailw(String emailw) {
		this.emailw = emailw;
	}

	public String getCnic() {
		return cnic;
	}

	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}


}
