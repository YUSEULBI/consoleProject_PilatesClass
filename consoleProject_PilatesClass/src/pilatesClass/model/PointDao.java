package pilatesClass.model;

import java.util.ArrayList;

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
		String sql = "select sum(point.pointvalue) from point where mno = ?;";
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
	public boolean addPoint( int point  , String reason , int loginsession , int rno ) {
		
		String sql = "insert into point(pointvalue,reason,mno,rno) values( ? , ? , ? , ? );";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, point);
			ps.setString(2, reason);
			ps.setInt(3, loginsession);
			ps.setInt(4, rno);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}return false;
		
		
	}
	
	// 포인트 사용
	public boolean pointUse( int point , int loginsession, int rno ) {
		
		String sql = "insert into point(pointvalue,reason,mno,rno) values( ? , ? , ? , ? );";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, -point);
			ps.setString(2, "포인트사용[수강번호 "+rno+"번 수업예약]");
			ps.setInt(3, loginsession);
			ps.setInt(4, rno);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}return false;
	}
	
	// 예약취소시 예약시 적립된 포인트 차감
	public int cancelPoint( int rno , int loginsession ) {
		String sql = "select pointvalue from point where pointvalue > 0 and rno = ? and mno = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, rno);
			ps.setInt(2, loginsession);
			rs = ps.executeQuery();
			
			// 차감할 포인트가 있으면
			if ( rs.next() ) {
				int pointvalue = rs.getInt(1);
				// 포인트차감 ( 차감레코드 생성 )
				sql = "insert into point ( pointvalue , reason , mno , rno ) values( ? , ? , ? , ? )";
				ps = con.prepareStatement(sql);
				ps.setInt(1, -pointvalue);
				ps.setString(2, "적립취소[수강번호 "+rno+"번 예약취소]");
				ps.setInt(3, loginsession);
				ps.setInt(4, rno);
				int row = ps.executeUpdate();
				if ( row > 0 ) {
					return pointvalue;
				}
			}else { return 0; } // 차감할 포인트 없음
		} catch (Exception e) {
			System.out.println("예약취소시 포인트차감 예외발생 : "+e);
		}
		return -1; // 관리자문의
	}
	
	// 포인트 출력
	public ArrayList<PointDto> printPointHistory( int mno ){
		String sql = "select daterecord , pointvalue , reason from point where mno = "+mno;
		ArrayList<PointDto> pointList = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while( rs.next() ) {
				PointDto pointDto = new PointDto(rs.getString(1), rs.getInt(2), rs.getString(3));
				pointList.add(pointDto);
			}
		} catch (Exception e) {
			System.out.println("포인트출력 예외발생 : "+e);
		}
		return pointList;
	}
}
