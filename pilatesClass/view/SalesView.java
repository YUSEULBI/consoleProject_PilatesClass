package pilatesClass.view;

import java.text.DecimalFormat;
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
		while (true) {
			System.out.println("================= 매출 관리 ===================");
			System.out.println();
			total();
			System.out.println();
			System.out.println("=============================================");
			System.out.println("1.월별 매출  2.일자별 매출 3.뒤로가기");
			try {
				int ch = scanner.nextInt();
				if ( ch == 1 ) { monthSales();	}
				else if ( ch == 2 ) { dailySales(); }
				else if ( ch == 3 ) { return; }
			}catch (Exception e) { 	System.out.println(e); 	scanner=new Scanner(System.in);}
		}
	}
	
	//총 예약수
	public void total() {
		System.out.printf("%s\t%s\n","누적예약건","총매출액");
		System.out.println("--------------------");
		SalesDto dto = SalesController.getInstance().total();
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
				cal.set(year, month-1, 1);
				day++;
				if ( day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					month++;
					if ( month > 12 ) { year++; month = 1; 	}
					day = 1;
				}// if end
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