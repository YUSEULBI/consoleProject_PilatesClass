package pilatesClass.controller;

import java.util.ArrayList;

import pilatesClass.model.RankDto;
import pilatesClass.model.MemberDao;
import pilatesClass.model.MemberDto;

public class MemberController {
	
	private static MemberController controller=new MemberController();
	private MemberController() {}
	public static MemberController getInstance() {
		return controller;
	}
	
	public boolean signup(String 아이디,String 비밀번호,String 전화번호,String 이름,int 등급) {
		
		MemberDto 회원=new MemberDto(0, 아이디, 비밀번호, 전화번호, 이름, 등급);
		
		boolean result=
				MemberDao.getInstance().signup(회원);
		
		
		return result;
		
		
	}
	
	// 회원출력
	public ArrayList<MemberDto> PMemberView(){
		
		return MemberDao.getInstance().PMemberView(1);
		
	}
	
	// 강사출력
		public ArrayList<MemberDto> PteacherView(){
			
			return MemberDao.getInstance().PMemberView(2);
			
		}

	
	private int logSession; //로그 세션 int
	
	
	public int getLogSession() { //로그세션 게터
		return logSession;
	}
	
	
	public void setLogSession(int logSession) {
		this.logSession = logSession;
	}
	
	public int login(String 아이디,String 비밀번호) {//로그인
		
		return MemberDao.getInstance().login(아이디, 비밀번호);
		
	}
	
	public String findId(String 이름 , String 전화번호) {//아이디찾기
		
		return MemberDao.getInstance().findId(이름, 전화번호);
	}
	
	public String findPw(String 아이디 , String 이름) {//비밀번호찾기
		
		return MemberDao.getInstance().findPw(아이디, 이름);
	}
	
	public String findName() {
		return MemberDao.getInstance().findName();
	}
	
	
	// 패스워드확인
	public boolean checkPw( String mpw ) {
		int mno = getLogSession();
		return MemberDao.getInstance().checkPw(mno, mpw);
	}
	
	
	// 회원탈퇴
	public boolean deleteMember() {
		int mno = getLogSession();
		boolean result = MemberDao.getInstance().deleteMember(mno);
		if ( result ) { setLogSession(0); } // 로그아웃
		return result;
	}
	
	public ArrayList<RankDto> teacherRank(){
		return MemberDao.getInstance().getTchRank();
	}
	
	///관리자로그인//////////////////////////////////////////////////////
	public boolean admin_login( String pw ) {
		return MemberDao.getInstance().admin_login(pw);
	}
	
	// 존재하는 강사명인지 확인하기{}
		public boolean teacher_NumFind( String name ) {
			int result = MemberDao.getInstance().teacher_NumFind(name);
			if ( result == -1 ) { return false;	}
			else { return true;	}
			
		}
	
	// 회원번호로 회원명 알기
	public String memberNameFind( int num ) {
		return MemberDao.getInstance().memberNameFind(num);
	}
	
	// 회원명으로 회원번호 찾기
	public int memberNoFind( String name ) {
		return MemberDao.getInstance().memberNoFind(name);
	}

}
