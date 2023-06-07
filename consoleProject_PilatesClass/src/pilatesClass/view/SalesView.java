package pilatesClass.view;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

import pilatesClass.controller.SalesController;
import pilatesClass.model.SalesDto;



public class SalesView {
	
	// 싱글톤
	private static SalesView salesView = new SalesView();
	private SalesView() { 	}
	public static SalesView getInstance() { return salesView;	}
	
	Scanner scanner = new Scanner(System.in);
	DecimalFormat df = new DecimalFormat("#,##0원");
	/////////////////////////////////////////////////////////////////
	// 관리자페이지
	
	// 월별/일자별 매출관리 페이지
	public void sales_page() {
		int selectYear = Calendar.getInstance().get(Calendar.YEAR);
		while (true) {
			System.out.println("================= 매출 관리 ===================");
			System.out.println();
			total(selectYear);
			System.out.println();
			System.out.println("=============================================");
			System.out.println("1.월매출  2.일자별 매출 3.연매출[직접입력] 4.뒤로가기");
			try {
				int ch = scanner.nextInt();
				if ( ch == 1 ) { monthSales();	}
				else if ( ch == 2 ) { dailySales(); }
				else if ( ch == 3 ) { // 연매출 
					System.out.println("년[yyyy] : "); int year = scanner.nextInt();
					if ( year < 1900 || year > 9999 ) { System.out.println("[연도를 맞게 입력하세요.]");	}
					else if( year > Calendar.getInstance().get(Calendar.YEAR) ) {System.out.println("[연도를 맞게 입력하세요.]");}
					else { selectYear = year; }
				}
				else if ( ch == 4 ) { return; }
			}catch (Exception e) { 	System.out.println(e); 	scanner=new Scanner(System.in);}
		}
	}
	
	//올해 총 예약수
	public void total(int todayYear) {
		System.out.println(todayYear+"년");
		System.out.printf("%s\t%s\n","누적예약건","총매출액");
		System.out.println("--------------------");
		SalesDto dto = SalesController.getInstance().yearTotal(todayYear);
		System.out.printf("%s건\t%s\n",dto.get누적예약수(),df.format(dto.get총매출액()) );
	}
	
	// 월별 매출
	public void monthSales() throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		while(true) {
			System.out.println("================== 월별 매출 ===================");
			System.out.println();
			System.out.printf("[ %d년 %d월 매출 ]\n",year,month);
			SalesDto dto = SalesController.getInstance().monthTotal(year, month);
			System.out.println("--------------------");
			System.out.printf("%s\t  %s\n",month+"월 예약건",month+"월 매출액");
			System.out.println("--------------------");
			System.out.printf("%s건\t  %s\n",dto.get누적예약수(),df.format(dto.get총매출액()) );
			System.out.println();
			System.out.println("=============================================");
			System.out.println("1.이전달 2.다음달 3.직접날짜입력 4.뒤로가기");
			
			try {
				int ch = scanner.nextInt();
				if ( ch == 1 ) { month--; if ( month<1) { year--; month=12;	} 	}
				else if ( ch == 2 ) {
					 // 유효성검사 : 입력날짜가 금월보다 많으면 안됨.
					LocalDate currentDate = LocalDate.now();
					LocalDate selectDate = LocalDate.of(year, month+1 , 1);
					if( selectDate.isAfter(currentDate) ) { System.out.println("[금월까지 조회 가능합니다.]"); continue; }
					
					// 다음달로 넘기기
					month++;
					if ( month>12 ) { year++; month =1; }
				}
				else if ( ch == 3 ) {  	
					LocalDate currentDate = LocalDate.now();
					year = Calendar.getInstance().get(Calendar.YEAR); // 현재년도로 변경
	
					System.out.println(currentDate);
					System.out.println("년[yyyy] : "); int selectYear = scanner.nextInt();
					
					// 입력년도가 현재년도보다 크거나 1900보다 낮으면 안됨.
					if ( selectYear < 1900 || selectYear > year ) { System.err.println("[조회가능한 년도를 입력해주세요.]"); continue;	}
					
					// 1월보다 적거나 12월보다 많으면 안됨.
					System.out.println("월 : "); int selectMonth = scanner.nextInt();
					if ( month > 12 || month < 1 ) { System.err.println("[형식에 맞게 월을 입력하세요.]"); continue;}
					
					// 입력된 값이 지금연월보다 많으면 안됨.
					LocalDate selectDate = LocalDate.of(selectYear, selectMonth, 1);
					if( selectDate.isAfter(currentDate) ) { System.err.println("[금월까지 조회 가능합니다.]"); continue; }
					
					// 유효성검사후 선택한 연월로 변경
					year = selectYear;
					month = selectMonth;
				}
				else if ( ch == 4 ) { return; 	}
			} catch (Exception e) {
				System.err.println("올바른 형식으로 입력해주세요");
			}
		}
	}
	
	// 일자별 매출
	public void dailySales() throws Exception {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		while(true) {
			System.out.println("================= 일자별 매출 ==================");
			System.out.println();
			System.out.println(year+"년 "+month+"월 "+day+"일");
			SalesDto dto = SalesController.getInstance().dailySales(year, month, day);
			System.out.println("--------------------");
			System.out.printf("%s\t  %s\n","예약건","매출액");
			System.out.println("--------------------");
			System.out.printf("%s건\t  %s\n",dto.get누적예약수(),df.format(dto.get총매출액()) );
			System.out.println();
			System.out.println("=============================================");
			System.out.println("1.이전날 2.다음날 3.직접날짜입력 4.뒤로가기");
			int ch = scanner.nextInt();
			if ( ch == 1 ) { 
				day--; 
				if ( day < 1 ) {
					month--;
					if( month < 1 ) { year--; month = 12; }// if end
					cal.set(year, month-1, 1);
					day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					
				}// if end
			}// if end
			else if ( ch == 2 ) {
				int selectday = day+1;
				// 오늘보다 다음날로는 못가도록 - 선택한날짜가 오늘보다 많으면 안됨.
				if ( !isPastDate(year, month, selectday) ) {  System.err.println("[금일까지 조회가능합니다.]");
				}else {
					// 다음날짜구하기
					cal.set(year, month-1, 1);
					day = selectday;
					if ( day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) { // 그달에 마지막날 보다 크면
						month++; // 다음달로 변경
						if ( month > 12 ) { year++; month = 1; 	} // 다음달이 12보다 크면 다음해로 넘김, 1월로 변경
						day = 1; // 다음달이 되면 day는 1로
					}// if end
				}
			}// if end
			else if ( ch == 3 ) {	
				System.out.println("년[yyyy] : "); int selectYear = scanner.nextInt();
				if ( selectYear < 1900 || selectYear > 9999 ) { System.err.println("[연도를 맞게 입력하세요.]"); continue;	}
				System.out.println("월 : "); int selectMonth = scanner.nextInt();
				if ( selectMonth > 12 || selectMonth < 1 ) { System.err.println("[달을 맞게 입력하세요.]"); continue;}
				System.out.println("일 : "); int selectDay = scanner.nextInt();
				
				// 날짜가 금일 이후인지 확인
				if ( !isPastDate(selectYear, selectMonth, selectDay) ) { System.out.println("매출은 금일까지 조회가능합니다."); continue; }
				cal.set(year, month, 1);
				if ( day < 1 || day > cal.getMaximum(Calendar.DAY_OF_MONTH) ) { System.err.println("[존재하지 않는 날짜입니다.]"); continue; }
				
			}else if ( ch == 4 ) { return; }
		}// while end
	} // 일자별 매출 end
	
	// 오늘보다 날짜가 지났는지 확인하는 함수
	public boolean isPastDate(int selectYear , int selectMonth , int selectDay ) {
		try {
			LocalDate inputDate = LocalDate.of(selectYear, selectMonth, selectDay);
			LocalDate currentDate = LocalDate.now();
			if ( inputDate.isAfter(currentDate) ) { return false; }	
			
		} catch (Exception e) { System.err.println("[날짜형식을 올바르게 입력해주세요]");		}
		return true;	
	}
	
}