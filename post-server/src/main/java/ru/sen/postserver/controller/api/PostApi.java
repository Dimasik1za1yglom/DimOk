package ru.sen.postserver.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.postserver.dto.PostDto;

public interface PostApi {

    @GetMapping("/{user-id}")
    String getAllPostsUser(@PathVariable("user-id") Long userId,
                           Model model,
                           RedirectAttributes redirectAttributes);

    @GetMapping("/{post-id}")
    String getPost(Model model, @PathVariable("post-id") Long postId,
                   RedirectAttributes redirectAttributes);

    @PostMapping("/add")
    String addPost(HttpServletRequest request,
                   @Valid PostDto postDto,
                   BindingResult bindingResult,
                   Model model);

    @PostMapping("/{post-id}/delete")
    String deletePost(@PathVariable("post-id") Long postId,
                      RedirectAttributes redirectAttributes);

    @PostMapping("/{post-id}/update")
    String updatePost(@Valid PostDto postDto,
                      BindingResult bindingResult,
                      @PathVariable("post-id") Long postId,
                      Model model);

    @GetMapping("/{post-id}/change")
    String changePost(Model model, @PathVariable("post-id") Long postId,
                      RedirectAttributes redirectAttributes);

}
