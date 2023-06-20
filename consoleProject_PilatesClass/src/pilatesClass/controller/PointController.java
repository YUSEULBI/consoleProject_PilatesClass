package pilatesClass.controller;

import java.util.ArrayList;

import pilatesClass.model.PointDao;
import pilatesClass.model.PointDto;
import pilatesClass.model.RefundDto;
import pilatesClass.model.ReservationDto;

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
		int loginsession = MemberController.getInstance().getLogSession();
		return PointDao.getInstance().pointCheck(loginsession);
	}
	
	// 포인트 사용
	public boolean pointUse( int point , int rno ) {
		int loginsession = MemberController.getInstance().getLogSession();
		return PointDao.getInstance().pointUse(point ,loginsession , rno);
	}
	
	// 포인트 적립
	public int addPoint( int amount  ,  int rno ) {
		String reason = "1% 포인트적립 [결제금액"+amount+"] [수강번호 "+rno+"번 수업예약]";
		// 결제금액10% 포인트 만들기 
		double point = amount*0.01;
		int intPoint = (int)point;
		int loginsession = MemberController.getInstance().getLogSession();
		boolean result = PointDao.getInstance().addPoint(intPoint, reason, loginsession , rno);
		if ( result ) { return intPoint; }
		else { return -1;}
	}
	
	// 예약취소시 예약시 적립된 포인트 차감
	public RefundDto cancelPoint( int rno , byte type ) {
		int loginsession = MemberController.getInstance().getLogSession();
		return PointDao.getInstance().cancelPoint(rno , loginsession, type);
	}
	
	// 포인트 출력
	public ArrayList<PointDto> printPointHistory(){
		int loginsession = MemberController.getInstance().getLogSession();
		return PointDao.getInstance().printPointHistory(loginsession);
	}
	
	// 수업취소로 인한 여러명 예약취소환불
	public ArrayList<RefundDto> refundPointCancelledClass( ArrayList<ReservationDto> reservationDtoList ) {
		ArrayList<RefundDto> refundDtoList = new ArrayList<>();
		byte type = 2; // 업체측 일방 취소이기 때문에 적립포인트 회수 안함. 환불만 진행 = 2
		for( ReservationDto dto : reservationDtoList ) {
			refundDtoList.add( PointDao.getInstance().cancelPoint(dto.getrno(), dto.getmno() , type ) );
		}
		return refundDtoList;
	}
}
