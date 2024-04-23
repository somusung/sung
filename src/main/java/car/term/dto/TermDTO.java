package car.term.dto;

public class TermDTO {
	private int term_code;
	private String term_content;
	public int getTerm_code() {
		return term_code;
	}
	public void setTerm_code(int term_code) {
		this.term_code = term_code;
	}
	public String getTerm_content() {
		return term_content;
	}
	public void setTerm_content(String term_content) {
		this.term_content = term_content;
	}
	@Override
	public String toString() {
		return "TermDTO [term_code=" + term_code + ", term_content=" + term_content + "]";
	}
}
