package pilatesClass.model.Member;

import java.util.ArrayList;

public class 수강내역dao extends Dao {

	private static 수강내역dao dao=new 수강내역dao();
	private 수강내역dao () {};
	public static 수강내역dao getInstance () {return dao;}
	

	public boolean reservation(int ch) {
		
		String sql="insert into 수강내역(수강내역번호) values (?);";
		try {
		ps=con.prepareStatement(sql);
		ps.setInt(1, ch);
		ps.executeUpdate();
		return true;
	}catch (Exception e) {System.out.println(e);}
		return false;
	}
		
	public ArrayList<수강내역dto> print(){
		
		return null;
	}
	
	
}
