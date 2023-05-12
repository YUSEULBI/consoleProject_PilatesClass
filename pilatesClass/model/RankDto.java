package pilatesClass.model;

public class RankDto {
	
	//필드
	private String 회원이름;
	private int 예약수;
	private int 랭킹;
	
	public RankDto() {
		// TODO Auto-generated constructor stub
	}

	public RankDto(String 회원이름, int 예약수, int 랭킹) {
		super();
		this.회원이름 = 회원이름;
		this.예약수 = 예약수;
		this.랭킹 = 랭킹;
	}

	@Override
	public String toString() {
		return "RankDto [회원이름=" + 회원이름 + ", 예약수=" + 예약수 + ", 랭킹=" + 랭킹 + "]";
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

	public int get랭킹() {
		return 랭킹;
	}

	public void set랭킹(int 랭킹) {
		this.랭킹 = 랭킹;
	}
	
	
	
	
	
}
