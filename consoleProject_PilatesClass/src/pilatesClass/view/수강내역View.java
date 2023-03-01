package pilatesClass.view;

import java.util.Scanner;

import pilatesClass.controller.수강내역Controller;


public class 수강내역View {

	
	private static 수강내역View view=new 수강내역View();
	private 수강내역View() {};
	public static 수강내역View getInstance() {return view;}
	
	Scanner scanner=new Scanner(System.in);
	
	public void reservation(){
		System.out.println("수강번호 를 입력해주세요");int ch=scanner.nextInt();
		 int result=수강내역Controller.getInstance().reservation(ch);
		 if(result == 1) {System.out.println("[수강할 수업이 등록되었습니다.]");}
		 else if ( result == 2 ) {  System.out.println("[이미 예약된 수업입니다.]");}
		 else if ( result == 3 ) {  System.out.println("[예약 실패 - 관리자문의 ]");}
	}
	
	public void cancel() {//취소
		System.out.println("취소하실 수업번호를 선택해주세요"); int ch=scanner.nextInt();
		
		boolean result=수강내역Controller.getInstance().cancel(ch);
		if(result==true) {
			System.out.println("수업취소완료");
		}else {
			System.out.println("수업 취소 실패");
		}
		
	}

	
}
