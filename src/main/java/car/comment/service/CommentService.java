package car.comment.service;

import java.util.ArrayList;

import car.comment.dto.CommentDTO;



public interface CommentService {
	 public ArrayList<CommentDTO> commentSelectAll(int parking_code);
	 public CommentDTO commentSelect(int comment_code);
	 public CommentDTO commentInsert(CommentDTO commentDTO, int member_code);
	 public CommentDTO commentUpdate(CommentDTO commentDTO);
	 public CommentDTO commentDelete(int comment_code);
	 
}
