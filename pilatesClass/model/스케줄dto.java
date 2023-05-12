package pilatesClass.model;

public class 스케줄dto {

	
	private int 스케줄번호;
	private String 수강일시;
	private int 금액;
	private String 강사명;
	
	
	
	
	public 스케줄dto() {}




	public 스케줄dto(int 스케줄번호, String 수강일시, int 금액, String 강사명) {
		super();
		this.스케줄번호 = 스케줄번호;
		this.수강일시 = 수강일시;
		this.금액 = 금액;
		this.강사명 = 강사명;
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




	public String get강사명() {
		return 강사명;
	}




	public void set강사명(String 강사명) {
		this.강사명 = 강사명;
	}
	
	
	
	
}
