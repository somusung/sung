package min.member.service;

import java.util.ArrayList;

import min.member.dto.MemberDTO;

public interface MemberService {
//제네릭 MemberDTO 클래스 반환 자료형으로 전체 데이터를 조회한다.
	public ArrayList<MemberDTO> memberSelectAll(int page, int limit);

//MemberDTO 반환 자료형으로 데이터를 입력한다.
	public MemberDTO memberInsert(MemberDTO memberDTO);

//MemberDTO 클래스 반환 자료형으로 데이터를 수정한다.
	public MemberDTO memberUpdate(MemberDTO memberDTO);

//MemberDTO 클래스 반환 자료형으로 데이터를 삭제한다.
	public MemberDTO memberDelete(MemberDTO memberDTO);

//MemberDTO 클래스 반환 자료형으로 특정 회원 데이터를 조회한다.
	public MemberDTO memberSelect(MemberDTO memberDTO);

//int 반환 자료형으로 입력 데이터의 번호를 구한다.
	public int memberNumber( );

//int 반환 자료형으로 입력 데이터의 개수를 구한다.
	public int memberCount( );

//제네릭 MemberDTO 클래스 반환 자료형으로 데이터를 검색한다.
	public ArrayList<MemberDTO> memberSearch(String keyword, int page, int limit);

//int 반환 자료형으로 검색 데이터의 개수를 구한다.
	public int memberSearchCount(String keyword);

//MemberDTO 클래스 반환 자료형으로 로그인 데이터를 조회한다.
	public MemberDTO memberLogin(MemberDTO memberDTO);

//int 반환 자료형으로 아이디 데이터를 조회한다.
	public int memberId(String id);

//MemberDTO 클래스 반환 자료형으로 아이디 데이터를 검색한다.
	public MemberDTO memberSearchId(String name, String email);

//MemberDTO 클래스 반환 자료형으로 비밀번호 데이터를 검색한다.
	public MemberDTO memberSearchPassword(String id, String email);

}
