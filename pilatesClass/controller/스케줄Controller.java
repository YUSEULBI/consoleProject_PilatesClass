package pilatesClass.controller;

import java.util.ArrayList;

import pilatesClass.model.수강내역dao;
import pilatesClass.model.수강내역dto;
import pilatesClass.model.스케줄dao;
import pilatesClass.model.스케줄dto;
import pilatesClass.model.회원dao;

public class 스케줄Controller {
	private static 스케줄Controller controller = new 스케줄Controller();
	private 스케줄Controller() {
		// TODO Auto-generated constructor stub
	}
	public static 스케줄Controller getInstance() {
		return controller;
	}
	
	
	// 수업보기
	public ArrayList<스케줄dto> classView(){
		
		return 스케줄dao.getInstance().classView();
	
	}
	
	
	//수업등록
	public boolean classAdd( 스케줄dto dto ) {
		return 스케줄dao.getInstance().classAdd(dto);
	}
	
	//수업수정
	public int classEdit( 스케줄dto dto , int 수업번호 ) {
		return 스케줄dao.getInstance().classEdit(dto, 수업번호);	
	}
	
	//수업삭제
	public boolean classDelete( int ch ) {
		return 스케줄dao.getInstance().classDelete(ch);
	}
	
	
	
	public ArrayList<스케줄dto> te_print(){
		
		int logsession=회원controller.getInstance().getLogSession();
		
		
		return 스케줄dao.getInstance().te_print(logsession);
	}
	
	
	
}
