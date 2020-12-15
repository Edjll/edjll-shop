package ru.edjll.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edjll.shop.domain.Question;
import ru.edjll.shop.service.QuestionService;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPPORT')")
    @GetMapping("/admin/question/update/{id}")
    public String getUpdateQuestionPage(
            @PathVariable Long id,
            Model model
    ) {
        Question question = questionService.getQuestionById(id);
        model.addAttribute("question", question);
        return "admin/support/question/update";
    }
}
