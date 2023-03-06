package pilatesClass.view;

import java.util.Scanner;

import pilatesClass.controller.회원controller;

public class Front {
	 
	
	private static Front front=new Front();
	private Front() {};
	 

	public static Front getInstance() {
		return front;
	}


	Scanner scanner=new Scanner(System.in);
	
	public void index() {
		while (true) {	
			System.out.println("     PilatesClass");
			System.out.println("┊　　┊　　┊　　┊　　┊　　┊　　┊\r\n"
					+ "┊　　┊　　┊　　☆　 ┊　　┊　　┊\r\n"
					+ "┊　　┊　　✬ 　  　✬　　┊ 　┊\r\n"
					+ "┊　　★ 　  　  　  　★ 　┊\r\n"
					+ "☆ 　  　  　  　  　  　☆");
			System.out.println(" 1.관리자페이지 2.회원페이지");
			int ch = scanner.nextInt();
			if ( ch == 1 ) { 회원view.getInstance().admin_login();	}
			else if ( ch == 2 ) { member_page();	}
		}
		
	}
	
	public void member_page() {
		
		while (true) {
			System.out.println("1.회원가입  2.로그인  3.아이디찾기  4.비밀번호찾기" );
			int ch=scanner.nextInt();
			if(ch==1) { 회원view.getInstance().signup ();}
			else if(ch==2) {회원view.getInstance().login(); }	
			else if(ch==3) {회원view.getInstance().findId();}	
			else if(ch==4) {회원view.getInstance().findPw();}	
			
		}
	}
	

	

	
	public void student_page() { // 수강생 로그인 완료후 페이지가 되어야할것 강사로그인 성공시 예약이 없기때문
		while (true) {
			MessageView.getInstance().messageCount(); // 메시지 알림 추가 ////////////
			System.out.printf("1.예약 2.예약내역보기 3.로그아웃 4.메시지"); // 메시지 추가 ////////////
			int ch=scanner.nextInt();
			if(ch==1) { 수강내역View.getInstance().pay();}
			else if(ch==2) {
				수강내역View.getInstance().res_print();
				cancel_page(); //학생은 예약을 하기때문에 취소가 가능
			}else if (ch==3) {	logout();		}
			else if (ch==4) {	MessageView.getInstance().message_Page();		}
		}

}
	
	public void teacher_page() { // 수강생 로그인 완료후 페이지가 되어야할것 강사로그인 성공시 예약이 없기때문
		while (true) {
			
			System.out.printf("1.나의수업보기 2.로그아웃");
			int ch=scanner.nextInt();
			if(ch==1) {
				스케줄View.getInstance().te_print(); //강사는 예약이 없음->취소도없음(취소는관리자만 가능)
			}
			else if(ch==2) {logout();}
		}
		
	}
	
	public void logout() {
	 	 회원controller.getInstance().setLogSession(0);
		 int login=회원controller.getInstance().getLogSession(); //확인용 주석처리할것
		 System.out.println("로그세션확인:"+login);//확인용 주석처리할것
		 System.out.println("정상 로그아웃 되었습니다~!");
		 member_page();
	 
	}

	
	
 public void cancel_page() { //취소하기 페이지
	 System.out.println("1.수업취소 2.뒤로가기");
	 int ch=scanner.nextInt();
	 if(ch==1) {수강내역View.getInstance().cancel(); }
	 else if (ch==2) {
		 스케줄View.getInstance().alphaClassView();
		 student_page(); //이렇게했을때 문제가 있는지 확인할것
	}
 }
 
 
 public void pay() {//결제하기페이지
	 System.out.println("1.결제하기 2.취소하기");
	 int ch=scanner.nextInt();
	 if(ch==1) {
		 수강내역View.getInstance().pay();
	 }else if (ch==2) {
		 스케줄View.getInstance().alphaClassView(); 
		Front.getInstance().student_page();
	}
	 
 }
 
 //////////////////////////////////////////////////////////
 
 // 관리자페이지

		// 관리자 로그인
 		// [파일위치 : 회원View] --> 관리자페이지[파일위치 : Front]
		
 		// 관리자페이지
		public void admin_page() {
			while(true) {
				System.out.println("================ 관리자 페이지 ================");
				System.out.println();
				System.out.println("1.수업조회 2.회원조회 3.강사조회 4.매출관리 5.로그아웃");
				System.out.println();
				System.out.println("===========================================");
				try {
					int sc = scanner.nextInt();
					if ( sc == 1 ) { 스케줄View.getInstance().classPage();	}
					else if ( sc == 2 ) { 회원view.getInstance().PMemberView();	}
					else if ( sc == 3 ) { 회원view.getInstance().PteacherView();	}
					else if ( sc == 4 ) { SalesView.getInstance().sales_page();	} //매출관리
					else if ( sc == 5 ) { break;	}
					else {	}
				}catch (Exception e) {
					System.out.println("[메뉴 번호에 맞게 입력하세요.]");
					scanner=new Scanner(System.in);
				}
			}
			
		}
		
	
	
}