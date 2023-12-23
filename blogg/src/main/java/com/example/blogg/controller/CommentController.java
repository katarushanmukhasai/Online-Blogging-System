package com.example.blogg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.blogg.Service.BlogsService;
import com.example.blogg.Service.CommentsService;
import com.example.blogg.entity.Blogs;
import com.example.blogg.entity.Comments;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentsService commentsService;
	
	@Autowired
	private BlogsService blogsService;
	
//	@GetMapping("/")
	
	
	@PostMapping("")
    public String blogComments(@RequestParam("authorid") Long authorid, Model model) {
		System.out.println(authorid);
		List<Comments> l = commentsService.CommentsbyBlogid(authorid);
		Blogs b = blogsService.getBlogsById(authorid);
		model.addAttribute("comments",l);
		model.addAttribute("blog",b);
    	return "Blogcomment";
    }
	
	@PostMapping("/createcomment")
	public String createComment(@RequestParam("blogid") Long blogid,@RequestParam("comment") String comment) {
		Comments com = new Comments(blogid,comment);
		commentsService.SaveComment(com);
		return "redirect:/user/home";
	}
	
	
}
