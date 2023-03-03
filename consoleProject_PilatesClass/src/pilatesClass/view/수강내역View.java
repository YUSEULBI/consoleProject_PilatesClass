package pilatesClass.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import pilatesClass.controller.수강내역Controller;
import pilatesClass.controller.회원controller;
import pilatesClass.model.Member.SalesnRankDto;
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
		System.out.printf("%s\t %10s\t %10s %6s \n","수강번호","수강일시","금액","강사");
		ArrayList<스케줄dto> relist=수강내역Controller.getInstance().print();
		for(스케줄dto d: relist) {
			System.out.printf("%d\t%s\t%d\t%s \n",d.get스케줄번호(),d.get수강일시(),d.get금액(),d.get강사명());
		}
	}
	
	
	public void cancel() {//취소
		System.out.println("취소하실 수강내역번호를 선택해주세요"); int ch=scanner.nextInt();
		
		boolean result=수강내역Controller.getInstance().cancel(ch);
		if(result==true) {
			System.out.println("수업취소완료");
		}else {
			System.out.println("수업 취소 실패");
		}
		
	}
	
	/////////////////////////////////////////////////////////////////
	// 관리자페이지
	
	// 월별/일자별 매출관리 페이지
	public void sales_page() {
		while (true) {
			System.out.println("1.일자별 매출  2.월별 매출 ");
			try {
				int ch = scanner.nextInt();
				if ( ch == 1 ) { dailySales();	}
				else if ( ch == 2 ) { monthSales();	}
				else if ( ch == 3 ) { return; }
			}catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	
	// 일자별 매출
	public void dailySales() throws Exception {
	int year = Calendar.getInstance().get(Calendar.YEAR);
	int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		while(true) {
			System.out.println("================== 월별 매출 ===================");
			System.out.printf("%d년 %d월 매출\n",year,month);
			ArrayList<SalesnRankDto> list = 수강내역Controller.getInstance().Sales();
			for ( SalesnRankDto dto : list ) {
				System.out.println(dto.get아이디());
			}
			System.out.println("=============================================");
			System.out.println("1.이전달 2.다음달");
			int ch = scanner.nextInt();
			if ( ch == 1 ) {
				month--;
				if ( month<1) { year--; month=12;	}
			}
			else if ( ch == 2 ) { 
				month++;
				if ( month>12 ) { year++; month =1; }
			}
			else if ( ch == 3 ) { return; 	}
		}
	}
	
	// 월별 매출
	public void monthSales() throws Exception {
		System.out.println("================== 일자별 매출 ===================");
		
	}
	
	
	

	
}