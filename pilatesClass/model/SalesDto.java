package pilatesClass.model;

public class SalesDto {
	int 누적예약수;
	int 총매출액;
	
	public SalesDto() {
		// TODO Auto-generated constructor stub
	}

	public SalesDto(int 누적예약수, int 총매출액) {
		super();
		this.누적예약수 = 누적예약수;
		this.총매출액 = 총매출액;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public int get누적예약수() {
		return 누적예약수;
	}

	public void set누적예약수(int 누적예약수) {
		this.누적예약수 = 누적예약수;
	}

	public int get총매출액() {
		return 총매출액;
	}

	public void set총매출액(int 총매출액) {
		this.총매출액 = 총매출액;
	}
	
	
}
