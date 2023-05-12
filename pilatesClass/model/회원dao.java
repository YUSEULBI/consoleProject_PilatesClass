package pilatesClass.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import pilatesClass.controller.회원controller;


public class 회원dao extends Dao{
	
	private static 회원dao dao = new 회원dao();
	private 회원dao() {
		// TODO Auto-generated constructor stub
	}
	public static 회원dao getInstance() {
		return dao;
	}
	ArrayList<회원dto> PMemberList = new ArrayList<>();
	
	//회원가입
	public boolean signup(회원dto 회원) {//회원가입
		
		String sql="insert into 회원(아이디,비밀번호,전화번호,이름,등급) "
				+ "values(?,?,?,?,?)";
		
		
		try {//ps는 상속받으면 해결
			ps=con.prepareStatement(sql);
			
			ps.setString(1, 회원.get아이디());
			ps.setString(2, 회원.get비밀번호());
			ps.setString(3, 회원.get전화번호());
			ps.setString(4, 회원.get이름());
			ps.setInt(5, 회원.get등급());
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			System.out.println("DB입력실패"+e);
		}
		
		
		return false;
		
		
	}
	
	public ArrayList<회원dto> PMemberView(int PRating){
		PMemberList = new ArrayList<>();
		String sql = "select * from 회원 where 회원.등급 = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setLong( 1 ,  PRating);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				회원dto 회원dto = new 회원dto(rs.getInt(1) , rs.getString(2) , rs.getString(3) , rs.getString(4), rs.getString(5) , rs.getInt(6));
				PMemberList.add(회원dto);
			}
			return PMemberList;
		}catch (Exception e) {System.out.println(e);}
		return null;
	}

	
	
	public int login(String 아이디,String 비밀번호) { //로그인
		
		String sql="select 회원번호_pk,비밀번호,등급 from 회원 where 아이디=? ";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, 아이디);
			rs=ps.executeQuery();
			
			if(rs.next()) {//출력이면..출력이 트루이면(?)
				if(rs.getString(2).contentEquals(비밀번호)) {//출력된 rs1번과 작성하는 비밀번호와 같을시
					
					회원controller.getInstance().setLogSession( rs.getInt(1) ) ; //1번이면 일반회원 로그인 성
					
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
	

	public boolean reservation(int ch) {
		
		String sql="insert into ";
		return true;
	}
	
	public ArrayList<수강내역dto> print(){
		
		return null;
	}
	
	
	
	public String findId(String 이름 , String 전화번호) { // 이름과 전화번호 입력으로 아이디 찾기
		 String sql="select 아이디 from 회원 where  이름=? and 전화번호=?";
		 try {
			ps=con.prepareStatement(sql);
			ps.setString(1, 이름);
			ps.setString(2, 전화번호);
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;
		
	}
	
	public String findPw(String 아이디 , String 이름) { // 아이디와 이름으로 비밀번호 찾기
		String sql="select 비밀번호 from 회원 where  아이디=? and 이름=?";
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, 아이디);
			ps.setString(2, 이름);
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;
		
	}
	
	
	public String findName() {//로그세션으로 이름 뽑아내는 함수 , 인수없이 이름만 리턴
		String sql="select 이름 from 회원 where  회원번호_pk =?"  ;
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, 회원controller.getInstance().getLogSession());
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
		String sql ="select c.이름 as 강사명 , count(*) as 누적수강생  , rank() over ( order by count(*) desc ) as 랭킹 from 수강내역 a , 스케줄 b , 회원 c where a.스케줄번호_fk = b.스케줄번호_pk and b.회원번호_fk = c.회원번호_pk group by c.회원번호_pk" ;
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
	
	//////////////////////////////////////////////////////////////////
	// 관리자페이지
	public boolean admin_login( String pw ) {
		
		String sql = "select * from 회원 where 등급 = 3 and 비밀번호 = ? ;";
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
				String sql = "select 회원번호_pk from 회원 where 회원.등급 =2 and 회원.이름 = ?;";
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
		String sql = "select 회원.이름 from 회원 where 회원번호_pk = ?;";
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
}

	
	
	
	
	
	
	
	
	
	


