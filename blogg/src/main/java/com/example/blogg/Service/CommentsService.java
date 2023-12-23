package com.example.blogg.Service;
import java.util.List;
import com.example.blogg.entity.Comments;

public interface CommentsService {
	List<Comments> CommentsbyBlogid(Long blogid);
	void SaveComment(Comments comment);
	Comments UpdateComment(Comments comment);
	void DeleteComment(Comments comment);
}
