package pilatesClass.model;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClassScheduleDao extends Dao {
	 
	private static ClassScheduleDao classScheduleDao = new ClassScheduleDao(); 
	private ClassScheduleDao() {	}
	public static ClassScheduleDao getInstance() {
		return classScheduleDao;
	}
	
	ArrayList<ClassScheduleDto> classList  = new ArrayList<>();
	
	// 전체수업출력
	public ArrayList<ClassScheduleDto> classView(){//과거 수업은 안보임
		classList  = new ArrayList<>();
		String sql = "select sno , sdate , sprice , mname from member , classschedule where member.mno = classschedule.mno and classschedule.sdate >= now() + interval 3 hour";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				ClassScheduleDto classScheduleDto = new ClassScheduleDto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				classList.add(classScheduleDto);
			}
			return classList;
			
		}catch (Exception e) {
			System.out.println("전체수업출력 예외발생: "+e);
		}
		return null;
	}
	
	// 존재하는 수업인지 확인
	public boolean checkExistSchedule( int sno ) {
		String sql = "select * from classschedule where sno= "+sno;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if ( rs.next() ) { return true; } // 수업 존재 
		} catch (Exception e) {
			System.out.println("수업예약시 수업번호확인 예외발생 :"+e);
		}
		return false;
	}
	
	// 수업 예약전 예약가능한 수업시간인지 확인
	public boolean checkClassDateTime( int sno ) {
		String sql = "select sno from member , classschedule where member.mno = classschedule.mno and classschedule.sdate >= now() + interval 3 hour and sno = "+sno;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if ( rs.next() ) { return true;}
		} catch (Exception e) {
			System.out.println("예약가능시간 여부확인 예외발생 : "+e);
		}
		return false;
	}
	
	// 지나간수업출력
	public ArrayList<ClassScheduleDto> completedClassView(){
		classList  = new ArrayList<>();
		String sql = "select sno , sdate , sprice , mname from member , classschedule where member.mno = classschedule.mno and classschedule.sdate < now() order by sdate desc";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				ClassScheduleDto classScheduleDto = new ClassScheduleDto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				classList.add(classScheduleDto);
			}
			return classList;
			
		}catch (Exception e) {
			System.out.println("지난수업출력 예외발생: "+e);
		}
		return null;
	}
	
	// 수업등록
	public boolean classAdd( ClassScheduleDto dto ) {
		int teacherMno = MemberDao.getInstance().teacher_NumFind(dto.getTeacherName());
		if ( teacherMno == -1 ) { return false;	} // 강사명이 잘못됨
		String sql = "insert into classschedule( sdate , sprice , mno ) values( ? , ? , ? );";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getSdate());
			ps.setInt(2, dto.getSprice());
			ps.setInt(3, teacherMno);
			ps.executeUpdate();
			return true;
		}catch (Exception e) { System.out.println(e); }
		
		return false;
	}
	
	
	// 수업수정
		public int classEdit( ClassScheduleDto dto , int sno ) {
			int teacherMno = MemberDao.getInstance().teacher_NumFind(dto.getTeacherName());
			if ( teacherMno == -1 ) { return 3;	} // 강사명이 잘못됨
			String sql = "update classschedule set sdate =? , sprice=? , mno =? where mno =?;";
			try {
				ps = con.prepareStatement(sql);
				ps.setString(1, dto.getSdate());
				ps.setInt(2, dto.getSprice());
				ps.setInt(3, teacherMno);
				ps.setInt(4, sno);
				ps.executeUpdate();
				return 1; // 변경성공
			}catch (Exception e) {
				System.out.println(e);
			}
			
			return 2;
		}
	
	// 수업삭제 전 스케줄번호 유무 확인
	public boolean deleteCheck ( int ch ) {
		String sql = "select * from classschedule where sno = ?;";
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
			String sql = "delete from classschedule where sno = ?;";
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
		
		
		ArrayList<ClassScheduleDto> relist=new ArrayList<>();
		public ArrayList<ClassScheduleDto> te_print(int logsession){
			relist=new ArrayList<>();
			String spl="select sno,sdate,sprice, mname from member m ,classschedule s  where m.mno = s.mno and m.mno=?;";
				
			
			try {
				ps=con.prepareStatement(spl);
				ps.setInt(1, logsession);
				rs=ps.executeQuery();
				
				while(rs.next()) {
					ClassScheduleDto classScheduleDto=new ClassScheduleDto(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getInt(4));
					relist.add(classScheduleDto);
					
				}
				return relist;
				
			} catch (SQLException e) {System.out.println(e);}
			
			return null;
		}
		
	// 회원이 예약취소 가능한 수업인지 체크
	public boolean checkCancelAvailability( int sno , int mno ) {
		String sql = "select r.rno , r.mno , s.sno , s.sdate , s.sprice from reservation r , classschedule s where r.sno = s.sno and sdate > now() and  r.sno = "+sno+" and r.mno = "+mno;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if ( rs.next() ) { return true; } // 예약취소가능
		
		} catch (Exception e) { System.out.println("예약취소 가능여부체크 예외발생 : "+e); }
		return false;
	}
	
	// 수업번호로 해당 수업의 금액 확인하기
	public int classAmount ( int sno ) {
		String sql = "select sprice from classschedule where sno = "+sno;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if ( rs.next() ) { return rs.getInt(1); }
		} catch (Exception e) { System.out.println("수업금액 조회 예외발생 : " +e); }
		return 0;
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
		
		
		
		
		
		
		
		

