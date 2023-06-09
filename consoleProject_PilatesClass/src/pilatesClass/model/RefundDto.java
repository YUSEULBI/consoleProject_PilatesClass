package pilatesClass.model;

public class RefundDto {
	
	//private int lessonAmount; // 결제한 수업의 금액
	private int canceledAccumulatedPoints; // 회수해야할 총 포인트
	private int deductedPoints; // 환불시 차감해야할 포인트보다 보유포인트가 적어서 보유한 포인트만큼한 차감한 포인트 = 보유포인트.
	//private int remainingPoints; // 환불시 차감해야할 포인트보다 보유포인트가 적어서 보유포인트를 모두 차감하고 남은 포인트 = 환불금액에서 차감해야할 포인트
	private int usedPoints; // 결제당시 고객이 사용한 포인트 = 결제취소시 고객에게 환불해줘야하는 포인트
	private boolean refundSuccess; // 환불 성공실패
	
	// deductedPoint + remainingPoints = 회수해야하는 총 포인트
	// canceledAccumulatedPoints - deductedPoins = 포인트차감후 남은 회수해야할 포인트
	
	public RefundDto() {	}

	public RefundDto(int canceledAccumulatedPoints, int deductedPoints, int usedPoints, boolean refundSuccess) {
		super();
		this.canceledAccumulatedPoints = canceledAccumulatedPoints;
		this.deductedPoints = deductedPoints;
		this.usedPoints = usedPoints;
		this.refundSuccess = refundSuccess;
	}

	@Override
	public String toString() {
		return "RefundDto [canceledAccumulatedPoints=" + canceledAccumulatedPoints + ", deductedPoints="
				+ deductedPoints + ", usedPoints=" + usedPoints + ", refundSuccess=" + refundSuccess + "]";
	}

	public int getCanceledAccumulatedPoints() {
		return canceledAccumulatedPoints;
	}

	public void setCanceledAccumulatedPoints(int canceledAccumulatedPoints) {
		this.canceledAccumulatedPoints = canceledAccumulatedPoints;
	}

	public int getDeductedPoints() {
		return deductedPoints;
	}

	public void setDeductedPoints(int deductedPoints) {
		this.deductedPoints = deductedPoints;
	}

	public int getUsedPoints() {
		return usedPoints;
	}

	public void setUsedPoints(int usedPoints) {
		this.usedPoints = usedPoints;
	}

	public boolean isRefundSuccess() {
		return refundSuccess;
	}

	public void setRefundSuccess(boolean refundSuccess) {
		this.refundSuccess = refundSuccess;
	}

	

	
	
	
}
