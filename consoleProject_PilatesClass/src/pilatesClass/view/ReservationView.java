package pilatesClass.view;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import pilatesClass.controller.ReservationController;
import pilatesClass.controller.ClassScheduleController;
import pilatesClass.controller.MemberController;
import pilatesClass.model.ReservationDao;
import pilatesClass.model.ClassScheduleDto;


public class ReservationView {

	
	private static ReservationView view=new ReservationView();
	private ReservationView() {};
	public static ReservationView getInstance() {return view;}
	
	Scanner scanner=new Scanner(System.in);
	
	
	
	public void res_print() {
		System.out.println("==================나의 수강목록===================");
		System.out.printf("%s\t %10s\t %10s %6s \n","수강번호","수강일시","금액","강사");
		ArrayList<ClassScheduleDto> relist=ReservationController.getInstance().print();
		if ( relist != null ) {
			for(ClassScheduleDto d: relist) {
				System.out.printf("%d\t%s\t%d\t%s \n",d.getSno(),d.getSdate(),d.getSprice(),d.getTeacherName());
			}
		}
	}
	
	
	public void cancel() {//취소
		System.out.println("수업 하루 전까지 취소가능합니다.");
		System.out.println("취소하실 수강내역번호를 선택해주세요"); int ch=scanner.nextInt();
		// 취소 불가능한 수업인지 확인
		boolean checkResult = ClassScheduleController.getInstance().checkCancelAvailability(ch);
		if ( checkResult == false ) { System.out.println("이미 사용한 수업입니다."); return; }
		
		// 수업취소
		boolean result=ReservationController.getInstance().cancel(ch);
		if(result==true) {
			System.out.println("수업취소완료");
		}else {
			System.out.println("수업 취소 실패");
		}
		
	}
	
	
	  public void pay() {//결제 및 거스름돈
		  
		System.out.println("수강번호를 입력해 주세요");int ch=scanner.nextInt();
		
		// 존재하는 수업인지 확인
		boolean result1 = ClassScheduleController.getInstance().checkExistSchedule(ch);
		if(result1==false) { System.out.println("존재하지 않는 수업번호입니다."); return;}
		
		// 이미 등록한 수업이 아니면 밑에 실행
		boolean result2=re_check(ch);
		if(result2==false) { System.out.println("이미 수강한 수업입니다."); return; }
		
		// 등록 가능한 수업인지 확인
		boolean result3 = ClassScheduleController.getInstance().checkClassDateTime(ch);
		if ( result3 == false ) { System.out.println("예약 가능시간이 지난 수업입니다."); return; }
		
		// 결제금액 조회 ( amount = 결제예정금액 )
		int amount = ReservationController.getInstance().payMoneyCheck(ch);
		
		//사용할 포인트
		int Point =  PointView.getInstance().wannaUsePoint(amount);
		// 선택한 포인트만큼 결제금액 차감
		amount = amount-Point;
		//결제금액 안내
		PointView.getInstance().payMoney_info(amount);
		
		
		System.out.println("지불금액을 써주세요"); int money=scanner.nextInt();
		
		int result=ReservationController.getInstance().pay(Point,money, ch);
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
		
		boolean result=ReservationController.getInstance().reservation(ch);
		if(result) {System.out.println("수강할 수업이 등록되었습니다.");}
	}
	
	
	public boolean re_check(int ch) { // 수업등록 유효성
		if(ReservationDao.getInstance().re_check(ch) == false) {
			
			System.err.println("이미 등록하신 수업입니다.");
			
			return false;
			
		}else {
			return true;
		}
		
	}
	
	
	
	
	
	
	
	

	
}