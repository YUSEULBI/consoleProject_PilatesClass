package pilatesClass.model.Member;

import java.sql.SQLException;
import java.util.ArrayList;

import pilatesClass.controller.회원controller;

public class 수강내역dao extends Dao{
	
	private static 수강내역dao dao=new 수강내역dao();
	private 수강내역dao () {};
	public static 수강내역dao getInstance () {return dao;}

	
	public boolean cancel(int ch) { //수강내역취소함수(예약 완료후 예약내역보기 다음에 넣기)
		String sql="delete from 수강내역 where 수강내역번호=?";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, ch);
			ps.executeUpdate();
			return true;
			
			
		} catch (SQLException e) {
			System.out.println("실패~");
			e.printStackTrace();
		}
		return false;
	}

<<<<<<< HEAD
	
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
=======


	public boolean reservation(int ch) {// 은영이가 한걸로 머지해주세용
>>>>>>> branch '장민정' of https://github.com/YUSEULBI/consoleProject_PilatesClass
		
<<<<<<< HEAD
	}
	

	public int reservation(int loginsession , int ch) {
		boolean result = reser_Check(loginsession, ch);
		if ( !result ) { return 2;	} // 중복 예약실패 
		String sql="insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values(? , ?);";
=======
		String sql="insert into 수강내역(회원번호_fk,스케줄번호_fk) values (?,?);";
>>>>>>> branch '장민정' of https://github.com/YUSEULBI/consoleProject_PilatesClass
		try {
		ps=con.prepareStatement(sql);
<<<<<<< HEAD
		ps.setInt(1, loginsession);
=======
		ps.setInt(1, 회원controller.getInstance().getLogSession());
>>>>>>> branch '장민정' of https://github.com/YUSEULBI/consoleProject_PilatesClass
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
