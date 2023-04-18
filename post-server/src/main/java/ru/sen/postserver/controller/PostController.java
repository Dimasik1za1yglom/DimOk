package ru.sen.postserver.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.postserver.controller.api.PostApi;
import ru.sen.postserver.dto.PostDto;
import ru.sen.postserver.entity.Post;
import ru.sen.postserver.jwt.exception.AuthException;
import ru.sen.postserver.jwt.service.AuthService;
import ru.sen.postserver.services.ErrorInterceptorService;
import ru.sen.postserver.services.PostService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController implements PostApi {

    private final PostService postService;
    private final ErrorInterceptorService interceptorService;
    private final AuthService authService;

    @Override
    public String getAllPostsUser(Long userId, Model model, RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /post");
        try {
            List<Post> posts = postService.getAllPostByUserId(userId);
            posts.forEach(post -> {
                if (post.getText().length() > 33) {
                    post.setText(post.getText().substring(0, 33));
                }
            });
            log.info("/post: receiving a request post was successful: {}", posts);
            model.addAttribute("posts", posts);
            return "myPosts";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось получить информацию об существующих постах, попробуйте позднее");
            log.error("/post: An error occurred with the posts page: {}", e.getMessage());
            return "redirect:/user/myprofile";
        }
    }

    @Override
    public String getPost(Model model,
                          Long postId,
                          RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /post/{}", postId);
        try {
            Post post = postService.getPostById(postId);
            model.addAttribute("post", post);
            log.info("/post/{}: receiving the user's post was successful. The output of the page with this post.",
                    postId);
            return "myPost";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось получить информацию об данном посте, попробуйте позднее");
            log.error("/post/{}: An error occurred with the post page: {}", postId, e.getMessage());
            return "myPosts";
        }
    }

    @Override
    public String addPost(HttpServletRequest request,
                          PostDto postDto,
                          BindingResult bindingResult,
                          Model model) {
        log.info("receiving a request for /add");
        if (bindingResult.hasErrors()) {
            log.warn("/add: Error entering values into the form");
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("post", postDto);
            model.addAttribute("errors", errors);
            log.info("/add: Errors were received when filling out the form for creating post page fields: {}", errors);
            return "fieldsPost";
        }
        try {
            Long userId = authService.getIdUserByRefreshToken(request);
            log.info("getting the token from the request was successful: user id {}", userId);
            if (interceptorService.checkIfAddingPostSuccessful(postDto, LocalDateTime.now(), userId)) {
                log.info("/add: Adding fields to the post's page was successful");
                return "redirect:/post";
            } else {
                String error = "Добавление полей поста не удалось. Попробуйте позднее";
                model.addAttribute("error", error);
                log.error("/add: Errors occurred when adding post data: {}", error);
                return "fieldsPost";
            }
        } catch (AuthException e) {
            log.error("getting the token from the request was failed: {}", e.getMessage());
            return "redirect:/user/logout";
        }
    }

    @Override
    public String deletePost(Long postId,
                             RedirectAttributes redirectAttributes) {
        log.info("receiving a request for /delete");
        if (!interceptorService.checkIfDeletingPostSuccessful(postId)) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось удалить пост. Попробуйте позднее");
            log.error("/delete: Error on deleting a post under id: {}", postId);
            return String.format("redirect:/post/%d", postId);
        } else {
            log.info("/delete: Deleting the post was successful, exiting the session");
            return "redirect:/post";
        }
    }

    @Override
    public String updatePost(PostDto postDto,
                             BindingResult bindingResult,
                             Long postId,
                             Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("post", postDto);
            model.addAttribute("errors", errors);
            log.info("/update: Errors were received when filling out the form for change post page fields: {}", errors);
            return "changePost";
        }
        if (interceptorService.checkIfUpdatePostSuccessful(postDto, postId)) {
            log.info("/update: post update was successful");
            return "redirect:/post";
        } else {
            model.addAttribute("error", "Не удалось изменить данные");
            log.error("/update: Sending a message that the post could not be updated");
            return String.format("redirect:/post/%d/change", postId);
        }
    }

    @Override
    public String changePost(Model model,
                             Long postId,
                             RedirectAttributes redirectAttributes) {
        try {
            Post post = postService.getPostById(postId);
            model.addAttribute("post", post);
            log.info("/change: getting a form of fields for changing post");
            return "changePost";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось найти пост, попробуйте позднее");
            log.error("/change: Errors occurred when change post data: {}", e.getMessage());
            return "redirect:/post";
        }
    }
}
