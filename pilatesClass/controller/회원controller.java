package pilatesClass.controller;

import java.util.ArrayList;

import pilatesClass.model.RankDto;
import pilatesClass.model.회원dao;
import pilatesClass.model.회원dto;

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
	
	// 회원출력
	public ArrayList<회원dto> PMemberView(){
		
		return 회원dao.getInstance().PMemberView(1);
		
	}
	
	// 강사출력
		public ArrayList<회원dto> PteacherView(){
			
			return 회원dao.getInstance().PMemberView(2);
			
		}

	
	private int logSession; //로그 세션 int
	
	
	public int getLogSession() { //로그세션 게터
		return logSession;
	}
	
	
	public void setLogSession(int logSession) {
		this.logSession = logSession;
	}
	
	public int login(String 아이디,String 비밀번호) {//로그인
		
		return 회원dao.getInstance().login(아이디, 비밀번호);
		
	}
	
	public String findId(String 이름 , String 전화번호) {//아이디찾기
		
		return 회원dao.getInstance().findId(이름, 전화번호);
	}
	
	public String findPw(String 아이디 , String 이름) {//비밀번호찾기
		
		return 회원dao.getInstance().findPw(아이디, 이름);
	}
	
	public String findName() {
		return 회원dao.getInstance().findName();
	}
	
	public ArrayList<RankDto> teacherRank(){
		return 회원dao.getInstance().getTchRank();
	}
	
	///관리자로그인//////////////////////////////////////////////////////
	public boolean admin_login( String pw ) {
		return 회원dao.getInstance().admin_login(pw);
	}
	
	// 존재하는 강사명인지 확인하기{}
		public boolean teacher_NumFind( String name ) {
			int result = 회원dao.getInstance().teacher_NumFind(name);
			if ( result == -1 ) { return false;	}
			else { return true;	}
			
		}
	
	// 회원번호로 회원명 알기
	public String memberNameFind( int num ) {
		return 회원dao.getInstance().memberNameFind(num);
	}
	
	// 회원명으로 회원번호 찾기
	public int memberNoFind( String name ) {
		return 회원dao.getInstance().memberNoFind(name);
	}

}
