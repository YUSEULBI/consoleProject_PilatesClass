package pilatesClass.view;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import pilatesClass.controller.스케줄Controller;
import pilatesClass.model.Member.스케줄dao;
import pilatesClass.model.Member.스케줄dto;

public class 스케줄View {

	private static 스케줄View view = new 스케줄View();
	private 스케줄View() {
		// TODO Auto-generated constructor stub
	}
	public static 스케줄View getInstance() {
		return view;
	}
	
	Scanner scanner = new Scanner(System.in);
	
	public void classPage() {
		while(true) {
			classView();
			System.out.println("1.수업등록 2.수업변경 3.수업삭제");
			try {
				int ch = scanner.nextInt();
				if ( ch == 1 ) { classAdd();	}
				else if ( ch == 2 ) { classEdit();	 }
				else if ( ch == 3 ) { classDelete();	}
			}catch (Exception e) {
				System.out.println(e);
				scanner = new Scanner(System.in);
			}
		}
	}
	
	public void classView() {
		System.out.println("=========== 전체 수업목록 ===========");
		System.out.printf("%s\t%-15s\t%s\t%s\n","수업번호","수강일시","금액","강사");
		ArrayList<스케줄dto> classList = 스케줄Controller.getInstance().classView();
		for ( 스케줄dto d : classList) {
			System.out.printf("%d\t%s\t%d\t%s\n",d.get스케줄번호(),d.get수강일시(),d.get금액(),d.get강사명());
		}
		System.out.println("=================================");
	}
	
	public 스케줄dto classAddandEditInput() {
		System.out.print("연도 : ");	int year = scanner.nextInt();
		System.out.print("월 : ");	int month = scanner.nextInt();
		System.out.print("일 : ");	int day = scanner.nextInt();
		System.out.print("시 : ");	int hour = scanner.nextInt();
		System.out.print("분 : ");	int minute = scanner.nextInt();
		
		
		System.out.print("금액 : "); int price = scanner.nextInt();
		System.out.print("강사명 : "); String tName = scanner.next();
		LocalDateTime time = LocalDateTime.of(year, month, day, hour, minute);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//2023-02-24 11:00:00
		String stime = time.format(dtf);
		
		return (new 스케줄dto(0, stime, price, tName));
		
	}
	
	public void classAdd() {
		
		boolean result = 스케줄Controller.getInstance().classAdd(classAddandEditInput());
		if ( result ) { System.out.println("[수업이 등록되었습니다.]");	}
		else { System.out.println("[수업이 등록실패] - 관리자 문의");	}
		
	}
	
	public void classEdit() throws Exception {
		System.out.println("수정할 스케줄번호 입력하세요 : ");
		int ch = scanner.nextInt();
		int result = 스케줄Controller.getInstance().classEdit(classAddandEditInput(), ch);
		if ( result == 1 ) { System.out.println("["+ch+"번 수업을 변경했습니다.]");	}
		else if ( result == 2 ) { System.out.println("[강사명을 바르게 입력하세요.]");	}
		else if ( result == 3 ) { System.out.println("[수업 변경 실패] - 관리자 문의");	}
		
	}
	
	//스케줄을 삭제하면 수강내역이 사라짐 / 사라지기전에 해당 스케줄을 수강한 회원들에게 삭제 알림을 안내해야할 것 같다.
	public void classDelete() {
		System.out.println("삭제할 스케줄번호를 입력하세요");
		int ch = scanner.nextInt();
		int result = 스케줄Controller.getInstance().classDelete(ch);
		if ( result == 1 ) { System.out.println("["+ch+"번 수업을 삭제했습니다.]");	}
		else if ( result == 2 ) { System.out.println("[존재하지 않는 수업번호 입니다.]");	}
		else if ( result == 3 ) { System.out.println("[수업삭제 실패]-관리자문의");	}
		
				
	}
	
}

