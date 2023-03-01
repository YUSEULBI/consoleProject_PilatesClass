package pilatesClass.view;

import java.util.Scanner;

public class Front {
	
	
	private static Front front=new Front();
	private Front() {};
	

	public static Front getInstance() {
		return front;
	}


	Scanner scanner=new Scanner(System.in);
	
	public void index() {
		while (true) {
			System.out.println("1.관리자페이지 2.회원페이지");
			int ch = scanner.nextInt();
			if ( ch == 1 ) { admin_page();	}
			else if ( ch == 2 ) { member_page();	}
		}
		
	}
	
	public void member_page() {
		회원view 회원view=new 회원view();
		while (true) {
			System.out.println("1.회원가입  2.로그인");
			int ch=scanner.nextInt();
			if(ch==1) { 회원view.signup ();}
			else if(ch==2) {회원view.login(); }	
			
		}
	}
	
	public void admin_page() {
		System.out.println("1.수업조회 2.회원조회 3.강사조회");
		int sc = scanner.nextInt();
		if ( sc == 1 ) { 스케줄View.getInstance().classPage();	}
		else if ( sc == 2 ) {	}
		else if ( sc == 3 ) {	}
		
	}
	
	

}
