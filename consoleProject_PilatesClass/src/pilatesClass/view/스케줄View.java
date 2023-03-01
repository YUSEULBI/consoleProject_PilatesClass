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
			System.out.println("1.수업등록 2.수업수정 3.수업삭제");
			int ch = scanner.nextInt();
			if ( ch == 1 ) { classAdd();	}
			else if ( ch == 2 ) { classEdit();	 }
			else if ( ch == 3 ) { classDelete();	}
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
	
	public void classEdit() {
		
		
	}
	
	public void classDelete() {
		
	}
	
}

