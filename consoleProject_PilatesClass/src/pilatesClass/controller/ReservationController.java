package pilatesClass.controller;

import java.util.ArrayList;

import pilatesClass.model.ReservationDao;
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
		return ReservationDao.getInstance().reservation(ch);
		}
	
	public ArrayList<ClassScheduleDto> print(){
		int logsession=MemberController.getInstance().getLogSession();
		return ReservationDao.getInstance().print(logsession);
	}
	
		
	public boolean re_check(int ch) {
		return ReservationDao.getInstance().re_check(ch);
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
	

	
	
}