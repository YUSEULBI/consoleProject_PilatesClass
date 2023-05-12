package pilatesClass.model;

import java.sql.SQLException;
import java.util.ArrayList;

public class 스케줄dao extends Dao {
	 
	private static 스케줄dao 스케줄dao = new 스케줄dao(); 
	private 스케줄dao() {
		// TODO Auto-generated constructor stub
	}
	public static 스케줄dao getInstance() {
		return 스케줄dao;
	}
	
	ArrayList<스케줄dto> classList  = new ArrayList<>();
	
	// 전체수업출력
	public ArrayList<스케줄dto> classView(){//과거 수업은 안보임
		classList  = new ArrayList<>();
		String sql = "select 스케줄번호_pk , 수강일시 , 금액 , 이름 from 회원 , 스케줄 where 회원.회원번호_pk = 스케줄.회원번호_fk AND 스케줄.수강일시 >=date_add(now(),interval -1 day) and now()";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				스케줄dto 스케줄dto = new 스케줄dto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				classList.add(스케줄dto);
			}
			return classList;
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	// 수업등록
	public boolean classAdd( 스케줄dto dto ) {
		int 회원번호 = 회원dao.getInstance().teacher_NumFind(dto.get강사명());
		if ( 회원번호 == -1 ) { return false;	} // 강사명이 잘못됨
		String sql = "insert into 스케줄( 수강일시 , 금액 , 회원번호_fk ) values( ? , ? , ? );";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.get수강일시());
			ps.setInt(2, dto.get금액());
			ps.setInt(3, 회원번호);
			ps.executeUpdate();
			return true;
		}catch (Exception e) {
			System.out.println(e);
		}
		
		return false;
	}
	
	
	// 수업수정
		public int classEdit( 스케줄dto dto , int 수업번호 ) {
			int 회원번호 = 회원dao.getInstance().teacher_NumFind(dto.get강사명());
			if ( 회원번호 == -1 ) { return 3;	} // 강사명이 잘못됨
			String sql = "update 스케줄 set 수강일시 =? , 금액=? , 회원번호_fk =? where 스케줄번호_pk =?;";
			try {
				ps = con.prepareStatement(sql);
				ps.setString(1, dto.get수강일시());
				ps.setInt(2, dto.get금액());
				ps.setInt(3, 회원번호);
				ps.setInt(4, 수업번호);
				ps.executeUpdate();
				return 1; // 변경성공
			}catch (Exception e) {
				System.out.println(e);
			}
			
			return 2;
		}
	
	// 수업삭제 전 스케줄번호 유무 확인
	public boolean deleteCheck ( int ch ) {
		String sql = "select * from 스케줄 where 스케줄번호_pk = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, ch);
			rs = ps.executeQuery();
			if ( rs.next() ) { return true;	}
			
		}catch (Exception e) {
			System.out.println(e);
		}return false;
	}
		
	// 수업삭제
		public boolean classDelete( int ch ) {
			String sql = "delete from 스케줄 where 스케줄번호_pk = ?;";
			try {
				ps = con.prepareStatement(sql);
				ps.setInt(1, ch);
				ps.executeUpdate();
				return true; // 삭제 성공
			}catch (Exception e) {
				System.out.println(e);
			}
			return false; // 실패 관리자문의
		}
		
		
		ArrayList<스케줄dto> relist=new ArrayList<>();
		public ArrayList<스케줄dto> te_print(int logsession){
			relist=new ArrayList<>();
			String spl="select 스케줄번호_pk,수강일시,금액, 이름 from 회원 m ,스케줄 s  where m.회원번호_pk = s.회원번호_fk and m.회원번호_pk=?;";
				
			
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
		
		
	/*	public boolean x_print() {
			
			String sql="select 수강일시 from  스케줄 where 수강일시 >=date_add(now(),interval -1 day) and now();";
															
			try {
				ps=con.prepareStatement(sql);
				rs=ps.executeQuery();
				while(rs.next()) {return true;}
			} catch (Exception e) {System.out.println(e);}return false;
			
			*/
		
		
	//	"select 수강일시 from  스케줄 where 수강일시 between date_add(now(),interval -35 day) and now();";
		
		
		
		
		
		
		
		

