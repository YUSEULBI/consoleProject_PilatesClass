package pilatesClass.controller;


import java.util.ArrayList;

import pilatesClass.model.MessageDao;
import pilatesClass.model.MessageDto;
import pilatesClass.model.RefundDto;
import pilatesClass.model.ClassScheduleDao;
import pilatesClass.model.MemberDao;

public class MessageController {
	private static MessageController controller = new MessageController();
	private MessageController() {	}
	public static MessageController getInstance() {
		return controller;	}
	
	
	// 수업삭제 전 , 수업유무확인 , 수업을 예약한 수강생 확인
	public ArrayList<Integer> reser_Member( int sno ){
		if ( !ClassScheduleDao.getInstance().deleteCheck(sno) ) { 
			return null;	//수업이 없으면 null 반환
		}else {
			return MessageDao.getInstance().reser_Member(sno);
		}// 수업이 있으면 수강생 회원번호 반환
		
	}
	
	// 전체 회원번호 반환하기
//	public ArrayList<Integer> allMember(){
//		return MessageDao.getInstance().allMember();
//	}
	
	
	// 선택한 여러 회원에게 공통메시지 보내기
	public boolean sendMessage( ArrayList<Integer> MemList  , String title , String content ) {
		ArrayList<MessageDto> messageList = new ArrayList<>();
		for ( Integer i : MemList ) { // 선택한 회원들 각각의 메세지 작성 dto에 저장 후 ArrayList에 저장
			MessageDto dto = new MessageDto(0, title, content, false, i);
			messageList.add(dto);
		}
		return MessageDao.getInstance().sendMessage(messageList); //메세지 보낸 결과
	}
	
	// 전체 , 강사, 회원 전체메시지 보내기
	public boolean sendMessageByRole( int role , String title , String content ) {
		ArrayList<MessageDto> messageList = new ArrayList<>();
		ArrayList<Integer> MemList = new ArrayList<>();
		
		// 전체회원 또는 전체강사의 mno 가져오기
		MemList = MemberDao.getInstance().findRoleNo( role );
		
		for ( Integer i : MemList ) { // 선택한 회원들 각각의 메세지 작성 dto에 저장 후 ArrayList에 저장
			MessageDto dto = new MessageDto(title, content, false, i);
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
		int loginsession = MemberController.getInstance().getLogSession();
		return MessageDao.getInstance().message(loginsession);
		
	}
	
	// 메시지 상세보기
	public MessageDto message_content(int msgno) {
		return MessageDao.getInstance().message_content(msgno);
	}
	
	// 메시지 읽음처리
	public boolean message_read( int msgno ) {
		return MessageDao.getInstance().message_read(msgno);
	}
	
	// 수업취소로인한 환불처리 안내메시지 보내기
	public String CancellationAndRefundMessage( ArrayList<RefundDto> refundDtoList , int sno ) {
		int refundAmount = ClassScheduleController.getInstance().classAmount(sno);
		return MessageDao.getInstance().CancellationAndRefundMessage( refundDtoList , refundAmount );
	}
	
}
