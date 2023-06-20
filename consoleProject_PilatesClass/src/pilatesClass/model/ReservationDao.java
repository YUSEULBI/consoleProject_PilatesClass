package pilatesClass.model;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;



import pilatesClass.controller.PointController;
import pilatesClass.controller.MemberController;
import pilatesClass.view.PointView;

public class ReservationDao extends Dao{
	
	private static ReservationDao dao=new ReservationDao();
	private ReservationDao () {};
	public static ReservationDao getInstance () {return dao;}


	
	public boolean re_check(int ch , int mno) {
		String sql="select * from reservation where sno=? and mno=?"; //
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, ch);
			ps.setInt(2, mno);
			rs=ps.executeQuery();
			
			if(rs.next()) {//있으면 중복
				return false;
			}
			
		} catch (Exception e) {System.out.println(e);}
		return true;
	}
	
	
	
	
	
	public boolean cancel(int sno , int mno) { //reservation취소함수(예약 완료후 예약내역보기 다음에 넣기)

		String sql="delete from reservation where sno = ? and mno = ?";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, sno);
			ps.setInt(2, mno);
			int row = ps.executeUpdate();
			if ( row == 1 ) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("실패~");
			e.printStackTrace();
		}
		return false;
	}



	public int reservation(int ch , int mno) {

		String sql="insert into reservation( mno , sno ) values( ? , ? );";
		int rno = 0;
		
		try {
			ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, mno);
			ps.setInt(2, ch);
			int row = ps.executeUpdate();
			if ( row > 0 ) {
				rs = ps.getGeneratedKeys();
				if( rs.next() ) { rno = rs.getInt(1); } 
			}
		} catch (Exception e) {System.out.println(e);}
		
		return rno;
	}
	
	// 결제금액 DB에서 확인, 사용할 포인트를 뺀 결제금액과 지불금액과 비교
	public int pay(int point , int money,int ch) {//결제 및 거스름돈
		String sql="select sprice from classschedule where sno=?";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, ch);
			rs=ps.executeQuery();
			if(rs.next()) {
				//amount = 결제해야할 금액(수업금액-사용포인트)
				int amount = rs.getInt(1)-point;
				if(amount < money) {//입력값이 db값 보다 클경우 거스름돈을 리턴 [성공]
					int change = money-amount;
					return change; //차액을 바로 리턴
					
			 	}else if (amount==money) {//입력값이 db값 과 같을 경우 [성공]
						return -1;
					}
					
				else if (amount>money) {//입력값이 db값보다 작을 경우 [실패]
					return -2;
				}	
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		return 3;
		
	}
	
	// 결제 예정금액 체크
	public int payMoneyCheck( int ch ) {
		String sql="select sprice from classschedule where sno=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, ch);
			rs=ps.executeQuery();
			if ( rs.next() ) {
				return rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println(e);
		}return 0;
	}
	
	
	ArrayList<ClassScheduleDto> relist=new ArrayList<>();
	public ArrayList<ClassScheduleDto> print(int logsession){//내가 신청한 수업 목록
		relist=new ArrayList<>();
		String spl="select r.sno,s.sdate,s.sprice,m.mname from member m ,classschedule s,reservation r "
				+  " where m.mno=s.mno  and r.sno = s.sno and r.mno=?";
			
		
		try {
			ps=con.prepareStatement(spl);
			ps.setInt(1, logsession);
			rs=ps.executeQuery();
			
			while(rs.next()) {
				ClassScheduleDto classScheduleDto =new ClassScheduleDto(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getString(4));
				relist.add( classScheduleDto );
				
			}
			return relist;
			
		} catch (SQLException e) {System.out.println(e);}
		
		return null;
	}
	
	
	// 수업이 본인이 예약한 수업인지 확인 => 예약테이블 식별번호 rno 반환
	public int checkRno( int sno , int mno ) {
		String sql = "select * from reservation where sno = ? and mno = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, sno);
			ps.setInt(2, mno);
			rs = ps.executeQuery();
			if( rs.next() ) { return rs.getInt(1); } // 본인이 예약한 수강번호임
		} catch (Exception e) {
			System.out.println("수강번호 본인확인 예외발생 : " +e);
		}
		return 0; // 본인이 예약한 수강번호가 아님, 그 외
	}
	
	
}