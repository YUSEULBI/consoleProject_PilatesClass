package pilatesClass.model;

public class MemberDto {
	
		//필드
		private int mno; // 식별번호pk
		private String mid; //아이디
		private String mpw; // 비밀번호
		private String mphone; // 전화번호
		private String mname; // 이름
		private int mrole; // 1.일반회원 2.강사
		
		//생성자
		public MemberDto(int mno, String mid, String mpw, String mphone, String mname, int mrole) {
			this.mno = mno;
			this.mid = mid;
			this.mpw = mpw;
			this.mphone = mphone;
			this.mname = mname;
			this.mrole = mrole;
		}
		// 빈생성자
		public MemberDto() { }

		
		// toString
		
		@Override
		public String toString() {
			return "MemberDto [mno=" + mno + ", mid=" + mid + ", mpw=" + mpw + ", mphone=" + mphone + ", mname=" + mname
					+ ", mrole=" + mrole + "]";
		}
		
		
		//  getter setter
		public int getMno() {
			return mno;
		}

		public void setMno(int mno) {
			this.mno = mno;
		}

		public String getMid() {
			return mid;
		}

		public void setMid(String mid) {
			this.mid = mid;
		}

		public String getMpw() {
			return mpw;
		}

		public void setMpw(String mpw) {
			this.mpw = mpw;
		}

		public String getMphone() {
			return mphone;
		}

		public void setMphone(String mphone) {
			this.mphone = mphone;
		}

		public String getMname() {
			return mname;
		}

		public void setMname(String mname) {
			this.mname = mname;
		}

		public int getMrole() {
			return mrole;
		}

		public void setMrole(int mrole) {
			this.mrole = mrole;
		}

		

}
