package pilatesClass.model;

public class SalesDao extends Dao {
	
	private static SalesDao dao = new SalesDao();
	private SalesDao() { 	}
	public static SalesDao getInstance() { return dao;  }
	
	// 선택한 해의 누적예약수,총매출액 [ count(*) , sum(스케줄.sprice) ]
	public SalesDto yearTotal( int year ) {
		SalesDto dto = new SalesDto();
		String sql = "select count(*) as 누적예약수 , "
					+ " sum(s.sprice) as 누적매출액 "
					+ " from classschedule s , reservation r "
					+ " where s.sno = r.sno "
					+ " and s.sdate like \""+year+"%\"";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if ( rs.next()) { dto = new SalesDto(rs.getInt(1), rs.getInt(2)); }
			
		}catch (Exception e) { System.out.println(e); }
		return dto;
	}
	
	// 월별 매출액
	public SalesDto monthTotal( int year , int month ) {
		SalesDto dto = new SalesDto();
		String sql ="select count(*) as 해당월예약건 , sum(s.sprice) as 해당월총매출액 "
				+ " from classschedule s ,reservation r where s.sno = r.sno "
				+ " and date_format(sdate,'%Y') = ? and date_format(sdate,'%m') = ? ";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			rs = ps.executeQuery();
			if ( rs.next() ) { dto = new SalesDto(rs.getInt(1), rs.getInt(2)); }
		}catch (Exception e) { System.out.println(e); }
		return dto;
	}
	
	// 일자별 매출액
	public SalesDto dailySales( int year , int month , int day ) {
		SalesDto dto = new SalesDto();
		String sql = "select count(*) as 해당월예약건 , sum(s.sprice) as 해당월총매출액 "
				+ " from classschedule s ,reservation r where s.sno = r.sno "
				+ " and date_format(sdate,'%Y') = ? and date_format(sdate,'%m') = ? "
				+ " and date_format(sdate,'%d') = ? ";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, year); ps.setInt(2, month); ps.setInt(3, day);
			rs = ps.executeQuery();
			if ( rs.next() ) { dto = new SalesDto(rs.getInt(1), rs.getInt(2)); }
		}catch (Exception e) { 	System.out.println(e);}
		return dto;
	}
}
