package car.comment.dto;

public class CommentDTO {
	private int comment_code;
	private String comment_content;
	private String comment_date;
	private int parking_code;
	private int member_code;
	public int getComment_code() {
		return comment_code;
	}
	public void setComment_code(int comment_code) {
		this.comment_code = comment_code;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getComment_date() {
		return comment_date;
	}
	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}

	public int getParking_code() {
		return parking_code;
	}
	public void setParking_code(int parking_code) {
		this.parking_code = parking_code;
	}
	public int getMember_code() {
		return member_code;
	}
	public void setMember_code(int member_code) {
		this.member_code = member_code;
	}
	@Override
	public String toString() {
		return "CommentDTO [comment_code=" + comment_code + ", comment_content=" + comment_content + ", comment_date="
				+ comment_date + ", parking_code=" + parking_code + ", member_code=" + member_code + "]";
	}
	
}
