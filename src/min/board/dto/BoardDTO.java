package min.board.dto;

public class BoardDTO {

	private int num;
	private String name;
	private String password;
	private String subject;
	private String content;
	private String attachedfile;
	private String oldfile;
	private int answernum;
	private int answerlev;
	private int answerseq;
	private int readcount;
	private String writeday;
	private String id;

	public int getNum( ) {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName( ) {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword( ) {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject( ) {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent( ) {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachedfile( ) {
		return attachedfile;
	}

	public void setAttachedfile(String attachedfile) {
		this.attachedfile = attachedfile;
	}

	public String getOldfile( ) {
		return oldfile;
	}

	public void setOldfile(String oldfile) {
		this.oldfile = oldfile;
	}

	public int getAnswernum( ) {
		return answernum;
	}

	public void setAnswernum(int answernum) {
		this.answernum = answernum;
	}

	public int getAnswerlev( ) {
		return answerlev;
	}

	public void setAnswerlev(int answerlev) {
		this.answerlev = answerlev;
	}

	public int getAnswerseq( ) {
		return answerseq;
	}

	public void setAnswerseq(int answerseq) {
		this.answerseq = answerseq;
	}

	public int getReadcount( ) {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public String getWriteday( ) {
		return writeday;
	}

	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}

	public String getId( ) {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString( ) {
		return "BoardDTO [num=" + num + ", name=" + name + ", password=" + password + ", subject=" + subject + ", content="
		  + content + ", attachedfile=" + attachedfile + ", oldfile=" + oldfile + ", answernum=" + answernum + ", answerlev="
		  + answerlev + ", answerseq=" + answerseq + ", readcount=" + readcount + ", writeday=" + writeday + ", id=" + id
		  + "]";
	}

}