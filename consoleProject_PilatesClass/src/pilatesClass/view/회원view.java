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
				System.out.println(회원dao.getInstance().findName()+" 회원님 어서오세요!");
				스케줄View.getInstance().classView(); 
				Front.getInstance().reservation_page(); //수강생 로그인 성공시 예약하기 및 예약보기가 나와야함
				Front.getInstance().logout();
			}else if(result==0) {
				System.out.println("비밀번호가 잘못되었습니다.");
			}else if (result==-1) {
				System.out.println("없는 회원 입니다.");
			}else if (result==2) {
				System.out.println(회원dao.getInstance().findName()+" 강사님 어서오세요!");
				스케줄View.getInstance().classView();
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
		
		
		
}
		

		
		
	
	

