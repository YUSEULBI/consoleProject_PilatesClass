package pilatesClass.controller;

import pilatesClass.model.Member.수강내역dao;

public class 수강내역Controller {

	
	private static 수강내역Controller con= new 수강내역Controller();
	private 수강내역Controller ( ) {};
	public static 수강내역Controller getInstance( ) {return con;}
	
	
	
	public boolean cancel(int ch) {
		return 수강내역dao.getInstance().cancel(ch);
	}
}



