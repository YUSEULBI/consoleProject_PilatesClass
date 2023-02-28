package pilatesClass.model.Member;

public class 수강내역dto {

	
	private int 스케줄번호;
	private String 수강일시;
	private int 금액;
	private int 회원번호_fk;
	
	
	
	
	public 수강내역dto() {}




	public 수강내역dto(int 스케줄번호, String 수강일시, int 금액, int 회원번호_fk) {
		super();
		this.스케줄번호 = 스케줄번호;
		this.수강일시 = 수강일시;
		this.금액 = 금액;
		this.회원번호_fk = 회원번호_fk;
	}




	public int get스케줄번호() {
		return 스케줄번호;
	}




	public void set스케줄번호(int 스케줄번호) {
		this.스케줄번호 = 스케줄번호;
	}




	public String get수강일시() {
		return 수강일시;
	}




	public void set수강일시(String 수강일시) {
		this.수강일시 = 수강일시;
	}




	public int get금액() {
		return 금액;
	}




	public void set금액(int 금액) {
		this.금액 = 금액;
	}




	public int get회원번호_fk() {
		return 회원번호_fk;
	}




	public void set회원번호_fk(int 회원번호_fk) {
		this.회원번호_fk = 회원번호_fk;
	}
	
	
	
	
}
