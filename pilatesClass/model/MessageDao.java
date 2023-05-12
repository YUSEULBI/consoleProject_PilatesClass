package pilatesClass.model;

import java.util.ArrayList;

public class MessageDao extends Dao {
	private static MessageDao dao = new MessageDao();
	private MessageDao() { 	}
	public static MessageDao getInstance() {
		return dao;
	}
	

	
	// 수업삭제 전 수업을 예약했던 수강생번호 구하기
	public ArrayList<Integer> reser_Member( int 스케줄번호 ){
		ArrayList<Integer> reserMemList = new ArrayList<>();
		String sql = "select * from 수강내역 where 스케줄번호_fk = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, 스케줄번호);
			rs = ps.executeQuery();
			while ( rs.next()) {
				reserMemList.add(rs.getInt(2));
			}
		}catch (Exception e) {
			System.out.println(e);
		}return reserMemList;
	}
	
	// 전체 회원번호 구하기
	public ArrayList<Integer> allMember(){
		ArrayList<Integer> allMemList = new ArrayList<>();
		String sql = "select 회원번호_pk from 회원 where 등급 =1;";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while ( rs.next()) {
				allMemList.add(rs.getInt(1));
			}
		}catch (Exception e) {
			System.out.println("전체회원번호구하기 예외: "+e);
		}return allMemList;
	}
	
	// [선택]한 [여러회원]에게 [공통메세지] 보내기
	public boolean sendMessage( ArrayList<MessageDto> messageList ) {
		for ( MessageDto d : messageList ) {
			String sql = "insert into message( title , content , state , 회원번호_fk )  values ( ? , ? , false , ? );";
			try {
				ps = con.prepareStatement(sql);
				ps.setString(1, d.getTitle());
				ps.setString(2, d.getContent());
				ps.setInt(3, d.get회원번호_fk());
				ps.executeUpdate();
				
			}catch (Exception e) {
				System.out.println(e);
				return false;
			}//catch end
		}//for end
		return true;
	}
	
	// 선택한 [회원1명] 에게 메시지 보내기
	public boolean sendMessageOne(MessageDto dto ) {
		String sql = "insert into message( title , content , state , 회원번호_fk )  values ( ? , ? , false , ? );";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			ps.setInt(3, dto.get회원번호_fk());
			ps.executeUpdate();
			return true;
		}catch (Exception e) {
			System.out.println(e);
		}return false;
	}
	
	// 로그인한 회원 메시지 가져오기
	public ArrayList<MessageDto> message( int loginsession ){
		ArrayList<MessageDto> messageList = new ArrayList<>();
		String sql = "select * from message where 회원번호_fk = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, loginsession);
			rs = ps.executeQuery();
			while(rs.next()) {
				MessageDto dto = new MessageDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5));
				messageList.add(dto);
			}
		}catch (Exception e) { 	System.out.println(e);}
		return messageList;
		
	}
	
	// 메시지 상세보기
	public MessageDto message_content(int mno) {
		MessageDto dto = new MessageDto();
		String sql = "select * from message where mno = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, mno);
			rs = ps.executeQuery();
			if ( rs.next() ) {
				dto = new MessageDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5));
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}return dto;
	}
	
	// 메시지 읽음처리
	public boolean message_read( int mno ) {
		String sql = "update message set state = 1 where mno = ?;";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, mno);
			ps.executeUpdate();
			return true;
		}catch (Exception e) {
			System.out.println(e);
		}return false;
	}
}
