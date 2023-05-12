package pilatesClass.controller;

import pilatesClass.model.PointDao;

public class PointController {
	private static PointController controller = new PointController();
	private PointController() {
		// TODO Auto-generated constructor stub
	}
	public static PointController getInstance() {
		return controller;
	}
	
	// 회원의 로그인한 회원번호로 보유포인트확인
	public int pointCheck() {
		int loginsession = 회원controller.getInstance().getLogSession();
		return PointDao.getInstance().pointCheck(loginsession);
	}
	
	// 포인트 사용
	public boolean pointUse( int point ) {
		int loginsession = 회원controller.getInstance().getLogSession();
		return PointDao.getInstance().pointUse(point, "사용" ,loginsession);
	}
	
	// 포인트 적립
	public int addPoint( int amount  , String reason ) {
		// 결제금액10% 포인트 만들기 
		double point = amount*0.1;
		int intPoint = (int)point;
		int loginsession = 회원controller.getInstance().getLogSession();
		boolean result = PointDao.getInstance().addPoint(intPoint, reason, loginsession);
		if ( result ) { return intPoint;	}
		else { return -1;}
	}
	
}
