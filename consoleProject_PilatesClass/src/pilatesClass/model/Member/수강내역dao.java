package pilatesClass.model.Member;

import java.sql.SQLException;
import java.util.ArrayList;

import pilatesClass.controller.회원controller;

public class 수강내역dao extends Dao{
	
	private static 수강내역dao dao=new 수강내역dao();
	private 수강내역dao () {};
	public static 수강내역dao getInstance () {return dao;}

	
	
	public boolean re_check(int ch) {
		String sql="select* from 수강내역 where 스케줄번호_fk=?;"; //
		
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, ch);
			rs=ps.executeQuery();
			
			if(rs.next()) {//있으면 중복
				return false;
			}
			
		} catch (Exception e) {System.out.println(e);}
		return true;
	}
	
	
	
	
	
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



	public boolean reservation(int logsession,int ch) {
			
			String sql="insert into 수강내역( 회원번호_fk , 스케줄번호_fk ) values( ? , ? );";
			try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, logsession);
			ps.setInt(2, ch);
			ps.executeUpdate();
			return true;
		}catch (Exception e) {System.out.println(e);}
			return false;
		}
	
	
	ArrayList<스케줄dto> relist=new ArrayList<>();
	public ArrayList<스케줄dto> print(int logsession){//내가 신청한 수업 목록
		relist=new ArrayList<>();
		String spl="select 수강내역번호,수강일시,금액, 이름 from 회원 m ,스케줄 s,수강내역 r "
				+  " where m.회원번호_pk=s.회원번호_fk  and r.스케줄번호_fk = s.스케줄번호_pk and r.회원번호_fk=?;";
			
		
		try {
			ps=con.prepareStatement(spl);
			ps.setInt(1, logsession);
			rs=ps.executeQuery();
			
			while(rs.next()) {
				스케줄dto 스케줄dto=new 스케줄dto(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getString(4));
				relist.add(스케줄dto);
				
			}
			return relist;
			
		} catch (SQLException e) {System.out.println(e);}
		
		return null;
	}
	
	
	
	
	
	
	
	
}