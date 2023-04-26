package ru.sen.postserver.controller.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface GetPostApi {

    @GetMapping("/all/{user-id}")
    String getPosts(@PathVariable("user-id") Long userId,
                    Model model,
                    RedirectAttributes redirectAttributes);

    @GetMapping("/{post-id}/{user-id}")
    String getPost(@PathVariable("user-id") Long userId,
                   Model model,
                   @PathVariable("post-id") Long postId,
                   RedirectAttributes redirectAttributes);
}
