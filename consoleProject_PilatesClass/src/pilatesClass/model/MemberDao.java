package pilatesClass.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import pilatesClass.controller.MemberController;


public class MemberDao extends Dao{
	
	// 싱글톤
	private static MemberDao dao = new MemberDao();
	private MemberDao() {	}
	public static MemberDao getInstance() { return dao; 	}
	
	//
	ArrayList<MemberDto> PMemberList = new ArrayList<>();
	
	//회원가입
	public boolean signup(MemberDto memberDto) {//회원가입
		
		String sql="insert into member(mid,mpw,mphone,mname,mrole) "
				+ "values(?,?,?,?,?)";
		
		
		try {//ps는 상속받으면 해결
			ps=con.prepareStatement(sql);
			
			ps.setString(1, memberDto.getMid());
			ps.setString(2, memberDto.getMpw());
			ps.setString(3, memberDto.getMphone());
			ps.setString(4, memberDto.getMname());
			ps.setInt(5, memberDto.getMrole());
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			System.out.println("DB입력실패"+e);
		}
		
		
		return false;
		
		
	}
	
	// 조회
	public ArrayList<MemberDto> pMemberView(int PRating){
		PMemberList = new ArrayList<>();
		String sql = "select * from member where member.mrole = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setLong( 1 ,  PRating);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				MemberDto memberDto = new MemberDto(rs.getInt(1) , rs.getString(2) , rs.getString(3) , rs.getString(4), rs.getString(5) , rs.getInt(6));
				PMemberList.add(memberDto);
			}
			return PMemberList;
		}catch (Exception e) {System.out.println(e);}
		return null;
	}

	
	
	public int login(String mid,String mpw) { //로그인
		
		String sql="select mno,mpw,mrole from member where mid=? ";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, mid);
			rs=ps.executeQuery();
			
			if(rs.next()) {//출력이면..출력이 트루이면(?)
				if(rs.getString(2).contentEquals(mpw)) {//출력된 rs1번과 작성하는 비밀번호와 같을시
					
					MemberController.getInstance().setLogSession( rs.getInt(1) ) ; //1번이면 일반회원 로그인 성
					
					if(rs.getInt(3)==1) {//수강생 로그인
						 //회원번호 pk를 대입 -> 정보를 꺼내온다던지 할때 추후에 문제가 없는지.. -> 문제없음!
						return 1;
					}else if (rs.getInt(3)==2) {//강사 로그인
						return 2 ;
					}
				}else {
					return 0; //비밀번호 틀림
				}
			}// 아이디가 존재 하는 회원
			return -1; //없는회원(아이디없음)
			
		} catch (SQLException e) {
			System.out.println("없는회원입니다."+e);
		}
		
		return -2;//db오류 필요없는리턴값이지만 리턴안해주면 오류남
		
	}
	


	
	
	
	public String findId(String mname , String mphone) { // 이름과 전화번호 입력으로 아이디 찾기
		 String sql="select mid from member where mname=? and mphone=?";
		 try {
			ps=con.prepareStatement(sql);
			ps.setString(1, mname);
			ps.setString(2, mphone);
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;
		
	}
	
	public String findPw(String mid , String mname) { // 아이디와 이름으로 비밀번호 찾기
		String sql="select mpw from member where  mid=? and mname=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, mid);
			ps.setString(2, mname);
			rs=ps.executeQuery();
			if(rs.next()) { return rs.getString(1); }
			
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
		
	}
	
	
	public String findName() {//로그세션으로 이름 뽑아내는 함수 , 인수없이 이름만 리턴
		String sql="select mname from member where mno =?" ;
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, MemberController.getInstance().getLogSession());
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} return null;
	}
	
	public ArrayList<RankDto> getTchRank(){
		ArrayList<RankDto> rankList = new ArrayList<>();
		String sql ="select m.mname as 강사명 , count(*) as 누적수강생  , rank() over ( order by count(*) desc ) as 랭킹 from reservation r , classschedule s , member m where r.sno = s.sno and s.mno = m.mno group by m.mno" ;
		try {
			ps=con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				RankDto rankDto = new RankDto( rs.getString(1) , rs.getInt(2) ,rs.getInt(3) );
				rankList.add(rankDto);
			}
			return rankList;
		}catch (Exception e) {System.out.println(e);}
		return null;
	}
	
	// 패스워드확인
	public boolean checkPw( int mno , String mpw ) {
		String sql = "select * from member where mno = ? and mpw = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, mno);
			ps.setString(2, mpw);
			rs = ps.executeQuery();
			if ( rs.next() ) { return true; }
		} catch (Exception e) { System.out.println(e); }
		return false;
	}
	
	// 회원탈퇴
	public boolean deleteMember( int mno ) {
		String sql = "delete from member where mno = "+mno;
		try {
			ps = con.prepareStatement(sql);
			int row = ps.executeUpdate();
			if ( row == 1 ) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	//////////////////////////////////////////////////////////////////
	// 관리자페이지
	public boolean admin_login( String pw ) {
		
		String sql = "select * from member where mrole = 3 and mpw = ? ;";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, pw);
			rs = ps.executeQuery();
			
			if ( rs.next() ) {
				if ( rs.getString(3).equals(pw) ) { return true;	}
			}
			return false;
		}catch (Exception e) {
			System.out.println("[관리자 로그인 실패]"+e);
		}return false;
		
	}
	
	
		
	// 강사명으로 회원번호 찾기
	public int teacher_NumFind( String name ) {
		String sql = "select mno from member where member.mrole =2 and member.mname = ?;";
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
	

			
	// 회원번호로 회원명 찾기
	public String memberNameFind( int num ) {
		String sql = "select member.mname from member where mno = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			if ( rs.next() ) {
				return rs.getString(1);
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	// 회원명으로 회원번호 찾기
	public int memberNoFind( String name ) {
		String sql = "select mno from member where member.mname = ?;";
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
	
	// 회원이나 강사 mno 리스트 반환
	public ArrayList<Integer> findRoleNo( int role ){
		String sql = "select mno from member where mrole = "+role;
		ArrayList<Integer> mnoList = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while ( rs.next() ) {
				mnoList.add(rs.getInt(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return mnoList;
	}
}

	
	
	
	
	
	
	
	
	
	


