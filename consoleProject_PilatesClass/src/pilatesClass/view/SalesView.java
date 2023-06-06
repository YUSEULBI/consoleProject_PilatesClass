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
			int ch = scanner.nextInt();
			if ( ch == 1 ) { month--; if ( month<1) { year--; month=12;	} 	}
			else if ( ch == 2 ) { month++; if ( month>12 ) { year++; month =1; }  }
			else if ( ch == 3 ) {  	
				System.out.println("년[yyyy] : "); year = scanner.nextInt();
				if ( year < 1900 || year > 9999 ) { System.out.println("[연도를 맞게 입력하세요.]"); return;	}
				System.out.println("월 : "); month = scanner.nextInt();
				if ( month > 12 || month < 1 ) { System.out.println("[달을 맞게 입력하세요.]"); return;}
			}
			else if ( ch == 4 ) { return; 	}
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
				// 오늘보다 다음날로는 못가도록
				LocalDate inputDate = LocalDate.of(year, month, day);
				LocalDate currentDate = LocalDate.now();
				if ( inputDate.isEqual(currentDate) ) { // 현재 날짜와 오늘날짜가 동일하면
					System.out.println("매출은 오늘날짜까지 조회가능합니다.");
				}else {
					// 다음날짜구하기
					cal.set(year, month-1, 1);
					day++;
					if ( day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) { // 그달에 마지막날 보다 크면
						month++; // 다음달로 변경
						if ( month > 12 ) { year++; month = 1; 	} // 다음달이 12보다 크면 다음해로 넘김, 1월로 변경
						day = 1; // 다음달이 되면 day는 1로
					}// if end
				}
			}// if end
			else if ( ch == 3 ) {	
				System.out.println("년[yyyy] : "); year = scanner.nextInt();
				if ( year < 1900 || year > 9999 ) { System.out.println("[연도를 맞게 입력하세요.]"); return;	}
				System.out.println("월 : "); month = scanner.nextInt();
				if ( month > 12 || month < 1 ) { System.out.println("[달을 맞게 입력하세요.]"); return;}
				System.out.println("일 : "); day = scanner.nextInt();
				cal.set(year, month, 1);
				if ( day < 1 || day > cal.getMaximum(Calendar.DAY_OF_MONTH) ) {
					System.out.println("[존재하지 않는 날짜입니다.]"); return;
				}
			}else if ( ch == 4 ) { return; }
		}// while end
	} // 일자별 매출 end
	

	
}