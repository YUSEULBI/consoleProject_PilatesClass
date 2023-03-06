package pilatesClass.model.Member;

public class RankDto {
	
	//필드
	private int 회원번호_fk;
	private String 회원이름;
	private int 예약수;
	
	public RankDto() {
		// TODO Auto-generated constructor stub
	}

	public RankDto(int 회원번호_fk, String 회원이름, int 예약수) {
		super();
		this.회원번호_fk = 회원번호_fk;
		this.회원이름 = 회원이름;
		this.예약수 = 예약수;
	}

	@Override
	public String toString() {
		return "RankDto [회원번호_fk=" + 회원번호_fk + ", 회원이름=" + 회원이름 + ", 예약수=" + 예약수 + "]";
	}

	public int get회원번호_fk() {
		return 회원번호_fk;
	}

	public void set회원번호_fk(int 회원번호_fk) {
		this.회원번호_fk = 회원번호_fk;
	}

	public String get회원이름() {
		return 회원이름;
	}

	public void set회원이름(String 회원이름) {
		this.회원이름 = 회원이름;
	}

	public int get예약수() {
		return 예약수;
	}

	public void set예약수(int 예약수) {
		this.예약수 = 예약수;
	}
	
	
	
	
}
