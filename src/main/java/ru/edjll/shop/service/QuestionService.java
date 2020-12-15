package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Question;
import ru.edjll.shop.domain.User;
import ru.edjll.shop.model.MailMessage;
import ru.edjll.shop.repository.QuestionRepository;

import java.util.Date;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MailSender mailSender;

    public Page<Question> getPageQuestion(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public void addQuestion(ru.edjll.shop.model.Question question, User user) {
        Question questionDomain = new Question();

        questionDomain.setQuestion(question.getQuestion());

        if (user != null) {
            questionDomain.setUser(user);
        } else {
            questionDomain.setEmail(question.getEmail());
        }
        questionDomain.setQuestionDate(new Date());

        questionRepository.save(questionDomain);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.getOne(id);
    }

    public void addAnswer(ru.edjll.shop.model.Question question, User user) {
        Question questionDomain = questionRepository.getOne(question.getId());

        questionDomain.setAnswer(question.getAnswer());
        questionDomain.setAnswerDate(new Date());
        questionDomain.setEmployee(employeeService.getEmployeeByUserId(user.getId()));

        MailMessage mailMessage = new MailMessage();
        mailMessage.setTitle("Ответ на вопрос #" + questionDomain.getId());
        mailMessage.setBody("<td colspan=\"3\">Вопрос: " + questionDomain.getQuestion() + "</td></tr><tr><td colspan=\"3\">Ответ: " + questionDomain.getAnswer() + "</td>");

        String email = questionDomain.getEmail();
        if (email == null) email = questionDomain.getUser().getEmail();
        mailSender.send(email, mailMessage.getTitle(), mailMessage);

        questionRepository.save(questionDomain);
    }

    public Page<Question> getQuestionsByUser(Pageable pageable, User user) {
        return questionRepository.getAllByUserId(user.getId(), pageable);
    }
}
