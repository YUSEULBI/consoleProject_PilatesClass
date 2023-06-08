package pilatesClass.model;

public class refundDto {
	
	private int lessonAmount; // 결제한 수업의 금액
	private int deductedPoints; // 환불시 차감해야할 포인트보다 보유포인트가 적어서 보유한 포인트만큼한 차감한 포인트 = 보유포인트.
	private int remainingPoints; // 환불시 차감해야할 포인트보다 보유포인트가 적어서 보유포인트를 모두 차감하고 남은 포인트 = 환불금액에서 차감해야할 포인트
	private int usedPoints; // 결제당시 고객이 사용한 포인트 = 결제취소시 고객에게 환불해줘야하는 포인트
	
	public refundDto() {	}
	

	public refundDto(int lessonAmount, int deductedPoints, int remainingPoints, int usedPoints) {
		super();
		this.lessonAmount = lessonAmount;
		this.deductedPoints = deductedPoints;
		this.remainingPoints = remainingPoints;
		this.usedPoints = usedPoints;
	}

	
	@Override
	public String toString() {
		return "refundDto [lessonAmount=" + lessonAmount + ", deductedPoints=" + deductedPoints + ", remainingPoints="
				+ remainingPoints + ", usedPoints=" + usedPoints + "]";
	}

	public int getLessonAmount() {
		return lessonAmount;
	}

	public void setLessonAmount(int lessonAmount) {
		this.lessonAmount = lessonAmount;
	}

	public int getDeductedPoints() {
		return deductedPoints;
	}

	public void setDeductedPoints(int deductedPoints) {
		this.deductedPoints = deductedPoints;
	}

	public int getRemainingPoints() {
		return remainingPoints;
	}

	public void setRemainingPoints(int remainingPoints) {
		this.remainingPoints = remainingPoints;
	}

	public int getUsedPoints() {
		return usedPoints;
	}

	public void setUsedPoints(int usedPoints) {
		this.usedPoints = usedPoints;
	}
	
	
}
