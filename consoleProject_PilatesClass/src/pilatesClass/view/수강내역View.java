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
		 boolean result=수강내역Controller.getInstance().reservation(ch);
		 if(result) {System.out.println("수강할 수업이 등록되었습니다.");}
		 else {System.out.println("[실패]");}
		
	}
	
	

	
}
