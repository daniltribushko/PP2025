package ru.tdd.backend.model.entities.vacancies;

import jakarta.persistence.*;
import ru.tdd.backend.model.DBEntity;
import ru.tdd.backend.model.dto.DtoEntity;
import ru.tdd.backend.model.dto.vacancies.VacancyResponseDto;
import ru.tdd.backend.model.entities.DBFile;
import ru.tdd.backend.model.entities.users.User;

@Entity
public class VacancyResponse extends DBEntity implements DtoEntity<VacancyResponseDto> {
    @JoinColumn(name = "author")
    @ManyToOne(cascade = CascadeType.DETACH)
    private User author;

    @JoinColumn(name = "resume")
    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private DBFile resume;

    @Column(length = 2000)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "vacancy")
    private Vacancy vacancy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResponseState responseState;

    public VacancyResponse() {}

    public VacancyResponse(User author, DBFile resume, String answer, Vacancy vacancy) {
        this.author = author;
        this.resume = resume;
        this.answer = answer;
        this.vacancy = vacancy;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public DBFile getResume() {
        return resume;
    }

    public void setResume(DBFile resume) {
        this.resume = resume;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public ResponseState getResponseState() {
        return responseState;
    }

    public void setResponseState(ResponseState responseState) {
        this.responseState = responseState;
    }

    @Override
    public VacancyResponseDto toDto() {
        return new VacancyResponseDto(id, author.toDto(), resume.toDto(), answer, responseState);
    }
}
