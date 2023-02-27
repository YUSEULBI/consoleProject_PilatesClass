package pilatesClass.view;

import java.util.Scanner;

import 과제.과제팀플.Controller;

public class 수강내역View {

	
	private static 수강내역View view=new 수강내역View();
	private 수강내역View() {};
	public static 수강내역View getInstance() {return view;}
	
	Scanner scanner=new Scanner(System.in);
	
	public void index() {
		while(true) {
			System.out.println("1.일반회원[회원가입] 2.강사[강사등록]");
			int ch=scanner.nextInt();
			if(ch==1) {signup();}
			else if(ch==2){}
		}//while e
		
	}
	
	
	//회원가입
	public void signup() {
		
		System.out.println("아이디: "); String 아이디=scanner.next();
		System.out.println("비밀번호: "); String 비밀번호=scanner.next();
		System.out.println("전화번호: "); String 전화번호=scanner.next();
		System.out.println("이름: "); String 이름=scanner.next();
		System.out.println("등급: "); int 등급=scanner.nextInt();
		

	if(result) {
		System.out.println("회원가입성공");
	}else {
		System.out.println("회원가입 실패");
	}
	
	}
	
	
	
	
	
	
	
	
}
