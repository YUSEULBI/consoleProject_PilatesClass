package pilatesClass.model.Member;

public class PointDao extends Dao {
	private static PointDao dao = new PointDao();
	private PointDao() {
		// TODO Auto-generated constructor stub
	}
	public static PointDao getInstance() {
		return dao;
	}
	
	
	// 포인트가 쌓인 적이 있는지 확인 , 보유포인트 리턴
	public int pointCheck( int loginsession ) {
		String sql = "select * from point where 회원번호_fk = ?;";
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
		
		
		// 기존 포인트 가져오기 / 적립한적 없으면 -1 / 오류 -2
		int beforePoint = pointCheck(loginsession);
		if ( beforePoint == -2 ) { System.out.println("포인트가져오기 오류"); return false;	}
		
		// 기존포인트 + 새로 추가할 포인트
		int resultPoint = beforePoint + point;
		System.out.println(beforePoint);
		// 첫적립시 insert
		if ( beforePoint == -1) {
			//
			String sql = "insert into point values( ? , ? , ? );";
			try {
				ps = con.prepareStatement(sql);
				ps.setInt(1, resultPoint);
				ps.setString(2, reason);
				ps.setInt(3, loginsession);
				ps.executeUpdate();
				return true;		
			}catch (Exception e) {
				System.out.println(e);
			}return false;
			
		}else { // 두번째 적립부터는 update;
			String sql = "update point set point = ? , reason= ?  where 회원번호_fk = 1;";
			try {
				ps = con.prepareStatement(sql);
				ps.setInt(1, resultPoint);
				ps.setString(2, reason);
				ps.setInt(3, loginsession);
				ps.executeUpdate();
				return true;
			} catch (Exception e) {
				System.out.println(e);
			}return false;
			
		}
		
	}
	
	// 포인트 사용
	public boolean pointUse( int point , int loginsession ) {
		int beforePoint = pointCheck(loginsession);
		if ( beforePoint == -1 ) { System.out.println("포인트가져오기 오류"); return false;	}
		point = beforePoint - point;
		String sql = "update point set point = ? where 회원번호_fk = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, point);
			ps.setInt(2, loginsession);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}return false;
	}
}
