package ru.edjll.shop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import ru.edjll.shop.controller.ControllerUtils;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.Question;
import ru.edjll.shop.service.QuestionService;

import java.util.Map;

@RestController
public class QuestionRestController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private QuestionService questionService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/support/question/create")
    public ResponseEntity<Map<String, String>> addQuestion(
            @AuthenticationPrincipal User user,
            @RequestPart(name = "question") Question question,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        questionService.addQuestion(question, user);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAuthority('SUPPORT') or hasAuthority('ADMIN')")
    @PostMapping(value = "/support/question/update")
    public ResponseEntity<Map<String, String>> addAnswer(
            @AuthenticationPrincipal User user,
            @RequestPart(name = "question") Question question,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = controllerUtils.getErrors(bindingResult);
            return ResponseEntity.badRequest().body(errors);
        }
        questionService.addAnswer(question, user);
        return ResponseEntity.ok().body(null);
    }
}
