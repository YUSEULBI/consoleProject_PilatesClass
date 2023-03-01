package pilatesClass.model.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public class 수강내역dao extends Dao{
	
	private static 수강내역dao dao=new 수강내역dao();
	private 수강내역dao () {};
	public static 수강내역dao getInstance () {return dao;}

	
	public boolean cancel(int ch) { //취소함수
		String sql="delete from 수강내역 where cno=?";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, ch);
			ps.executeUpdate();
			return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	public boolean reser_Check( int loginsession , int ch ) {
		String sql = "select * from 수강내역 where 수강내역.회원번호_fk = ? and 수강내역.스케줄번호_fk = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, loginsession);
			ps.setInt(2, ch);
			rs = ps.executeQuery();
			if( rs.next() ) { return false;	} // 예약중복이 있으면 false;
			else { return true; } // 예약중복없으면 true;
		}catch (Exception e) {
			System.out.println(e);
		}
		return true;
		
	}
	

	public int reservation(int loginsession , int ch) {
		boolean result = reser_Check(loginsession, ch);
		if ( !result ) { return 2;	} // 중복 예약실패 
		String sql="insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values(? , ?);";
		try {
		ps=con.prepareStatement(sql);
		ps.setInt(1, loginsession);
		ps.setInt(2, ch);
		ps.executeUpdate();
		return 1; // 예약성공
	}catch (Exception e) {System.out.println(e);}
		return 3; // 예외 관리자 문의
	}
		
	public ArrayList<수강내역dto> print(){
		
		return null;
	}
	
	
}
