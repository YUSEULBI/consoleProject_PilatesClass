package pilatesClass.controller;

import java.util.ArrayList;

import pilatesClass.model.ReservationDao;
import pilatesClass.model.ReservationDto;
import pilatesClass.model.ClassScheduleDao;
import pilatesClass.model.ClassScheduleDto;
import pilatesClass.model.MemberDao;

public class ClassScheduleController {
	private static ClassScheduleController controller = new ClassScheduleController();
	private ClassScheduleController() {
		// TODO Auto-generated constructor stub
	}
	public static ClassScheduleController getInstance() {
		return controller;
	}
	
	
	// 수업보기
	public ArrayList<ClassScheduleDto> classView(){
		
		return ClassScheduleDao.getInstance().classView();
	
	}
	
	
	//수업등록
	public boolean classAdd( ClassScheduleDto dto ) {
		return ClassScheduleDao.getInstance().classAdd(dto);
	}
	
	//수업수정
	public int classEdit( ClassScheduleDto dto , int 수업번호 ) {
		return ClassScheduleDao.getInstance().classEdit(dto, 수업번호);	
	}
	
	//수업삭제
	public boolean classDelete( int ch ) {
		return ClassScheduleDao.getInstance().classDelete(ch);
	}
	
	
	
	public ArrayList<ClassScheduleDto> te_print(){
		
		int logsession=MemberController.getInstance().getLogSession();
		
		
		return ClassScheduleDao.getInstance().te_print(logsession);
	}
	
	
	
}
