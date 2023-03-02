package pilatesClass.controller;

import pilatesClass.model.Member.수강내역dao;

public class 수강내역Controller {

	
	private static 수강내역Controller con= new 수강내역Controller();
	private 수강내역Controller ( ) {};
	public static 수강내역Controller getInstance( ) {return con;}
	
	
	
	public boolean cancel(int ch) { 
		return 수강내역dao.getInstance().cancel(ch);
	}
	
	
	public int reservation(int ch) {
	int loginsession = 회원controller.getInstance().getLogSession();
	int result=수강내역dao.getInstance().reservation(loginsession , ch );
	return result;
	}
	
	
	
}



