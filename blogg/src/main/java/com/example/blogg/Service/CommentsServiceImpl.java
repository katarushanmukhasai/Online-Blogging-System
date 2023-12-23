package com.example.blogg.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogg.entity.Comments;
import com.example.blogg.repository.CommentRepository;

@Service
public class CommentsServiceImpl implements CommentsService{

	@Autowired
	private CommentRepository commentrepository;
	
	@Override
	public List<Comments> CommentsbyBlogid(Long blogid) {
		List<Comments> cm = commentrepository.findAll();
		List<Comments> com = new ArrayList<>();
		for(Comments c : cm) {
			if(c.getBlogid() == blogid) {
				com.add(c);
			}
		}
		return com;
	}

	@Override
	public void SaveComment(Comments comment) {
		commentrepository.save(comment);
	}

	@Override
	public Comments UpdateComment(Comments comment) {
		Optional<Comments> o = commentrepository.findById(comment.getId());
		List<Comments> l = o.map(List::of).orElse(List.of());
		return l.get(0);
	}

	@Override
	public void DeleteComment(Comments comment) {
		commentrepository.deleteById(comment.getId());
	}
	

}
