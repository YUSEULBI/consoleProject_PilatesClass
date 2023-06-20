package pilatesClass.view;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import pilatesClass.controller.ReservationController;
import pilatesClass.controller.ClassScheduleController;
import pilatesClass.controller.MemberController;
import pilatesClass.controller.PointController;
import pilatesClass.model.ReservationDao;
import pilatesClass.model.ClassScheduleDto;
import pilatesClass.model.RefundDto;


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
		System.out.println("취소하실 수업번호를 선택해주세요"); int sno =scanner.nextInt();
		
		// 수업이 본인이 예약한 수업인지 확인 , rno반환 , rno는 포인트차감시 사용
		int rno = ReservationController.getInstance().checkSno(sno);
		if ( rno < 1 ) { System.out.println("예약하신 수강번호가 아닙니다."); return; }
		
		// 취소 불가능한 수업인지 확인 
		boolean checkResult = ClassScheduleController.getInstance().checkCancelAvailability(sno);
		if ( checkResult == false ) { System.out.println("이미 사용한 수업입니다."); return; }
		
		
		// 포인트처리 /////////////////////////////////////////////////////////////////////////////////////////////
			// 예약시 적립했던 포인트 차감 // 수업취소시 포인트테이블 rno가 null이 되기 때문에 취소전에 포인트차감
		
		// 포인트환불,회수처리
		RefundDto refundDto = PointController.getInstance().cancelPoint( rno , (byte)1 );
		System.out.println("refundDto : "+refundDto);
		
		// 수업 금액 
		int refundAmount = ClassScheduleController.getInstance().classAmount(sno); 
		
		String refundDetails = "=====================환불상세내역======================\n"
				+ "[수업금액] : "+refundAmount+"원\n";
		
		// 회수해아할 포인트
		if ( refundDto.getCanceledAccumulatedPoints() > 0  ) {
			refundDetails += "[예약시 적립포인트 적립취소 됩니다.] : "+ refundDto.getCanceledAccumulatedPoints()+"포인트\n";
			
			int deductedPoints = 0;

			// 보유표인트에서 회수
			if ( refundDto.getDeductedPoints() > 0 ) {
				
				// 1. 모든포인트를 보유포인트에서 회수함
				if ( refundDto.getDeductedPoints() == refundDto.getCanceledAccumulatedPoints() ) {
					refundDetails += "[보유포인트에서 전액 적립취소되었습니다.] : "+ (-refundDto.getDeductedPoints())+"원\n";
					// deductedPoints = 0;
					
				// 2. 보유포인트가 부족해서 적립취소해야할 포인트 전부 회수하지 못함 => 환불금액에서 차감해야함.
				}else if ( refundDto.getDeductedPoints() < refundDto.getCanceledAccumulatedPoints() ) {
					deductedPoints = refundDto.getCanceledAccumulatedPoints()-refundDto.getDeductedPoints();
					refundDetails += "[보유포인트에서 적립포인트 적립취소] : "+ (-refundDto.getDeductedPoints())+"원\n";
					refundDetails += "[나머지 적립취소해야할 포인트 환불금액에서 차감] : "
							+  refundAmount + "-" + deductedPoints +"포인트\n";
				}
			}else { // 보유포인트가 0원이라 아예 회수 못함 , 회수해야할 포인트 전체 환불금액에서 차감
				deductedPoints = refundDto.getCanceledAccumulatedPoints();
				refundDetails += "[적립취소할 보유포인트가 없으므로 환불금액에서 적립포인트를 제하고 환불됩니다.]\n"
						+ refundAmount + "원 - " + deductedPoints +"포인트적립취소\n";
			}
			//refundAmount = refundAmount-deductedPoints;
			refundDetails += "--------------------------[환불금액] : 총 "+(refundAmount-deductedPoints)+"원\n";
			
			
		// 환불해야할 포인트
		}else if ( refundDto.getUsedPoints() > 0 ) {
			refundDetails += "[사용포인트 환불적립] : "+"+"+refundDto.getUsedPoints()+"\n";
			refundDetails += "-------------------------[환불금액] : 총 " + refundAmount +"원\n";
		}else {
			refundDetails += "--------------------------------------------------\n"
					+ "-------------------------[환불금액] : 총 " + refundAmount +"원\n";
		}
		
		
//////////////////////////////////////////////////////////////////////////////////////
		
		// 오류없이 포인트처리 완료시 수업취소
		if ( refundDto.isRefundSuccess() ) { 
			// 수업취소
			boolean result=ReservationController.getInstance().cancel(sno);
			if(result==true) {
				System.out.println("수업 취소가 완료되었습니다.");
				System.out.println(refundDetails);
				
			}else {
				System.out.println("수업 취소 실패 - 관리자문의");
			}
		}else {
			System.out.println("[예약취소]포인트 요청 실패");
		}
		
	}
	
	
	  public void pay() {//결제 및 거스름돈
		  
		System.out.println("수강번호를 입력해 주세요");int ch=scanner.nextInt();
		
		// 존재하는 수업인지 확인
		if ( ClassScheduleController.getInstance().checkExistSchedule(ch) == false ) {
			System.err.println("존재하지 않는 수업번호입니다."); return;
		}
		
		// 등록 가능한 수업인지 확인
		if ( ClassScheduleController.getInstance().checkClassDateTime(ch) == false ) {
			System.err.println("수업목록에 있는 수업번호를 선택해주세요."); return;
		}

		// 이미 등록한 수업이 아니면 밑에 실행
		if( ReservationController.getInstance().re_check(ch) == false){ 
			System.err.println("이미 수강한 수업입니다."); return; 
		}
		

		// 결제금액 조회 ( amount = 결제예정금액 )
		int amount = ReservationController.getInstance().payMoneyCheck(ch);
		
		// 사용할 포인트 물어보기
		int point =  PointView.getInstance().wannaUsePoint(amount);
		// 선택한 포인트만큼 결제금액 차감
		amount = amount-point;

		//포인트 차감한 결제금액 안내
		System.out.println("[결제예정금액 : "+amount+"원]");
		
		System.out.println("지불금액을 써주세요"); int money=scanner.nextInt();
		
		int result=ReservationController.getInstance().pay(point,money, ch);
		if (result==-2) {
			System.err.println("금액이 부족합니다");
			
		}else if(result==-1) {
			// 예약, 포인트사용, 포인트적립
			reservation(ch , point , amount );
			
		}else if (result>0) {//거스름돈은 0보다 클꺼니까!
			NumberFormat nf=NumberFormat.getNumberInstance();
			System.out.println("거스름돈은 : "+nf.format(result)+"원 입니다."); //3번째 자리 콤마찍기
			
			// 예약, 포인트사용, 포인트적립
			reservation(ch , point , amount );
		}
		
		

		
		
	}
	
	public void reservation(int ch , int point , int amount ){
		
		int reservationRno = ReservationController.getInstance().reservation(ch);
		if( reservationRno > 0 ) {
			System.out.println("수강할 수업이 등록되었습니다.");
			
			if ( point > 0 ) { // 포인트를 사용할 경우에만 포인트 차감
				//사용한 포인트 차감
				PointView.getInstance().pointUse(point , reservationRno);					
			}else {
				//포인트 사용을 안했을 경우에만 포인트 적립
				PointView.getInstance().addPoint(amount , reservationRno );
			}
		}
		else { System.out.println("수강 등록 실패 - 관리자문의"); }

	}
	
	
	public boolean re_check(int ch) { // 수업등록 유효성
		if(ReservationController.getInstance().re_check(ch) == false) {
			
			System.err.println("이미 등록하신 수업입니다.");
			
			return false;
			
		}else {
			return true;
		}
		
	}
	
	
	
	
	
	
	
	

	
}