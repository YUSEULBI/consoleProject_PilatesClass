package pilatesClass.controller;

import java.util.ArrayList;

import pilatesClass.model.Member.Dao;
import pilatesClass.model.Member.SalesnRankDto;
import pilatesClass.model.Member.수강내역dao;
import pilatesClass.model.Member.스케줄dto;

public class 수강내역Controller {

	
	private static 수강내역Controller con= new 수강내역Controller();
	private 수강내역Controller ( ) {};
	public static 수강내역Controller getInstance( ) {return con;}
	
	
	
	public boolean cancel(int ch) { 
		return 수강내역dao.getInstance().cancel(ch);
	}
	
	
	public boolean reservation(int ch) {
		
		
		boolean result=수강내역dao.getInstance().reservation(ch);
		return result;
		}
	
	public ArrayList<스케줄dto> print(){
		int logsession=회원controller.getInstance().getLogSession();
		
		return 수강내역dao.getInstance().print(logsession);
	}
	
	// 매출계산
	public ArrayList<SalesnRankDto> Sales(){
		ArrayList<SalesnRankDto> list = 수강내역dao.getInstance().Sales();
		System.out.println(list);
		return list;
	}
	
	public boolean re_check(int ch) {
		return 수강내역dao.getInstance().re_check(ch);
	}
	
	public int pay(int money,int ch) {//결제 및 거스름돈
		
		return 수강내역dao.getInstance().pay(money, ch);
	}
	
}