package pilatesClass.model;

public class PointDto {

	private int pointvalue;
	private String reason;
	private String daterecord;
	private int mno;
	private int rno; // 수강번호
    
	// 빈생성자
    public PointDto() {	}

    

	public PointDto(int pointvalue, String reason, String daterecord, int mno, int rno) {
		super();
		this.pointvalue = pointvalue;
		this.reason = reason;
		this.daterecord = daterecord;
		this.mno = mno;
		this.rno = rno;
	}

	// 포인트이력 출력용 생성자
	public PointDto( String daterecord , int pointvalue , String reason ) {
		super();
		this.daterecord = daterecord;
		this.pointvalue = pointvalue;
		this.reason = reason;
	}



	@Override
	public String toString() {
		return "PointDto [pointvalue=" + pointvalue + ", reason=" + reason + ", daterecord=" + daterecord + ", mno="
				+ mno + ", rno=" + rno + "]";
	}



	public int getPointvalue() {
		return pointvalue;
	}



	public void setPointvalue(int pointvalue) {
		this.pointvalue = pointvalue;
	}



	public String getReason() {
		return reason;
	}



	public void setReason(String reason) {
		this.reason = reason;
	}



	public String getDaterecord() {
		return daterecord;
	}



	public void setDaterecord(String daterecord) {
		this.daterecord = daterecord;
	}



	public int getMno() {
		return mno;
	}



	public void setMno(int mno) {
		this.mno = mno;
	}



	public int getRno() {
		return rno;
	}



	public void setRno(int rno) {
		this.rno = rno;
	}

	
	
}
