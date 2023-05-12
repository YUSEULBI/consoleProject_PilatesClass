package pilatesClass.view;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import pilatesClass.controller.수강내역Controller;
import pilatesClass.controller.회원controller;
import pilatesClass.model.수강내역dao;
import pilatesClass.model.스케줄dto;


public class 수강내역View {

	
	private static 수강내역View view=new 수강내역View();
	private 수강내역View() {};
	public static 수강내역View getInstance() {return view;}
	
	Scanner scanner=new Scanner(System.in);
	
	
	
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
	
	
	  public void pay() {//결제 및 거스름돈
		  
		System.out.println("수강번호를 입력해 주세요");int ch=scanner.nextInt();
		
		boolean result1=re_check(ch);
		if(result1==false) {
			return;
		}//이미 등록한 수업이 아니면 밑에 실행
		
		// 결제금액 조회 ( amount = 결제예정금액 )
		int amount = 수강내역Controller.getInstance().payMoneyCheck(ch);
		
		//사용할 포인트
		int Point =  PointView.getInstance().wannaUsePoint(amount);
		// 선택한 포인트만큼 결제금액 차감
		amount = amount-Point;
		//결제금액 안내
		PointView.getInstance().payMoney_info(amount);
		
		
		System.out.println("지불금액을 써주세용"); int money=scanner.nextInt();
		
		int result=수강내역Controller.getInstance().pay(Point,money, ch);
		if(result==-1) {
			//사용한 포인트 차감
			PointView.getInstance().pointUse(Point);
			//포인트추가
			PointView.getInstance().addPoint(amount);
			//예약
			reservation(ch);
		}else if (result==-2) {
			System.err.println("금액이 부족합니다");
			
		}else if (result>0) {//거스름돈은 0보다 클꺼니까!
			NumberFormat nf=NumberFormat.getNumberInstance();
			System.out.println("거스름돈은 : "+nf.format(result)+"원 입니다."); //3번째 자리 콤마찍기
			
			//사용한 포인트 차감
			PointView.getInstance().pointUse(Point);
			//포인트추가
			PointView.getInstance().addPoint(amount);
			
			reservation(ch);
		}
		
		
	}
	
	public void reservation(int ch){
		
		boolean result=수강내역Controller.getInstance().reservation(ch);
		if(result) {System.out.println("수강할 수업이 등록되었습니다.");}
	}
	
	
	public boolean re_check(int ch) { // 수업등록 유효성
		if(수강내역dao.getInstance().re_check(ch) == false) {
			
			System.err.println("이미 등록하신 수업입니다.");
			
			return false;
			
		}else {
			return true;
		}
		
	}
	
	
	
	
	
	
	
	

	
}