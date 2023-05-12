package pilatesClass.controller;


import java.util.ArrayList;

import pilatesClass.model.MessageDao;
import pilatesClass.model.MessageDto;
import pilatesClass.model.스케줄dao;

public class MessageController {
	private static MessageController controller = new MessageController();
	private MessageController() {	}
	public static MessageController getInstance() {
		return controller;	}
	
	
	// 수업삭제 전 , 수업유무확인 , 수업을 예약한 수강생 확인
	public ArrayList<Integer> reser_Member( int 스케줄번호 ){
		if ( !스케줄dao.getInstance().deleteCheck(스케줄번호) ) { 
			return null;	//수업이 없으면 null 반환
		}else {
			return MessageDao.getInstance().reser_Member(스케줄번호);
		}// 수업이 있으면 수강생 회원번호 반환
		
	}
	
	// 전체 회원번호 반환하기
	public ArrayList<Integer> allMember(){
		return MessageDao.getInstance().allMember();
	}
	
	
	
	// 선택한 여러 회원에게 공통메시지 보내기
	public boolean sendMessage( ArrayList<Integer> MemList  , String title , String content ) {
		ArrayList<MessageDto> messageList = new ArrayList<>();
		for ( Integer i : MemList ) { // 선택한 회원들 각각의 메세지 작성 dto에 저장 후 ArrayList에 저장
			MessageDto dto = new MessageDto(0, title, content, false, i);
			messageList.add(dto);
		}
		return MessageDao.getInstance().sendMessage(messageList); //메세지 보낸 결과
	}
	
	// 선택한 회원 1명에게 메시지 보내기
	public boolean sendMessageOne(MessageDto dto ) {
		return MessageDao.getInstance().sendMessageOne(dto);
	}
	
	// 로그인한 회원 메시지 가져오기
	public ArrayList<MessageDto> message(){
		int loginsession = 회원controller.getInstance().getLogSession();
		return MessageDao.getInstance().message(loginsession);
		
	}
	
	// 메시지 상세보기
	public MessageDto message_content(int mno) {
		return MessageDao.getInstance().message_content(mno);
	}
	
	// 메시지 읽음처리
	public boolean message_read( int mno ) {
		return MessageDao.getInstance().message_read(mno);
	}
	
	
}
