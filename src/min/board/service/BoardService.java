package min.board.service;

import java.util.List;

import min.board.dto.BoardDTO;

public interface BoardService {
//제네릭 BoardDTO 클래스 반환 자료형으로 전체 데이터를 조회한다.
	public List<BoardDTO> boardSelectAll(int page, int limit);

//boolean 반환 자료형으로 데이터를 입력한다.
	public boolean boardInsert(BoardDTO boardDTO);

//boolean 반환 자료형으로 데이터를 수정한다.
	public boolean boardUpdate(BoardDTO boardDTO);

//boolean 반환 자료형으로 데이터를 삭제한다.
	public boolean boardDelete(int num);

//BoardDTO 클래스 반환 자료형으로 특정 데이터를 조회한다.
	public BoardDTO boardSelect(int num);

//int 반환 자료형으로 입력 데이터의 개수를 구한다.
	public int boardCount( );

//void 반환 자료형으로 데이터의 조회수를 구한다.
	public void boardHitNumber(int num);

//int 반환 자료형으로 답변 데이터를 입력한다.
	public int boardReply(BoardDTO boardDTO);

//boolean 반환 자료형으로 아이디 데이터를 조회한다.
	public boolean boardId(int num, String id);

//제네릭 ? 와일드 카드 반환 자료형으로 데이터를 검색한다.
	public List<?> boardSearch(String keyword, String keyfield, int page, int limit);

//int 반환 자료형으로 검색 데이터의 개수를 구한다.
	public int boardSearchCount(String keyword, String keyfield);
}
