package ru.sen.searchserver.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sen.searchserver.controller.api.AdminSearchRequestApi;
import ru.sen.searchserver.entity.SearchRequest;
import ru.sen.searchserver.services.ErrorInterceptorService;
import ru.sen.searchserver.services.SearchRequestService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search/admin")
public class AdminSearchRequestController implements AdminSearchRequestApi {

    private final ErrorInterceptorService interceptorService;
    private final SearchRequestService searchRequestService;

    @Override
    public String getSearchRequests(Long userId, Model model, RedirectAttributes redirectAttributes) {
        try {
            if (searchRequestService.checkIfSearchRequestExists(userId)) {
                log.info("/admin/requests/{user-id}: Checking that the search request exist was successful");
                List<SearchRequest> searchRequests = searchRequestService.getAllSearchRequestByUserId(userId);
                model.addAttribute("searchRequests", searchRequests);
                log.info("/admin/requests/{user-id}: Checking search requests was successful. List search request: {}",
                        searchRequests);
                return "adminSearchRequest";
            } else {
                String error = "История запросов пуста";
                redirectAttributes.addFlashAttribute("error", error);
                log.warn("/admin/requests/{user-id}: Null Search request by users id {}: {}", userId, error);
                return "redirect:adminSearchUsers";
            }
        } catch (EntityNotFoundException e) {
            String error = "Не удалось получить истории запросов";
            redirectAttributes.addFlashAttribute("error", error);
            log.error("/delete: Error on checking a search request by userId {}: {}", userId, e.getMessage());
            return "redirect:adminSearchUsers";
        }
    }

    @Override
    public String deleteSearchRequest(Long userId, RedirectAttributes redirectAttributes) {
        if (!interceptorService.checkIfDeletingSearchRequestSuccessful(userId)) {
            redirectAttributes.addFlashAttribute("error",
                    "Не удалось удалить историю запросов. Попробуйте позднее");
            log.error("/delete: Error on deleting a search request under id: {}", userId);
            return String.format("redirect:/admin/requests/%d", userId);
        } else {
            log.info("/delete: Deleting the search request was successful");
            return "redirect:/admin/users/all";
        }
    }
}
