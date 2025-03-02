package ru.tdd.backend.model.dto;

public class ObjectDto {
    private Object value;

    public ObjectDto(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
