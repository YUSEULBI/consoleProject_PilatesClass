package pilatesClass.controller;

import pilatesClass.model.SalesDao;
import pilatesClass.model.SalesDto;

public class SalesController {
	
	// 싱글톤
	private static SalesController controller = new SalesController();
	private SalesController() { 	}
	public static SalesController getInstance() { return controller; 	}
	
	// 선택한 해의 누적예약수,총매출액
	public SalesDto yearTotal( int year ) {
		return SalesDao.getInstance().yearTotal(year);
	}
	
	// 월매출액
	public SalesDto monthTotal( int year , int month ) {
		return SalesDao.getInstance().monthTotal(year, month);
	}
	
	// 일자별매출액
	public SalesDto dailySales( int year , int month , int day ) {
		return SalesDao.getInstance().dailySales(year, month, day);
	}
}
