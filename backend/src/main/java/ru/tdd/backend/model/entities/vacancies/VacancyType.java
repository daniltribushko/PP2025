package ru.tdd.backend.model.entities.vacancies;

/** Тип вакансии */
public enum VacancyType {
    INTERNSHIP("Стажировка"),
    FULL_TIME("Полная занятость"),
    PART_TIME("Частичная занятость");

    private String name;

    VacancyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
