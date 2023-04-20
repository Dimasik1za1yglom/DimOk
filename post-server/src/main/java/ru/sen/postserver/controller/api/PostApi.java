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

    @GetMapping("/all/my/{user-id}")
    String getAllPostsUser(@PathVariable("user-id") Long userId,
                           Model model,
                           RedirectAttributes redirectAttributes);

    @GetMapping("/{post-id}/{user-id}")
    String getPost(@PathVariable("user-id") Long userId,
                   Model model,
                   @PathVariable("post-id") Long postId,
                   RedirectAttributes redirectAttributes);

    @PostMapping("/add")
    String addPost(HttpServletRequest request,
                   @Valid PostDto postDto,
                   BindingResult bindingResult,
                   RedirectAttributes redirectAttributes);

    @PostMapping("/{post-id}/delete/{user-id}")
    String deletePost(@PathVariable("user-id") Long userId,
                      @PathVariable("post-id") Long postId,
                      RedirectAttributes redirectAttributes);

    @PostMapping("/{post-id}/update/{user-id}")
    String updatePost(@PathVariable("user-id") Long userId,
                      @Valid PostDto postDto,
                      BindingResult bindingResult,
                      @PathVariable("post-id") Long postId,
                      Model model,
                      RedirectAttributes redirectAttributes);

    @GetMapping("/{post-id}/change/{user-id}")
    String changePost(@PathVariable("user-id") Long userId,
                      Model model,
                      @PathVariable("post-id") Long postId,
                      RedirectAttributes redirectAttributes);

}
