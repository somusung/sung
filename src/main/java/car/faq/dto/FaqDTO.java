package car.faq.dto;

public class FaqDTO {
	private int faq_code;
	private int faq_answer_code;
	private String faq_title;
	private String faq_question;
	private String faq_answer;
	private String faq_question_date;
	private String faq_answer_date;
	private int member_code;
	private boolean editable;
	public int getFaq_code() {
		return faq_code;
	}
	public void setFaq_code(int faq_code) {
		this.faq_code = faq_code;
	}
	public int getFaq_answer_code() {
		return faq_answer_code;
	}
	public void setFaq_answer_code(int faq_answer_code) {
		this.faq_answer_code = faq_answer_code;
	}
	public String getFaq_title() {
		return faq_title;
	}
	public void setFaq_title(String faq_title) {
		this.faq_title = faq_title;
	}
	public String getFaq_question() {
		return faq_question;
	}
	public void setFaq_question(String faq_question) {
		this.faq_question = faq_question;
	}
	public String getFaq_answer() {
		return faq_answer;
	}
	public void setFaq_answer(String faq_answer) {
		this.faq_answer = faq_answer;
	}
	public String getFaq_question_date() {
		return faq_question_date;
	}
	public void setFaq_question_date(String faq_question_date) {
		this.faq_question_date = faq_question_date;
	}
	public String getFaq_answer_date() {
		return faq_answer_date;
	}
	public void setFaq_answer_date(String faq_answer_date) {
		this.faq_answer_date = faq_answer_date;
	}
	
	public int getMember_code() {
		return member_code;
	}
	public void setMember_code(int member_code) {
		this.member_code = member_code;
	}
	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	@Override
	public String toString() {
		return "FaqDTO [faq_code=" + faq_code + ", faq_answer_code=" + faq_answer_code + ", faq_title=" + faq_title
				+ ", faq_question=" + faq_question + ", faq_answer=" + faq_answer + ", faq_question_date="
				+ faq_question_date + ", faq_answer_date=" + faq_answer_date + ", member_code=" + member_code + "]";
	}
	

	

}
