package com.example.webappvolunteer.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ActionInfoDto {

    @Getter
    @Setter
    private String actionName;

    @Getter
    @Setter
    private LocalDate actionStart;

    @Getter
    @Setter
    private String actionEnd;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private List<String> eventName;

    public void setEventNamesFromString(String eventNames) {
        if (eventNames != null && !eventNames.isEmpty()) {
            this.eventName = Arrays.asList(eventNames.split(",\\s*")); // Разделяем по запятой и пробелу
        } else {
            this.eventName = List.of(); // Если строка пуста, устанавливаем пустой список
        }
    }
    // Дополнительно, если нужен метод для получения eventNames в виде строки
    public String getEventNames() {
        return String.join(", ", eventName); // Объединяем названия мероприятий в строку
    }
}
