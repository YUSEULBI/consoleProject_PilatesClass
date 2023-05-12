package pilatesClass.model;

public class MessageDto {
	private int mno;
	private String title;
	private String content;
	private boolean state;
	private int 회원번호_fk;
    
    
    public MessageDto() {	}


	public MessageDto(int mno, String title, String content, boolean state, int 회원번호_fk) {
		super();
		this.mno = mno;
		this.title = title;
		this.content = content;
		this.state = state;
		this.회원번호_fk = 회원번호_fk;
	}

    
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}


	public int getMno() {
		return mno;
	}


	public void setMno(int mno) {
		this.mno = mno;
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


	public int get회원번호_fk() {
		return 회원번호_fk;
	}


	public void set회원번호_fk(int 회원번호_fk) {
		this.회원번호_fk = 회원번호_fk;
	}
	
	
	
	
	
}
