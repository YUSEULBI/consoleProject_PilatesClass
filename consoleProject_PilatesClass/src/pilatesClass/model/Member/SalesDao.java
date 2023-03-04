package pilatesClass.model.Member;

public class SalesDao extends Dao {
	
	private static SalesDao dao = new SalesDao();
	private SalesDao() { 	}
	public static SalesDao getInstance() { return dao;  }
	
	// 누적예약수,총매출액 [ count(*) , sum(스케줄.금액) ]
	public TotalDto total() {
		TotalDto dto = new TotalDto();
		String sql = "select count(*) as 누적예약수 , sum(스케줄.금액) as 누적매출액 from 회원,스케줄,수강내역 where 회원.회원번호_pk = 스케줄.회원번호_fk and 스케줄.스케줄번호_pk = 수강내역.스케줄번호_fk ;";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if ( rs.next()) { dto = new TotalDto(rs.getInt(1), rs.getInt(2)); }
			
		}catch (Exception e) {
			System.out.println(e);
		}return dto;
		
	}
	
	// 월별매출액
	public TotalDto monthTotal( int year , int month ) {
		
		/*
		 * String smonth = "0"; if ( month < 10 ) { smonth = "0"+ month; } else { smonth
		 * = String.valueOf(month); } System.out.println("년 : "+year +" / 월 : "+smonth);
		 */
		TotalDto dto = new TotalDto();
		String sql ="select count(*) as 해당월예약건 , sum(스케줄.금액) as 해당월총매출액 "
				+ "from 스케줄,수강내역 "
				+ "where 스케줄.스케줄번호_pk = 수강내역.스케줄번호_fk "
				+ "and date_format(수강일시,'%Y') = ? "
				+ "and date_format(수강일시,'%m') = ? ;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				dto = new TotalDto(rs.getInt(1), rs.getInt(2));
			}
		}catch (Exception e) { System.out.println(e); }
		
		return dto;
	}
}
