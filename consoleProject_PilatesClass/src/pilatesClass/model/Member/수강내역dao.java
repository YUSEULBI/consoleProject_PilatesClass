package pilatesClass.model.Member;

import java.sql.SQLException;

public class 수강내역dao extends Dao{
	
	private static 수강내역dao 수강내역dao=new 수강내역dao();
	public 수강내역dao() {
		// TODO Auto-generated constructor stub
	}
	
	public static 수강내역dao getInstance() {
		return 수강내역dao;
	}

	
	public boolean cancel(int ch) { //취소함수
		String sql="delete from 수강내역 where cno=?";
		
		try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, ch);
			ps.executeUpdate();
			return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	

}
