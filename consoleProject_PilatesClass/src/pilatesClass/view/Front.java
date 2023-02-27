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
		회원view 회원view=new 회원view();
		while (true) {
			System.out.println("1.회원가입  2.로그인");
			int ch=scanner.nextInt();
			if(ch==1) { 회원view.signup ();}
			else if(ch==2) {회원view.login(); }	
			
		}
	}

}
