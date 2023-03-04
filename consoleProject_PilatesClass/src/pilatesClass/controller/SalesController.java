package pilatesClass.controller;

import pilatesClass.model.Member.SalesDao;
import pilatesClass.model.Member.TotalDto;

public class SalesController {
	
	// 싱글톤
	private static SalesController controller = new SalesController();
	private SalesController() { 	}
	public static SalesController getInstance() { return controller; 	}
	
	// 누적예약수,총매출액
	public TotalDto total() {
		return SalesDao.getInstance().total();
	}
	
	// 월매출액
	public TotalDto monthTotal( int year , int month ) {
		return SalesDao.getInstance().monthTotal(year, month);
	}
}
