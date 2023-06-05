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
		String sql = "select sno , sdate , sprice , mno from member , classschedule where member.mno = classschedule.mno AND classschedule.sdate >=date_add(now(),interval -1 day) and now()";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while ( rs.next() ) {
				ClassScheduleDto classScheduleDto = new ClassScheduleDto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
				classList.add(classScheduleDto);
			}
			return classList;
			
		}catch (Exception e) {
			// TODO: handle exception
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
		}catch (Exception e) {			System.out.println(e);
		}
		
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
		
		
		
		
		
		
		
		

