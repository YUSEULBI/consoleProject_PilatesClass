package pilatesClass.model;

public class PointDto {

	private int point;
	private String reason;
	private String daterecord;
	private int 회원번호_fk;
    
    public PointDto() {
		// TODO Auto-generated constructor stub
	}

	public PointDto(int point, String reason, String daterecord, int 회원번호_fk) {
		super();
		this.point = point;
		this.reason = reason;
		this.daterecord = daterecord;
		this.회원번호_fk = 회원번호_fk;
	}

	@Override
	public String toString() {
		return "PointDto [point=" + point + ", reason=" + reason + ", daterecord=" + daterecord + ", 회원번호_fk=" + 회원번호_fk
				+ "]";
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
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

	public int get회원번호_fk() {
		return 회원번호_fk;
	}

	public void set회원번호_fk(int 회원번호_fk) {
		this.회원번호_fk = 회원번호_fk;
	}

	
	
}
