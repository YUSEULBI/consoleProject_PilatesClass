package pilatesClass.model.Member;

public class SalesnRankDto {
	private int 예약번호;
	private String 수강일시;
	private int 금액;
	private String 아이디;
	private int 회원번호;
	private int 수업번호;
	
	public SalesnRankDto() {	}

	public SalesnRankDto(int 예약번호, String 수강일시, int 금액, String 아이디, int 회원번호, int 수업번호) {
		super();
		this.예약번호 = 예약번호;
		this.수강일시 = 수강일시;
		this.금액 = 금액;
		this.아이디 = 아이디;
		this.회원번호 = 회원번호;
		this.수업번호 = 수업번호;
	}

	public int get예약번호() {
		return 예약번호;
	}

	public void set예약번호(int 예약번호) {
		this.예약번호 = 예약번호;
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

	public String get아이디() {
		return 아이디;
	}

	public void set아이디(String 아이디) {
		this.아이디 = 아이디;
	}

	public int get회원번호() {
		return 회원번호;
	}

	public void set회원번호(int 회원번호) {
		this.회원번호 = 회원번호;
	}

	public int get수업번호() {
		return 수업번호;
	}

	public void set수업번호(int 수업번호) {
		this.수업번호 = 수업번호;
	}
	
	
	
}
