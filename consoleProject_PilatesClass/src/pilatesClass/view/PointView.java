package pilatesClass.view;

import java.util.ArrayList;
import java.util.Scanner;

import pilatesClass.controller.PointController;
import pilatesClass.model.PointDto;
import pilatesClass.model.RankDto;
import pilatesClass.controller.MemberController;


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
				System.out.println("100원 단위로 포인트 사용가능. 포인트 미사용시 결제금액의 1% 적립.");
				System.out.println("포인트사용을 원치않으시면 0을 입력해주세요");
				System.out.println("사용할 포인트 : ");
				int wannaPoint = scanner.nextInt();
				if ( wannaPoint == 0 ) { return 0;	}
				if ( wannaPoint%100 != 0) { System.out.println("[100원 단위만 사용가능합니다. 다시 입력해주세요.]"); continue;	}
				if ( wannaPoint > point ) { System.out.println("[보유포인트를 초과하는 금액입니다. 다시 입력해주세요.]"); continue; }
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
	

	
	//포인트 사용
	public void pointUse( int point , int rno ) {
		
		boolean result = PointController.getInstance().pointUse( point , rno );
		// 포인트 사용할때만 안내.
		if ( result ) { System.out.println(point +"포인트 사용했습니다.");	}
		
	}
	
	// 결제후 포인트적립
	public void addPoint( int amount , int reservationRno ) {
		int point = PointController.getInstance().addPoint(amount, reservationRno );
		if ( point == -1 ) { System.out.println("[포인트적립 실패] - 관리자문의 ");	}
		else { System.out.println(point+" 포인트 적립되었습니다."); }
		
	}
	
	// 포인트조회
	public void viewPoint() {
		while(true) {
			//현재 포인트 체크
			int point = PointController.getInstance().pointCheck();
			if ( point >= 0 ) { System.out.println(MemberController.getInstance().findName()+" 회원님의 포인트 : "+point+" point"); }
			else if ( point == -1 ) { System.out.println("관리자문의 오류번호-1"); }
			else if ( point == -2 ) { System.out.println("관리자문의 오류번호-2"); }
			System.out.println("1. 뒤로가기");
			System.out.println("2. 포인트 이력");
			try {
				int ch = scanner.nextInt();
				if ( ch == 1 ) { return;	}
				else if ( ch == 2 ) { viewPointHistory();	}
			}catch (Exception e) {
				System.out.println(e);
				scanner=new Scanner(System.in);
			}
		}
	}
	
	// 포인트 이력 출력
	public void viewPointHistory() {
		
		System.out.println("================= 포인트이력 =================");
		System.out.printf("%-20s\t%s\t%s\n","날짜","포인트","상세");
		ArrayList<PointDto> PointList = PointController.getInstance().printPointHistory();
		if ( PointList != null ) {
			for(PointDto point : PointList ) {
				String pointvalue = null;
				if ( point.getPointvalue() > 0 ) { pointvalue = "+"+point.getPointvalue(); }
				else if ( point.getPointvalue() < 0 ) { pointvalue = Integer.toString( point.getPointvalue() ); }
				System.out.printf("%-20s\t%s\t%s\n" , point.getDaterecord() , pointvalue , point.getReason() );	
			}
		}
		System.out.println("============================================");
		System.out.println("아무키나 입력하십시오 (뒤로가기)");
		try {
			scanner.next();
		}catch (Exception e) {
			System.out.println(e);
			scanner=new Scanner(System.in);
		}
	}
}