package pilatesClass.view;

import java.util.Scanner;

import pilatesClass.controller.PointController;
import pilatesClass.controller.회원controller;


public class PointView {
	private static PointView pointView = new PointView();
	private PointView() {
		// TODO Auto-generated constructor stub
	}
	public static PointView getInstance() {
		return pointView;
	}
	//
	Scanner scanner = new Scanner(System.in);
	
	// 사용할 포인트 물어보기
	public int wannaUsePoint( int amount ) {
		while(true) {
			//현재 포인트 체크
			int point = PointController.getInstance().pointCheck();
			//포인트가 없으면 return 0
			if ( point < 100 ) { System.out.println("사용가능 포인트가 없습니다. 포인트: "+point); return 0;	}
			//포인트 사용안내
			System.out.println("[결제예정금액 : " + amount +"]");
			System.out.println("현재 포인트 : " + point);
			if ( point >= 100 ) {
				System.out.println("포인트는 100원 단위로 사용가능합니다.");
				System.out.println("포인트사용을 원치않으시면 0을 입력해주세요");
				System.out.println("사용할 포인트 : ");
				int wannaPoint = scanner.nextInt();
				if ( wannaPoint == 0 ) { return 0;	}
				if ( wannaPoint%100 != 0) { System.out.println("[100원 단위만 사용가능합니다. 다시 입력해주세요.]"); continue;	}
				// 포인트가 결제금액보다 많으면 사용할 포인트는 결제금액과 동일하게 변경
				if ( amount < wannaPoint ) { 
					wannaPoint = amount; 
					System.out.println("결제금액보다 포인트가 많습니다. 결제금액 만큼만 포인트를 사용합니다.");
					System.out.println("사용예정 포인트 : "+wannaPoint );
				}
				
				return wannaPoint;
			}
		}
		
	}
	
	//포인트 차감한 결제금액 안내
	public void payMoney_info( int money ) {
		System.out.println("[결제예정금액 : "+money+"원]");
	}
	
	//포인트 사용
	public void pointUse( int point ) {
		boolean result = PointController.getInstance().pointUse( point );
		// 포인트 사용할때만 안내.
		if ( result && point>0 ) { System.out.println(point +"포인트 사용했습니다.");	}
	}
	
	// 결제후 포인트적립
	public void addPoint( int amount ) {
		String reason = "결제금액의 10% 포인트적립 [결제금액"+amount+"]";
		int point = PointController.getInstance().addPoint(amount, reason );
		if ( point == -1 ) { System.out.println("[포인트적립 실패] - 관리자문의 ");	}
		else { System.out.println(point+" 포인트 적립되었습니다."); }
		
	}
	
	// 포인트조회
	public void viewPoint() {
		while(true) {
			//현재 포인트 체크
			int point = PointController.getInstance().pointCheck();
			System.out.println(회원controller.getInstance().findName()+" 회원님의 포인트 : "+point+" point");
			System.out.println("1. 뒤로가기");
			try {
				int ch = scanner.nextInt();
				if ( ch == 1 ) { return;	}
			}catch (Exception e) {
				System.out.println(e);
				scanner=new Scanner(System.in);
			}
		}
	}
}