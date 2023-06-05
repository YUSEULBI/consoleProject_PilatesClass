package pilatesClass.model;

public class ClassScheduleDto {

	
	private int sno; // 스케줄번호
	private String sdate; // 수강일시
	private int sprice; // 금액
	private int mno; // mno
	private String teacherName; // 강사명
	
	
	
	public ClassScheduleDto() {}




	public ClassScheduleDto(int sno, String sdate, int sprice, int mno) {
		super();
		this.sno = sno;
		this.sdate = sdate;
		this.sprice = sprice;
		this.mno = mno;
	}
	
	// 수강등록, 수업출력시 생성자
	public ClassScheduleDto(int sno, String sdate, int sprice, String teacherName) {
		super();
		this.sno = sno;
		this.sdate = sdate;
		this.sprice = sprice;
		this.teacherName = teacherName;
	}




	public String getTeacherName() {
		return teacherName;
	}




	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}




	public int getSno() {
		return sno;
	}




	public void setSno(int sno) {
		this.sno = sno;
	}




	public String getSdate() {
		return sdate;
	}




	public void setSdate(String sdate) {
		this.sdate = sdate;
	}




	public int getSprice() {
		return sprice;
	}




	public void setSprice(int sprice) {
		this.sprice = sprice;
	}




	public int getMno() {
		return mno;
	}




	public void setMno(int mno) {
		this.mno = mno;
	}

	
	
	
	
	
}
