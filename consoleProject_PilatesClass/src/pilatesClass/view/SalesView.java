package pilatesClass.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import pilatesClass.controller.SalesController;
import pilatesClass.controller.수강내역Controller;
import pilatesClass.model.Member.SalesnRankDto;
import pilatesClass.model.Member.TotalDto;

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
			System.out.println("================== 누적 매출 ===================");
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
			}catch (Exception e) { 	System.out.println(e); 	}
		}
	}
	
	//총 예약수
	public void total() {
		System.out.printf("%s\t%s\n","누적예약건","총매출액");
		System.out.println("--------------------");
		TotalDto dto = SalesController.getInstance().total();
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
			TotalDto dto = SalesController.getInstance().monthTotal(year, month);
			System.out.println("--------------------");
			System.out.printf("%s\t  %s\n",month+"월 예약건",month+"월 매출액");
			System.out.println("--------------------");
			System.out.printf("%s건\t  %s\n",dto.get누적예약수(),df.format(dto.get총매출액()) );
			System.out.println();
			System.out.println("=============================================");
			System.out.println("1.이전달 2.다음달 3.뒤로가기");
			int ch = scanner.nextInt();
			if ( ch == 1 ) { month--; if ( month<1) { year--; month=12;	} 	}
			else if ( ch == 2 ) { month++; if ( month>12 ) { year++; month =1; }  }
			else if ( ch == 3 ) { return; 	}
		}
	}
	
	// 일자별 매출
	public void dailySales() throws Exception {
	System.out.println("================== 일자별 매출 ===================");
	
	}
	
	
	
}
