package car.faq.service;


import java.util.ArrayList;


import car.faq.dto.FaqDTO;

public interface FaqService {

		 public ArrayList<FaqDTO> faqSelectAll();
		 public FaqDTO faqSelect(int faq_code, int member_code);
		 public FaqDTO faqInsert(FaqDTO faqDTO, int memberCode);
		 public FaqDTO faqUpdate(FaqDTO faqDTO);
		 public FaqDTO faqDelete(int faq_code);
		 public ArrayList<FaqDTO> faqSearch(String faq_title);
		 public FaqDTO faqAnswerSelect(int faq_answer_code);
		 public FaqDTO faqAnswerUpdate(FaqDTO faqDTO);
		 public FaqDTO faqAnswerDelete(int faq_answer_code);
		 }


