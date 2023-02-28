package pilatesClass.model.Member;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class 회원dao_ver2 extends Dao{
	
	private static 회원dao_ver2 dao = new 회원dao_ver2();
	private 회원dao_ver2() {
		// TODO Auto-generated constructor stub
	}
	public static 회원dao_ver2 getInstance() {
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

	
	
	public int login(String 아이디,String 비밀번호) {
		
		String sql="select 회원번호_pk,비밀번호,등급 from 회원 where 아이디=? ";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, 아이디);
			rs=ps.executeQuery();
			
			if(rs.next()) {//출력이면..출력이 트루이면(?)
				if(rs.getString(2).contentEquals(비밀번호)) {//출력된 rs1번과 작성하는 비밀번호와 같을시
					회원dto 회원dto=new 회원dto();
					if(rs.getInt(3)==1) {
						logSession=rs.getInt(1); //회원번호 pk를 대입 -> 정보를 꺼내온다던지 할때 추후에 문제가 없는지..
						return 1; //1번이면 일반회원 로그인 성공
					}else if (rs.getInt(3)==2) {
						logSession=rs.getInt(1);
						return 2; //2번이면 관리자 회원 로그인 성공
					}
				}else {
					return 0; //비밀번호 틀림
				}
			}
			return -1; //없는회원(아이디없음)
			
		} catch (SQLException e) {
			System.out.println("없는회원입니다."+e);
		}
		
		return -2;//db오류 필요없는리턴값이지만 리턴안해주면 오류남
		
	}

}
