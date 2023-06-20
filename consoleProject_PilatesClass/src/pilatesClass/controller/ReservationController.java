package pilatesClass.controller;

import java.util.ArrayList;

import pilatesClass.model.ReservationDao;
import pilatesClass.model.ReservationDto;
import pilatesClass.model.ClassScheduleDto;

public class ReservationController {

	
	private static ReservationController con= new ReservationController();
	private ReservationController ( ) {};
	public static ReservationController getInstance( ) {return con;}
	
	
	
	public boolean cancel(int sno) { 
		int logsession=MemberController.getInstance().getLogSession();
		return ReservationDao.getInstance().cancel(sno,logsession);
	}
	
	// 수업예약
	public int reservation(int ch) {		
		int mno = MemberController.getInstance().getLogSession();
		return ReservationDao.getInstance().reservation(ch , mno);
		}
	
	public ArrayList<ClassScheduleDto> print(){
		int mno = MemberController.getInstance().getLogSession();
		return ReservationDao.getInstance().print(mno);
	}
	
		
	public boolean re_check(int ch) {
		int mno = MemberController.getInstance().getLogSession();
		return ReservationDao.getInstance().re_check(ch,mno);
	}
	
	public int pay(int point ,int money,int ch) {//결제 및 거스름돈
		
		return ReservationDao.getInstance().pay(point, money, ch);
	}
	
	// 결제 예정금액 체크
	public int payMoneyCheck( int ch ) {
		return ReservationDao.getInstance().payMoneyCheck(ch);
	}
	
	// 수업이 본인이 예약한 수업인지 확인 => 예약테이블 식별번호 rno 반환
	public int checkSno( int sno ) {
		int mno = MemberController.getInstance().getLogSession();
		return ReservationDao.getInstance().checkRno(sno, mno);
	}
	
	// 수업번호에 해당하는 모든 예약리스트 구하기
	public ArrayList<ReservationDto> findReservationsBySno( int sno ) {
		return ReservationDao.getInstance().findReservationsBySno(sno);
	}

}