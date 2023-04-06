package ru.sen.postserver.controller.api;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.postserver.dto.PostDto;
import ru.sen.postserver.entity.Post;
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

    @Override
    public String getAllPostsUser() {
        List<Post> posts = postService.getAllPostByUserId();
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
            return;
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось получить информацию об данном посте, попробуйте позднее");
            log.error("/post/{}: An error occurred with the post page: {}", postId, e.getMessage());
            return;
        }
    }

    @Override
    public String addPost(PostDto postDto,
                          BindingResult bindingResult,
                          Long userId,
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
        }
        if (interceptorService.checkIfAddingPostSuccessful(postDto, LocalDateTime.now(), userId)) {
            log.info("/add: Adding fields to the post's page was successful");
        } else {
            String error = "Добавление полей поста не удалось. Попробуйте позднее";
            model.addAttribute("error", error);
            log.error("/add: Errors occurred when adding post data: {}", error);
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
        } else {
            log.info("/delete: Deleting the post was successful, exiting the session");
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
        }
        if (interceptorService.checkIfUpdatePostSuccessful(postDto, postId)) {
            log.info("/update: post update was successful");
        } else {
            model.addAttribute("error", "Не удалось изменить данные");
            log.error("/update: Sending a message that the post could not be updated");
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
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось найти пост, попробуйте позднее");
            log.error("/change: Errors occurred when change post data: {}", e.getMessage());
        }
    }
}
