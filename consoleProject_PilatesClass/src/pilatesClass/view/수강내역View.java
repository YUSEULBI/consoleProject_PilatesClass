package pilatesClass.view;

import java.util.ArrayList;
import java.util.Scanner;

import pilatesClass.controller.수강내역Controller;
import pilatesClass.model.Member.수강내역dao;
import pilatesClass.model.Member.스케줄dto;


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
	
	
	public void res_print() {
		System.out.println("==================나의 수강목록===================");
		System.out.printf("%s\t %s\t %s\t %s \n","수업번호","수강일시","금액","강사");
		ArrayList<스케줄dto> relist=수강내역Controller.getInstance().print();
		for(스케줄dto d: relist) {
			System.out.printf("%d\t%s\t%d\t%s \n",d.get스케줄번호(),d.get수강일시(),d.get금액(),d.get강사명());
		}
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
