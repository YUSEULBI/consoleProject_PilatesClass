package pilatesClass.model;

public class PointDao extends Dao {
	private static PointDao dao = new PointDao();
	private PointDao() {
		// TODO Auto-generated constructor stub
	}
	public static PointDao getInstance() {
		return dao;
	}
	
	
	// 보유포인트 리턴
	public int pointCheck( int loginsession ) {
		String sql = "select sum(point.point) from point where 회원번호_fk = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, loginsession);
			rs = ps.executeQuery();
			if ( rs.next() ) { return rs.getInt(1);	}
			else { return -1; }
		}catch (Exception e) {
			System.out.println("pointCheck 예외"+e);
		}return -2;
	}
	
	
	// 포인트 적립
	public boolean addPoint( int point  , String reason , int loginsession ) {
		
		String sql = "insert into point(point,reason,회원번호_fk) values( ? , ? , ? );";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, point);
			ps.setString(2, reason);
			ps.setInt(3, loginsession);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}return false;
		
		
	}
	
	// 포인트 사용
	public boolean pointUse( int point , String reason , int loginsession ) {
		
		String sql = "insert into point(point,reason,회원번호_fk) values( ? , ? , ? );";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, -point);
			ps.setString(2, reason);
			ps.setInt(3, loginsession);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}return false;
	}
}
