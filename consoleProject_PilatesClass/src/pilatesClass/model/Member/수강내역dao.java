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



	public boolean reservation(int ch) {// 은영이가 한걸로 머지해주세용
		
		String sql="insert into 수강내역(회원번호_fk,스케줄번호_fk) values (?,?);";
		try {
		ps=con.prepareStatement(sql);
		ps.setInt(1, 회원controller.getInstance().getLogSession());
		ps.setInt(2, ch);
		ps.executeUpdate();
		return true;
	}catch (Exception e) {System.out.println(e);}
		return false;
	}
		
	public ArrayList<수강내역dto> print(){
		
		return null;
	}
	
	
}
