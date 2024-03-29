package com.gsilva.blog.controller;

import com.gsilva.blog.model.Post;
import com.gsilva.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView("post");
        mv.addObject("posts", postService.findAll());
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView add(Post post) {
        ModelAndView mv = new ModelAndView("postAdd");
        mv.addObject("post", post);
        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        return add(postService.findOne(id));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        postService.delete(id);
        return findAll();
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Post post, BindingResult result) {
        if (result.hasErrors()) {
            return add(post);
        }
        postService.save(post);
        return findAll();
    }
}
