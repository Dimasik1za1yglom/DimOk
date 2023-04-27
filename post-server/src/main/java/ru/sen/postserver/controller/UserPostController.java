package ru.sen.postserver.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.postserver.controller.api.GetPostApi;
import ru.sen.postserver.entity.Post;
import ru.sen.postserver.services.PostService;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post/user")
public class UserPostController implements GetPostApi {

    private final PostService postService;

    @Override
    public String getPosts(Long userId, Model model, RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /post/user");
        var errors = Stream.<String>builder();
        try {
            List<Post> posts = postService.getAllPostByUserId(userId);
            if (posts.isEmpty()) {
                log.info("The user by id {} has no posts", userId);
                errors.add("У пользователя нету пока постов");
            } else {
                posts.forEach(post -> {
                    if (post.getText().length() > 33) {
                        post.setText(post.getText().substring(0, 33));
                    }
                });
                log.info("/post/user: receiving a request post was successful: {}", posts);
                model.addAttribute("posts", posts);
            }
        } catch (EntityNotFoundException e) {
            errors.add("Невозможно получить посты пользователя. Посмотрите позднее");
            log.error("/post/user: An error occurred with the posts page: {}", e.getMessage());
        }
        model.addAttribute("userId", userId);
        model.addAttribute("errors", errors.build().toList());
        return "user/userPosts";
    }

    @Override
    public String getPost(Long userId, Model model, Long postId, RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /user/post/{}", postId);
        try {
            Post post = postService.getPostById(postId);
            model.addAttribute("post", post);
            log.info("/post/user{}: receiving the user's post was successful. The output of the page with this post.",
                    postId);
            return "user/userPost";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось получить информацию об данном посте, попробуйте позднее");
            return String.format("redirect:/post/user/all/%d", userId);
        }
    }
}
