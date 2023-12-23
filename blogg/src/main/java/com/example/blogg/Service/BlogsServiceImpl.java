package com.example.blogg.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogg.entity.Blogs;
import com.example.blogg.entity.Profile;
import com.example.blogg.repository.BlogsRepository;


@Service
public class BlogsServiceImpl implements BlogsService{
	
	@Autowired
	private BlogsRepository blogRepository;

	@Override
	public Blogs saveBlogs(Blogs blog) {
		System.out.println("Entered");
		return blogRepository.save(blog);
	}

	@Override
	public List<Blogs> fetchAllBlogs() {
		List<Blogs> allBlogs = blogRepository.findAll();
		return allBlogs;
	}

	@Override
	public Blogs getBlogsById(Long authorid) {
		Optional<Blogs> blog = blogRepository.findById(authorid);
        if (blog.isPresent()) {
            return blog.get();
        }
		return null;
	}

	@Override
	public Blogs updateBlogsByUsername(String username, Blogs updatedBlog) {
	    // Find all blogs for the given username
		System.out.println(username);
	    List<Blogs> blogs = blogRepository.findByUsername(username);
	    System.out.println(blogs.isEmpty()+" "+updatedBlog.getAuthorid()+" "+updatedBlog.getUsername());
	    if (!blogs.isEmpty()) {
	        for (Blogs originalBlog : blogs) {
	        	System.out.println(originalBlog.getAuthorid()+" "+updatedBlog.getAuthorid());
	            if (Objects.equals(originalBlog.getAuthorid(), updatedBlog.getAuthorid())) {

	                // Update the found blog
	            	if (Objects.nonNull(updatedBlog.getBlogtype()) && !"".equalsIgnoreCase(updatedBlog.getBlogtype())) {
	                    originalBlog.setBlogtype(updatedBlog.getBlogtype());
	                }
	                if (Objects.nonNull(updatedBlog.getTitle()) && !"".equalsIgnoreCase(updatedBlog.getTitle())) {
	                    originalBlog.setTitle(updatedBlog.getTitle());
	                }
	                if (Objects.nonNull(updatedBlog.getContent()) && !"".equalsIgnoreCase(updatedBlog.getContent())) {
	                    originalBlog.setContent(updatedBlog.getContent());
	                }
	                if (Objects.nonNull(updatedBlog.getDate())) {
	                    originalBlog.setDate(updatedBlog.getDate());
	                }

	                // Save the updated blog
	                return blogRepository.save(originalBlog);
	            }
	        }
	    }
	    System.out.println("Not entered");
	    // Handle the case when no matching blog is found.
	    return null;
	}




	@Override
	public String deleteBlogsById(Long authorid) {
	    List<Blogs> blogs = blogRepository.findByAuthorid(authorid);

	    if (!blogs.isEmpty()) {
	        blogRepository.deleteAll(blogs);
	        return "Blogs deleted successfully";
	    }

	    return "No blogs found with the specified authorid";
	}



	@Override
	public List<Blogs> getBlogsByUsername(String username) {
	    return blogRepository.findByUsername(username);
	}

	@Override
	public void updateBlogsByProfile(String username, Profile updatedProfile) {
		List<Blogs> blogs = blogRepository.findByUsername(username);
		for(Blogs blog : blogs) {
			blog.setUsername(updatedProfile.getUsername());
		}
		
	}

}
