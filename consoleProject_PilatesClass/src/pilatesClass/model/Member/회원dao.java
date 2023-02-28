package pilatesClass.model.Member;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class 회원dao extends Dao{
	
	private static 회원dao dao = new 회원dao();
	private 회원dao() {
		// TODO Auto-generated constructor stub
	}
	public static 회원dao getInstance() {
		return dao;
	}
	
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
	
	private int logSession; //로그세션 int
	public int getLogSession() { //로그세션 게터
		return logSession;
	}

	
	public 회원dto login(String 아이디,String 비밀번호) {//로그인
		
		String sql="select * from 회원 where 아이디=? ";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, 아이디);
			rs=ps.executeQuery();
			
			if(rs.next()) {//출력이면..출력이 트루이면(?)
				if(rs.getString(3).contentEquals(비밀번호)) {//출력된 rs1번과 작성하는 비밀번호와 같을시
						회원dto 회원dto=new 회원dto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6));
						logSession=회원dto.get회원번호_pk();//왜 갑자기 안되지..?! 분명됐는뎅
						return 회원dto; 
					
				}else {
					회원dto A;//비번틀림 필요없음 = 0으로 리턴할거임
				}
			}
			return null; //없는회원(아이디없음)
			
		} catch (SQLException e) {
			System.out.println("없는회원입니다."+e);
		}
		
		return null;//db오류 필요없는리턴값이지만 리턴안해주면 오류남
		
		
		
		
		
		
	}
	

}
