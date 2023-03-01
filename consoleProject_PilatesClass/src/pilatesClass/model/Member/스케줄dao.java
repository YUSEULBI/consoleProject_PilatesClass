package pilatesClass.model.Member;

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
	public ArrayList<스케줄dto> classView(){
		classList  = new ArrayList<>();
		String sql = "select 스케줄번호_pk , 수강일시 , 금액 , 이름 from 회원 , 스케줄 where 회원.회원번호_pk = 스케줄.회원번호_fk;";
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
	
	// 나중에 MemberDao로 옮길예정
	// 회원명으로 회원번호 찾기
	public int memberNoFind( String name ) {
		String sql = "select 회원번호_pk from 회원 where 회원.이름 = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if ( rs.next() ) {
				return rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
	// 수업등록
	public boolean classAdd( 스케줄dto dto ) {
		int 회원번호 = memberNoFind(dto.get강사명());
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
			int 회원번호 = memberNoFind(dto.get강사명());
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
		public int classDelete( int ch ) {
			if ( !deleteCheck(ch) ) { return 2; } // 수업이 없음 , 스케줄번호 잘못입력 
			String sql = "delete from 스케줄 where 스케줄번호_pk = ?;";
			try {
				ps = con.prepareStatement(sql);
				ps.setInt(1, ch);
				ps.executeUpdate();
				return 1; // 삭제 성공
			}catch (Exception e) {
				System.out.println(e);
			}
			return 3; // 실패 관리자문의
		}
}
