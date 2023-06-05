package pilatesClass.controller;

import java.util.ArrayList;

import pilatesClass.model.ReservationDao;
import pilatesClass.model.ClassScheduleDto;

public class ReservationController {

	
	private static ReservationController con= new ReservationController();
	private ReservationController ( ) {};
	public static ReservationController getInstance( ) {return con;}
	
	
	
	public boolean cancel(int ch) { 
		return ReservationDao.getInstance().cancel(ch);
	}
	
	
	public boolean reservation(int ch) {
		
		
		boolean result=ReservationDao.getInstance().reservation(ch);
		return result;
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
	
}