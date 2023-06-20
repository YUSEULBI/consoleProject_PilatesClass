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
	
	
	// 보유포인트 조회
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
	public RefundDto cancelPoint( int rno , int loginsession , byte type ) {
		
		// 예약시 적립 또는 사용한 포인트 찾기
		String sql = "select pointvalue from point where rno = ? and mno = ?";
		//환불정보를 담을 dto
		RefundDto refundDto = new RefundDto(rno, loginsession);
		
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, rno);
			ps.setInt(2, loginsession);
			rs = ps.executeQuery();
					
			// 적립또는 사용을 했다면
			if ( rs.next() ) {
				
				// 적립포인트 또는 사용포인트
				int pointvalue = rs.getInt(1); 
				
				// 적립취소(회수) 또는 환불해줄 포인트 변수
				int negativePoints = 0;
				// 적립취소 또는 환불 내용 작성 변수
				String reason = "";
				
				// 포인트 적립했을 경우 => 적립취소(회수)
				if ( pointvalue > 0 || type == 1 ) {
					// 적립취소(회수)해야하는 총 포인트
					refundDto.setCanceledAccumulatedPoints(pointvalue);
					
					// 적립한 포인트를 다시 차감해야하기 때문에
					// 1. 보유포인트 확인
					int currentPoints = pointCheck(loginsession);
					
					// 차감할 레코드에 넣을 차감포인트 변수 negativePoints
						// 차감할 포인트는 = 적립된 포인트이다.
					negativePoints = -pointvalue;
					refundDto.setDeductedPoints(pointvalue); // 보유포인트에서 차감함
					reason = "적립취소[수강번호 "+rno+"번 예약취소]";
					
					// 적립포인트 모두를 회수하지 못하게 되는 상황
					// 결제금액 환불을 적립포인트를 회수하고 환불해줘야함.
					// 차감해야할 포인트보다 보유포인트가 적으면
					if ( currentPoints < pointvalue ) {
						// 차감할 포인트 = 적립된 포인트.
						negativePoints = -currentPoints;
						
						// 적립포인트 회수를 위해 환불정보 dto의 차감한 보유포인트 필드에 보유포인트 넣기
						refundDto.setDeductedPoints(currentPoints);
						
						reason = "적립취소할 보유포인트 부족, 환불금액에서 차감 [수강번호 "+rno+"번 예약취소]";
						
						// 아직 회수하지못한 포인트 = 회수해야할 포인트
						//refundDto.setRemainingPoints(pointvalue-negativePoints);
					}
				// 포인트 사용했을 경우 => 환불
				}else if ( pointvalue < 0 ) { // 적립포인트(차감해야할 포인트)가 없음, 사용포인트가 있음 환불해드려야함.
					// 적립된 포인트가 없으면 포인트 사용했을 수 있음.
					// 해당 rno로 예약할때 사용한 포인트 확인
					// 환불해들릴 포인트
					//negativePoints = Math.abs(pointvalue);
					negativePoints = -pointvalue;
					reason = "포인트 환불 [수강번호 "+rno+"번 예약취소]";
					refundDto.setUsedPoints(negativePoints);

				}
				// 포인트 적립취소(회수) 또는 포인트 환불
				if ( type == 1 || pointvalue < 0 ) {
					sql = "insert into point ( pointvalue , reason , mno , rno ) values ( ? , ? , ? , ? )";
					ps = con.prepareStatement(sql);
					ps.setInt(1, negativePoints);
					ps.setString(2, reason);
					ps.setInt(3, loginsession);
					ps.setInt(4, rno);
					int row = ps.executeUpdate();
					if ( row > 0 ) { // 포인트환불레코드 생성성공
						refundDto.setRefundSuccess(true);
						return refundDto;
					}
				}
			} 
		} catch (Exception e) {
			System.out.println("예약취소시 포인트차감 예외발생 : "+e);
		}
		refundDto.setRefundSuccess(false);
		return refundDto; 
	}
	
	// 포인트 이력 출력
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
		System.out.println("pointList : "+ pointList);
		return pointList;
	}
}
