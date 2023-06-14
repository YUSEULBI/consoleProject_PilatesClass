package pilatesClass.model;

public class MessageDto {
	private int msgno;
	private String title;
	private String content;
	private boolean state;
	private int mno;
    
    
    public MessageDto() {	}


	public MessageDto(int msgno, String title, String content, boolean state, int mno) {
		super();
		this.msgno = msgno;
		this.title = title;
		this.content = content;
		this.state = state;
		this.mno = mno;
	}
	
	// 메세지 보내기용 생성자
	public MessageDto(String title, String content, boolean state, int mno) {
		super();
		this.title = title;
		this.content = content;
		this.state = state;
		this.mno = mno;
	}


	@Override
	public String toString() {
		return "MessageDto [msgno=" + msgno + ", title=" + title + ", content=" + content + ", state=" + state
				+ ", mno=" + mno + "]";
	}





	public int getMsgno() {
		return msgno;
	}


	public void setMsgno(int msgno) {
		this.msgno = msgno;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public boolean isState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}


	public int getMno() {
		return mno;
	}


	public void setMno(int mno) {
		this.mno = mno;
	}

    
	
	
	
	
	
}
