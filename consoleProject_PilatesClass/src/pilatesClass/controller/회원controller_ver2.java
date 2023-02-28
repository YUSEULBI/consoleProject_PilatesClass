package pilatesClass.controller;

import pilatesClass.model.Member.회원dao_ver2;
import pilatesClass.model.Member.회원dto;

public class 회원controller_ver2 {
	
	private static 회원controller_ver2 controller=new 회원controller_ver2();
	private 회원controller_ver2() {}
	public static 회원controller_ver2 getInstance() {
		return controller;
	}
	
	public boolean signup(String 아이디,String 비밀번호,String 전화번호,String 이름,int 등급) {
		
		회원dto 회원=new 회원dto(0, 아이디, 비밀번호, 전화번호, 이름, 등급);
		
		boolean result=
				회원dao_ver2.getInstance().signup(회원);
		
		
		return result;
		
		
	}
	

	
	public int login(String 아이디,String 비밀번호) {
		
		return 회원dao_ver2.getInstance().login(아이디, 비밀번호);
		
	}
	
	
	

}
