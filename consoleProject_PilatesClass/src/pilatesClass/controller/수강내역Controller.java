package pilatesClass.controller;

import java.util.ArrayList;

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
	int logsession=회원controller.getInstance().getLogSession();
	boolean result=수강내역dao.getInstance().reservation(logsession,ch);
	return result;
	}
	
	
	public ArrayList<스케줄dto> print(){
		int logsession=회원controller.getInstance().getLogSession();
		return 수강내역dao.getInstance().print(logsession);
	}
	
	
}



