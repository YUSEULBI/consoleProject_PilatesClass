package pilatesClass.view;

import java.util.Scanner;


public class 수강내역View {

	
	private static 수강내역View view=new 수강내역View();
	private 수강내역View() {};
	public static 수강내역View getInstance() {return view;}
	
	Scanner scanner=new Scanner(System.in);
	
	public void index() {
		while(true) {
			System.out.println("1.일반회원[회원가입] 2.강사[강사등록]");
			int ch=scanner.nextInt();
			if(ch==1) {}
			else if(ch==2){}
		}//while e
		
	}
	
	

	
}
