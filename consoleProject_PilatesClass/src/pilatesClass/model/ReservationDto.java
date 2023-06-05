package pilatesClass.model;

public class ReservationDto {

	private int rno; //식별번호pk
	private int mno; //회원번호fk
	private int sno; //스케줄번호fk
	
	public ReservationDto() {}

	public ReservationDto(int rno, int mno, int sno) {
		super();
		this.rno = rno;
		this.mno = mno;
		this.sno = sno;
	}

	public int getrno() {
		return rno;
	}

	public void setrno(int rno) {
		this.rno = rno;
	}

	public int getmno() {
		return mno;
	}

	public void setmno(int mno) {
		this.mno = mno;
	}

	public int getsno() {
		return sno;
	}

	public void setsno(int sno) {
		this.sno = sno;
	}
	
	
	
	
	
}
