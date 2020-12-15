package ru.edjll.shop.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(
            name = "email"
    )
    private String email;

    @NotEmpty(message = "error.validation.question.question.empty")
    @Column(
            name = "question",
            nullable = false
    )
    private String question;

    @Column(
            name = "answer"
    )
    private String answer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "question_date",
            length = 10,
            nullable = false
    )
    private Date questionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "answer_date",
            length = 10
    )
    private Date answerDate;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
