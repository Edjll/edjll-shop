package ru.edjll.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SupportController {

    @GetMapping("/support/question")
    public String getSupportQuestionPage() {
        return "main/support/question";
    }
}
