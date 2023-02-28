package pilatesClass.controller;

import pilatesClass.model.Member.회원dao;
import pilatesClass.model.Member.회원dto;

public class 회원controller {
	
	private static 회원controller controller=new 회원controller();
	private 회원controller() {}
	public static 회원controller getInstance() {
		return controller;
	}
	
	public boolean signup(String 아이디,String 비밀번호,String 전화번호,String 이름,int 등급) {
		
		회원dto 회원=new 회원dto(0, 아이디, 비밀번호, 전화번호, 이름, 등급);
		
		boolean result=
				회원dao.getInstance().signup(회원);
		return result;
		
		
	}
	

	
	
	public int login(String 아이디,String 비밀번호) {
		회원dto 회원dto=회원dao.getInstance().login(아이디, 비밀번호);
		if(회원dto.get등급()==1) {
			return 1;//일반회원 로그인 성공
		}else if (회원dto.get등급()==2) {
			return 2;//관리자회원 로그인 성공
		}else if (회원dto==null) {
			return -1;//없는회원
		}
		return 0;//비밀번호가 잘못됨
		
	}
	
	
	

}
