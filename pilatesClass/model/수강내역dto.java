package pilatesClass.model;

public class 수강내역dto {

	private int 수강내역번호;
	private int 회원번호_fk;
	private int 스케줄번호_fk;
	
	public 수강내역dto() {}

	public 수강내역dto(int 수강내역번호, int 회원번호_fk, int 스케줄번호_fk) {
		super();
		this.수강내역번호 = 수강내역번호;
		this.회원번호_fk = 회원번호_fk;
		this.스케줄번호_fk = 스케줄번호_fk;
	}

	public int get수강내역번호() {
		return 수강내역번호;
	}

	public void set수강내역번호(int 수강내역번호) {
		this.수강내역번호 = 수강내역번호;
	}

	public int get회원번호_fk() {
		return 회원번호_fk;
	}

	public void set회원번호_fk(int 회원번호_fk) {
		this.회원번호_fk = 회원번호_fk;
	}

	public int get스케줄번호_fk() {
		return 스케줄번호_fk;
	}

	public void set스케줄번호_fk(int 스케줄번호_fk) {
		this.스케줄번호_fk = 스케줄번호_fk;
	}
	
	
	
	
	
}
