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
	
	// 지나간수업출력
	public ArrayList<ClassScheduleDto> completedClassView(){
		
		return ClassScheduleDao.getInstance().completedClassView();
	
	}
	
	// 존재하는 수업인지 확인
	public boolean checkExistSchedule( int sno ) {
		return ClassScheduleDao.getInstance().checkExistSchedule(sno);
	}
	
	// 수업 예약전 예약가능한 수업시간인지 확인
	public boolean checkClassDateTime( int sno ) {
		return ClassScheduleDao.getInstance().checkClassDateTime(sno);
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
	
	// 회원이 예약취소 가능한 수업인지 체크
	public boolean checkCancelAvailability( int sno) {
		int loginsession = MemberController.getInstance().getLogSession();
		return ClassScheduleDao.getInstance().checkCancelAvailability(sno , loginsession);
	}
	
	public ArrayList<ClassScheduleDto> te_print(){
		
		int logsession=MemberController.getInstance().getLogSession();
		
		
		return ClassScheduleDao.getInstance().te_print(logsession);
	}
	
	// 수업번호로 해당 수업의 금액 확인하기
	public int classAmount ( int sno ) {
		return ClassScheduleDao.getInstance().classAmount(sno);
	}
	
}
