package pilatesClass.view;

import java.util.Scanner;

import pilatesClass.controller.회원controller;
import pilatesClass.controller.회원controller;
import pilatesClass.model.Member.회원dao;
import pilatesClass.model.Member.회원dao;

public class 회원view {
	
	Scanner scanner=new Scanner(System.in);

	public void signup() {//회원가입
			
			System.out.println("아이디:"); String 아이디=scanner.next();
			System.out.println("비밀번호:"); String 비밀번호=scanner.next();
			System.out.println("전화번호:");	String 전화번호=scanner.next();
			System.out.println("이름:");	String 이름=scanner.next();
			System.out.println("회원=> 1입력 , 관리자=> 2입력"); int 등급=scanner.nextInt();
			
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
				System.out.println("일반 회원 로그인성공");
				스케줄View.getInstance().classView(); //
			}else if(result==0) {
				System.out.println("비밀번호가 잘못되었습니다.");
			}else if (result==-1) {
				System.out.println("없는 회원 입니다.");
			}else if (result==2) {
				System.out.println("관리자회원 로그인 성공");
				스케줄View.getInstance().classView();
			}
			
			int login=회원controller.getInstance().getLogSession();
			System.out.println("login2:"+login);
					
		}
		
		
		
		
}
		

		
		
	
	

