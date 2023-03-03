package pilatesClass.model.Member;

public class RankDto {
	
	//필드
	private int 회원번호_fk;
	private int 예약수;
	private int 순위;
	
	//////////생성자
	public RankDto() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public RankDto(int 회원번호_fk, int 예약수) {
		super();
		this.회원번호_fk = 회원번호_fk;
		this.예약수 = 예약수;
	}


	/////toString
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
	////////////getter setter
	public int get회원번호_fk() {
		return 회원번호_fk;
	}



	public void set회원번호_fk(int 회원번호_fk) {
		this.회원번호_fk = 회원번호_fk;
	}



	public int get예약수() {
		return 예약수;
	}



	public void set예약수(int 예약수) {
		this.예약수 = 예약수;
	}



	public int get순위() {
		return 순위;
	}



	public void set순위(int 순위) {
		this.순위 = 순위;
	}

	
}
