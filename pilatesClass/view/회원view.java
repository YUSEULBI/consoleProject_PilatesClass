package pilatesClass.view;

import java.util.ArrayList;
import java.util.Scanner;

import pilatesClass.controller.회원controller;
import pilatesClass.model.RankDto;
import pilatesClass.model.회원dao;
import pilatesClass.model.회원dto;
import pilatesClass.controller.회원controller;

public class 회원view {
	
	private static 회원view 회원view = new 회원view();
	private 회원view() { }
	public static 회원view getInstance() { 	return 회원view; 	}
	
	Scanner scanner=new Scanner(System.in);

	public void signup() {//회원가입
			
			System.out.println("아이디:"); String 아이디=scanner.next();
			System.out.println("비밀번호:"); String 비밀번호=scanner.next();
			System.out.println("전화번호:");	String 전화번호=scanner.next();
			System.out.println("이름:");	String 이름=scanner.next();
			System.out.println("회원=> 1입력 , 강사=> 2입력"); int 등급=scanner.nextInt();
			
			boolean result=
					회원controller.getInstance().signup(아이디, 비밀번호, 전화번호, 이름, 등급);
			
			if(result==true) {
				System.out.println("회원가입성공");
			}else {
				System.out.println("가입실패");
			}
	}
			
	
			
			
			

		
		public void login() {//로그인
			
			
			
			System.out.println("아이디:"); String 아이디=scanner.next();
			System.out.println("비밀번호:"); String 비밀번호=scanner.next();
			
			int result=
					회원controller.getInstance().login(아이디, 비밀번호);
			if(result==1) {
				System.out.println(회원controller.getInstance().findName()+" 회원님 어서오세요!");
				
				Front.getInstance().student_page(); //수강생 로그인 성공시 예약하기 및 예약보기가 나와야함
			}else if(result==0) {
				System.out.println("비밀번호가 잘못되었습니다.");
			}else if (result==-1) {
				System.out.println("없는 회원 입니다.");
			}else if (result==2) {
				System.out.println(회원dao.getInstance().findName()+" 강사님 어서오세요!");
				
				Front.getInstance().teacher_page();
				//강사 로그인 성공시 예약은 x 본인의 수업만 출력해야함 reservation_page(); 사용불가
				
			}
			
				//로그세션에 회원pk 정상대입되는지 확인
			//int login=회원controller.getInstance().getLogSession();
			//System.out.println("login2:"+login);
					
		}
		
		public void findId() {//아이디 찾기
			System.out.println("이름:"); String 이름=scanner.next();
			System.out.println("전화번호:"); String 전화번호=scanner.next();
			String id=회원controller.getInstance().findId(이름, 전화번호);
			if(id==null) {
				System.err.println("잘못된 정보 입니다");
			}else {
				System.out.println("찾으실 아이디는:"+id);
			}
			
			
		}
		
		public void findPw() {//비밀번호 찾기
			System.out.println("아이디:"); String 아이디=scanner.next();
			System.out.println("이름:"); String 이름=scanner.next();
			String 비밀번호=회원controller.getInstance().findPw(아이디, 이름);
			if(비밀번호==null) {
				System.err.println("잘못된 정보 입니다");
			}else {
				System.out.println("찾으실 비밀번호는:"+비밀번호);
			}
			
			
		}
		
		public void getTchRank() {
			System.out.println("================= 강사 인기순위 =================");
			System.out.printf("%s\t%s\t%s\n","강사이름","누적수강생","랭킹");
			ArrayList<RankDto> teacherRankList = 회원controller.getInstance().teacherRank();
			for(RankDto rn : teacherRankList ) {
				System.out.printf("%s\t%d\t%s\n" , rn.get회원이름() , rn.get예약수() , rn.get랭킹() );	
			}
			System.out.println("============================================");
			System.out.println("아무키나 입력하십시오 (뒤로가기)");
			try {
				String ch = scanner.next();
			}catch (Exception e) {
				System.out.println(e);
				scanner=new Scanner(System.in);
			}
			
		}
//////////////////////////////////////////////////////////
		// 관리자페이지

		// 관리자로그인
		public void admin_login() {
			System.out.println("================ 관리자 로그인 ================");
			System.out.println("비밀번호 : ");		String pw = scanner.next();
			boolean result =  회원controller.getInstance().admin_login(pw);
			if ( result ) { System.out.println("[관리자 로그인 성공]");	Front.getInstance().admin_page(); }
			else { System.out.println("[관리자 로그인 실패]");	}
		}
		
		// 회원조회
		public void PMemberView() {
			while(true) {
				String RaitingName;
				System.out.println("================= 회원 목록 =================");
				System.out.printf("%s\t%s\t%-15s\t%s\n","아이디","이름","전화번호","등급");
				ArrayList<회원dto> PMemberList = 회원controller.getInstance().PMemberView();
				for (회원dto Pm : PMemberList) {
					if(Pm.get등급() == 1) {RaitingName="일반회원";}
					else {RaitingName=null;}
					System.out.printf("%s\t%s\t%s\t%s\n",Pm.get아이디() , Pm.get이름() , Pm.get전화번호() , RaitingName);
				}
				System.out.println("==========================================");
				
				System.out.println("1.뒤로가기 2.처음으로 3.메시지보내기"); 
				int 채널 = scanner.nextInt();
				if(채널 == 1) { break;	}
				else if(채널 == 3) { MessageView.getInstance().adminMessage_page();}//메시지 보내기 기능
				else {System.out.println(
						".╭◜◝ ͡ ◜◝\r\n"
						+ "(    ´ㅅ` )\r\n"
						+ "╰◟◞ ͜  「제대로 입력해주세요」 :)\r\n"
						+ "");}
			}
		}
		
		// 강사조회
		public void PteacherView() {
			while(true) {
				String RaitingName;
				System.out.println("================= 회원 목록 =================");
				System.out.printf("%s\t%s\t%-15s\t%s\n","아이디","이름","전화번호","등급");
				ArrayList<회원dto> PteacherList = 회원controller.getInstance().PteacherView();
				for (회원dto Pm : PteacherList) {
					if(Pm.get등급()==2) {RaitingName="강사";}
					else {RaitingName=null;}
					System.out.printf("%s\t%s\t%s\t%s\n",Pm.get아이디() , Pm.get이름() , Pm.get전화번호() , RaitingName);
				}
				System.out.println("==========================================");
				
				System.out.println("1.뒤로가기 2.처음으로"); int 채널 = scanner.nextInt();
				if(채널 == 1) {Front.getInstance().admin_page();	}
				else if(채널 == 2) {Front.getInstance().index();}
				else {System.out.println(
						".╭◜◝ ͡ ◜◝\r\n"
						+ "(    ´ㅅ` )\r\n"
						+ "╰◟◞ ͜  「제대로 입력해주세요」 :)\r\n"
						+ "");}
			}
		}			
}
		

		
		
	
	
