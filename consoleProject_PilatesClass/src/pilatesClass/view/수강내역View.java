package pilatesClass.view;

import java.util.Scanner;

import pilatesClass.controller.수강내역Controller;


public class 수강내역View {

	
	private static 수강내역View view=new 수강내역View();
	private 수강내역View() {};
	public static 수강내역View getInstance() {return view;}
	
	Scanner scanner=new Scanner(System.in);
	
	public void index() {
		while(true) {
			System.out.println("1.일반회원[회원가입] 2.강사[강사등록]");
			int ch=scanner.nextInt();
			if(ch==1) {}
			else if(ch==2){}
		}//while e
		
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
