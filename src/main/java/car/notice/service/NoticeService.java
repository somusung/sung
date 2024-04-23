package car.notice.service;

import java.util.ArrayList;

import car.notice.dto.NoticeDTO;

public interface NoticeService {

	 public ArrayList<NoticeDTO> noticeSelectAll( );
	 public NoticeDTO noticeSelect(int notice_code);
	 public NoticeDTO noticeInsert(NoticeDTO noticeDTO);
	 public NoticeDTO noticeUpdate(NoticeDTO noticeDTO);
	 public NoticeDTO noticeDelete(int notice_code);
	 
	 public ArrayList<NoticeDTO> noticeSelectEventAll( );
	 public NoticeDTO noticeSelectEvent(int notice_code);
	 public NoticeDTO noticeUpdateEvent(NoticeDTO noticeDTO);
	 }
