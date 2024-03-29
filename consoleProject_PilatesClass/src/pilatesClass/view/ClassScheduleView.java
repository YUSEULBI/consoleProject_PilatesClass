package pilatesClass.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import pilatesClass.controller.MessageController;
import pilatesClass.controller.PointController;
import pilatesClass.controller.ReservationController;
import pilatesClass.controller.ClassScheduleController;
import pilatesClass.controller.MemberController;
import pilatesClass.model.ClassScheduleDao;
import pilatesClass.model.ClassScheduleDto;
import pilatesClass.model.RefundDto;
import pilatesClass.model.ReservationDto;

public class ClassScheduleView {

	private static ClassScheduleView view = new ClassScheduleView();
	private ClassScheduleView() {	}
	public static ClassScheduleView getInstance() {	return view;	}
	
	Scanner scanner = new Scanner(System.in);
	
	public void classPage() {
		while(true) {
			
			classView();
			System.out.println("1.수업등록 2.수업변경 3.수업삭제 4.지난수업 5.뒤로가기");
			try {
				int ch = scanner.nextInt();
				if ( ch == 1 ) { classAdd();	}
				else if ( ch == 2 ) { classEdit();	 }
				else if ( ch == 3 ) { classDelete();	}
				else if ( ch == 4 ) { completedClassView();	}
				else if ( ch == 5 ) { break;	}
			}catch (Exception e) {
				System.out.println(e);
				scanner = new Scanner(System.in);
			}
		}
	}
	
	public void completedClassView() {
		System.out.println("================= 완료된 수업목록 ==================");
		System.out.printf("%s\t%-10s\t%s\t %s\n","수업번호","수강일시","금액","강사");
		ArrayList<ClassScheduleDto> classList = ClassScheduleController.getInstance().completedClassView();
		if ( classList != null) {
			for ( ClassScheduleDto d : classList) {
				String DateTime =  d.getSdate();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
				LocalDateTime time = LocalDateTime.parse(DateTime,dtf);
				String time2 = time.format(dtf2);
				int bPrice = d.getSprice();
				DecimalFormat df = new DecimalFormat("#,##0원");
				 
				System.out.printf("%d\t%s\t%s\t %s\n",d.getSno(),time2,df.format(bPrice),d.getTeacherName());
			}
		}
		System.out.println("==============================================");
		System.out.println("아무키나 입력하십시오 (뒤로가기)");
		try {
			scanner.next();
		}catch (Exception e) {
			System.out.println(e);
			scanner=new Scanner(System.in);
		}
	}
	
	public void classView() {
		System.out.println("================= 전체 수업목록 ==================");
		System.out.printf("%s\t%-10s\t%s\t %s\n","수업번호","수강일시","금액","강사");
		ArrayList<ClassScheduleDto> classList = ClassScheduleController.getInstance().classView();
		if ( classList != null) {
			for ( ClassScheduleDto d : classList) {
				LocalDateTime time = LocalDateTime.parse(d.getSdate(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				String time2 = time.format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
				DecimalFormat df = new DecimalFormat("#,##0원");
				System.out.printf("%d\t%s\t%s\t %s\n",d.getSno(),time2,df.format(d.getSprice()),d.getTeacherName());
			}
		}
		System.out.println("==============================================");
	}
	
	public ClassScheduleDto classAddandEditInput() {
		
		// 날짜
		System.out.print("년[yyyy] : ");		int year = scanner.nextInt();
		if ( year < 1 || year > 9999 ) 		{ System.out.println("[연도를 올바르게 입력하세요]"); return null;	}
		System.out.print("월 : ");			int month = scanner.nextInt();
		if ( month > 12 || month < 1) 		{ System.out.println("[날짜를 올바르게 입력하세요]"); return null;	}
		System.out.print("일 : ");			int day = scanner.nextInt();
		if ( day < 1 ){ System.out.println("[날짜를 올바르게 입력하세요]"); return null;	}
		System.out.print("시[24시간 기준] : ");int hour = scanner.nextInt();
		if ( hour > 24 || hour < 0 ) 		{ System.out.println("[날짜를 올바르게 입력하세요]"); return null;	}
		System.out.print("분 : ");			int minute = scanner.nextInt();
		if ( minute > 60 || minute < 0 )	{ System.out.println("[날짜를 올바르게 입력하세요]"); return null;	}
		
		// 날짜 유효성검사
		LocalDateTime time = LocalDateTime.of(year, month, day, hour, minute); // 입력일시 LocalDateTime
		LocalDateTime now = LocalDateTime.now(); //현재시간
		Duration duration = Duration.between(now, time);
		if ( duration.toDays() < 0 ) { System.out.println("[지난 날짜는 수업을 등록할 수 없습니다.]"); return null;	} // 일자가 과거이면 null;
		if ( duration.toHours() < 0 ) { System.out.println("[지난 시간은 수업을 등록할 수 없습니다.]"); return null;	} // 일시가 과거이면 null;
		if ( duration.toDays() > 30 ) { System.out.println("[한달 이내 날짜만 등록할 수 있습니다.]"); return null;	} // 입력날짜가 현재날짜보다 한달초과면 null;
		
		// 금액,강사명
		System.out.print("금액 : "); int price = scanner.nextInt();
		if ( price < 30000  ) { System.out.println("[3만원 이상의 수강료를 기입해주세요.]"); return null; } // 기본금액 3만원 이상
		System.out.print("강사명 : "); String tName = scanner.next();
		if ( !(MemberController.getInstance().teacher_NumFind(tName))) { System.out.println("[존재하지 않는 강사입니다.]"); return null; } // 강사이름이 없으면 null;
		
		// DB저장용 일시
		String stime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
		return new ClassScheduleDto(stime, price, tName);
	}
	
	public void classAdd() {
		System.out.println("================ 수업등록 페이지 ================");
		ClassScheduleDto scheduleDto = classAddandEditInput();
		if ( scheduleDto != null ) {
			boolean result = ClassScheduleController.getInstance().classAdd(scheduleDto);
			if ( result ) { System.out.println("[수업이 등록되었습니다.]");	}
			else { System.out.println("[수업이 등록실패] - 관리자 문의");	}
		}
	}
	
	public void classEdit() throws Exception {
		System.out.println("================ 수업수정 페이지 ================");
		System.out.println("수정할 스케줄번호 입력하세요 : ");
		int sno = scanner.nextInt();
		ClassScheduleDto scheduleDto = classAddandEditInput();
		if ( scheduleDto != null ) {
			int result = ClassScheduleController.getInstance().classEdit(scheduleDto, sno);
			if ( result == 1 ) { System.out.println("["+sno+"번 수업을 변경했습니다.]");	}
			else if ( result == 2 ) { System.out.println("[강사명을 바르게 입력하세요.]");	}
			else if ( result == 3 ) { System.out.println("[수업 변경 실패] - 관리자 문의");	}
		}
	}
	
	//스케줄을 삭제하면 수강내역이 사라짐 / 사라지기전에 해당 스케줄을 수강한 회원들에게 삭제 알림을 안내해야할 것 같다.
	public void classDelete() {
		System.out.println("================ 수업삭제 페이지 ================");
		System.out.println("삭제할 스케줄번호를 입력하세요"); int sno = scanner.nextInt();

		//메시지 발송 및 환불
		byte refundResult = refundCancelledClass(sno);
		if ( refundResult == 2 ) { System.out.println("[수업삭제 실패]-관리자문의,환불실패"); return; }
		
		// 수업삭제
		boolean result = ClassScheduleController.getInstance().classDelete(sno);
		if ( result ) { System.out.println("["+sno+"번 수업을 삭제했습니다.]"); } else { System.out.println("[수업삭제 실패]-관리자문의"); }	
	}
	
	public byte refundCancelledClass( int sno ) {
		// 취소해야할 rno(예약번호), mno(예약자) 구하기 
		ArrayList<ReservationDto> reservationDtoList = ReservationController.getInstance().findReservationsBySno(sno);
		// 환불, 환불정보 반환
		if ( reservationDtoList == null ) { System.out.println("예약회원이 없습니다."); return 1; } // 환불할 필요없이 수업삭제 하면 됨.
		ArrayList<RefundDto> refundDtoList = PointController.getInstance().refundPointCancelledClass(reservationDtoList);
		
		// 안내메시지 보내기 ( 수업취소, 환불내용 )
		if ( refundDtoList == null ) { System.out.println("[포인트 환불 실패] - 관리자문의"); return 2; }
		
		String messageResult = MessageController.getInstance().CancellationAndRefundMessage(refundDtoList , sno);
		if( messageResult != "" ) { System.out.println("[메시지전송실패]-"+messageResult+"번 회원 메시지전송실패"); } // 이미 환불처리 되었기 때문에 return하지않음
		else { System.out.println("취소환불메시지 전송완료"); }
		
		return 3;
	}

	public void te_print() {
		System.out.println("==================강사 수업목록===================");
		System.out.printf("%s\t %10s\t %10s %6s \n","수업번호","수강일시","금액","강사");
		ArrayList<ClassScheduleDto> relist=ClassScheduleController.getInstance().te_print();
		
		if( relist == null ) { return; }
		for(ClassScheduleDto d: relist) {
			System.out.printf("%d\t%s\t%d\t%s \n",d.getSno(),d.getSdate(),d.getSprice(),d.getTeacherName());
		}
	}
	
	
	
}
