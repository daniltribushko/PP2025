package ru.tdd.backend.model.dto.vacancies;

import ru.tdd.backend.model.dto.FileDto;
import ru.tdd.backend.model.dto.users.UserDto;

public class VacancyResponseDto {
    private Long id;
    private UserDto author;
    private FileDto resume;
    private String answer;

    public VacancyResponseDto(Long id, UserDto author, FileDto resume, String answer) {
        this.id = id;
        this.author = author;
        this.resume = resume;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public FileDto getResume() {
        return resume;
    }

    public void setResume(FileDto resume) {
        this.resume = resume;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
