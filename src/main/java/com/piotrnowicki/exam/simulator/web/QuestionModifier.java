package com.piotrnowicki.exam.simulator.web;

import com.piotrnowicki.exam.simulator.boundary.QuestionsManager;
import com.piotrnowicki.exam.simulator.entity.Answer;
import com.piotrnowicki.exam.simulator.entity.Question;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean
@ViewScoped
public class QuestionModifier {

    @Inject
    QuestionsManager qManager;

    @Inject
    FacesContext ctx;

    @Inject
    transient Logger log;

    private static final Integer NUMBER_OF_ANSWERS = 8;

    private Question question;

    private String questionId = "1";

    private String previousQuestionNumber;

    private String nextQuestionNumber;

    void loadQuestion(Long id) {
        this.question = qManager.getQuestionById(id);
    }

    public void loadAnswers() {
        while (question.getAnswers().size() < NUMBER_OF_ANSWERS) {
            question.addAnswers(new Answer());
        }
    }

    public void initCreation(ComponentSystemEvent event) {

        if (!ctx.isPostback()) {
            question = new Question();

            for (int i = 0; i < NUMBER_OF_ANSWERS; i++) {
                question.addAnswers(new Answer());
            }
        }
    }

    public void loadQuestion(String id) {
        
        List<Question> questions = qManager.getQuestions();
        
        if (id == null) {
            Long firstId = questions.get(0).getId();
            loadQuestion(firstId);
        } else {
            loadQuestion(Long.valueOf(id));
        }
        
        int idx = questions.indexOf(question);
        
        if (idx < questions.size() - 1) {
            this.nextQuestionNumber = questions.get(idx + 1).getId() + "";
        }
        
        if (idx >= 1) {
            this.previousQuestionNumber = questions.get(idx - 1).getId() + "";
        }
        
        loadAnswers();
    }

    public List<Question> allQuestions() {
        return qManager.getQuestions();
    }

    public String update() {
        boolean isUpdate = (question.getId() == null) ? false : true;

        removeEmptyAnswers();

        if (isUpdate) {
            qManager.updateQuestion(question);
            return null;
        } else {
            qManager.createQuestion(question);

            String url = "modifyQuestion.xhtml?faces-redirect=true&q=" + question.getId();
            return url;
        }

    }

    public String delete() {
        qManager.deleteQuestion(question);

        return "modifyQuestion";
    }

    void removeEmptyAnswers() {
        Iterator<Answer> it = question.getAnswers().iterator();

        while (it.hasNext()) {
            Answer answer = it.next();

            if (answer.getContent().isEmpty()) {
                it.remove();
            }
        }
    }

    public String logout() {
        log.info("Logging out user");

        ctx.getExternalContext().invalidateSession();

        return "index?faces-redirect=true";
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionNumber) {
        this.questionId = questionNumber;
    }

    public String getPreviousQuestionNumber() {
        return previousQuestionNumber;
    }

    public void setPreviousQuestionNumber(String previousQuestionNumber) {
        this.previousQuestionNumber = previousQuestionNumber;
    }

    public String getNextQuestionNumber() {
        return nextQuestionNumber;
    }

    public void setNextQuestionNumber(String nextQuestionNumber) {
        this.nextQuestionNumber = nextQuestionNumber;
    }

}
