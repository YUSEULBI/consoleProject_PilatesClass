package pilatesClass.view;

import java.util.ArrayList;
import java.util.Scanner;

import pilatesClass.controller.MessageController;
import pilatesClass.controller.MemberController;
import pilatesClass.model.MessageDto;

public class MessageView {
	private static MessageView messageView = new MessageView();
	private MessageView() {	}
	public static MessageView getInstance() {
		return messageView;
	}
	
	Scanner scanner = new Scanner(System.in);
	
	// 관리자 메시지 창
	public void adminMessage_page(int role) {

		while(true) {
			System.out.println("1.개별메시지 2.전체메시지 3.뒤로가기");
			try {
				int ch = scanner.nextInt();
				if ( ch == 1 ) { sendMessageOne();	}
				else if ( ch == 2 ) { sendMessageAll(role);	}
				else if ( ch == 3 ) { break;	}
			}catch (Exception e) {
				System.out.println(e);
				scanner=new Scanner(System.in);
			}
		}
	}
	
	
	// 수업삭제 전 [수업유무확인,예약수강생 회원번호 가져와서] 메세지 보내기
		public boolean reser_Member( int sno ){
			ArrayList<Integer> reserMemList =MessageController.getInstance().reser_Member(sno);
			if ( reserMemList == null ) {
				System.out.println("[존재하지 않는 수업번호 입니다.]");
				return false;			}
			System.out.println("----------- 메시지작성 -----------");
			String title = sno+"번 수업이 취소 되었습니다.";
			System.out.print("제목 [자동작성] : " + title);
			System.out.println();
			System.out.print("보낼메시지 : ");
			String content = "임시";
			content = scanner.nextLine();
		
			return sendMessage(reserMemList , title , content);
		}
		
	// 여러 회원에게 메세지 보내기
		public boolean sendMessage( ArrayList<Integer> MemList  , String title , String content ) {
			boolean result = MessageController.getInstance().sendMessage(MemList, title, content);
			if ( result ) {
				for ( Integer i : MemList ) {
					System.out.println("회원번호 "+i+"번 "+MemberController.getInstance().memberNoFind(i)+" 회원에게 메세지를 보냈습니다.");
				}
			}else { System.out.println("[메시지 발송 실패]");	}
			return result;
		}
	// 로그인한 회원 읽지않은 메시지 개수 알려주기
	public void messageCount() {
		ArrayList<MessageDto> messageList = MessageController.getInstance().message();
		int count = 0;
		for ( MessageDto m :  messageList ) {
			if ( !m.isState() ) { count++; }
		}
		if ( count > 0 ) { // 읽지않은 메시지가 있으면 알림.
			System.out.println("["+count+"개의 새로운 메시지가 있습니다.]");
		}
	}
	
	// 메시지 페이지
	public void message_Page() {
		while( true ) {
			ArrayList<MessageDto> messageList = MessageController.getInstance().message();
			System.out.println("================= 메시지 페이지 =================");
			System.out.printf("%s\t%-25s\t%-10s\n","번호","제목","상태");
			for ( MessageDto m :  messageList ) {
				String state = "읽지않음";
				if ( m.isState()) { state = "읽음";	}
				System.out.printf("%d\t%-25s\t%-10s\n",m.getMsgno(),m.getTitle(), state );
			}
			System.out.println("=============================================");
			System.out.println("0.뒤로가기 / 번호입력: 메시지 상세보기");
			try {
				int msgno = scanner.nextInt();
				if ( msgno == 0 ) { return;	}
				else { message_content(msgno);	}
			}catch (Exception e) {
				System.out.println(e);
				scanner=new Scanner(System.in);
			}
		}
	}
	
	// 메시지 상세보기
	public void message_content(int msgno) throws Exception {
		while( true ) {
			System.out.println("================ 메시지 상세보기 ================");
	        MessageDto dto = MessageController.getInstance().message_content(msgno);
	        System.out.print("[번호] "+dto.getMsgno()+"\n");
	        System.out.print("[제목] "+dto.getTitle()+"\n");
	        System.out.println("[내용] "+dto.getContent());
	        // 메시지 읽음처리
	        if ( !(MessageController.getInstance().message_read(msgno)) ){
	        	System.out.println("[메시지 읽음처리 실패]-관리자문의");
	        }
			System.out.println("=============================================");
	        System.out.println("1.뒤로가기");
	        int ch = scanner.nextInt();
	        if ( ch == 1 ) { return;	}
		}
	}
	
	// 1명에게 메시지 보내기
	public void sendMessageOne() throws Exception {
		System.out.println("메시지 받을분 회원번호를 입력하세요.");
		int mno = scanner.nextInt();
		// 존재하는 회원명인지 체크
		String name = MemberController.getInstance().memberNoFind(mno);
		if ( name == null ) { System.out.println("없는 회원 입니다.");	}
		else {
			System.out.print("제목 : ");
			scanner.nextLine();
			String title = scanner.nextLine();
			System.out.print("보낼메시지 : ");
			String content = scanner.nextLine();
			MessageDto dto = new MessageDto(0, title, content, false, mno);
			boolean result = MessageController.getInstance().sendMessageOne(dto);
			if ( result ) { System.out.println("["+mno+"번 "+name+" 회원에게 메시지를 보냈습니다.]");	}
		}
	}
	
	// 회원 또는 강사 전체에게 메시지 보내기
	public void sendMessageAll( int role ) throws Exception {
		System.out.print("제목 : ");
		scanner.nextLine();
		String title = scanner.nextLine();
		System.out.print("보낼메시지 : ");
		String content = scanner.nextLine();

		boolean result = 
				MessageController.getInstance().sendMessageByRole( role, title, content);
		if ( result ) { System.out.println("[모든 회원에게 메시지를 보냈습니다.]");	}
		
	}
}