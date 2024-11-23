package com.example.mbank.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Запрос на поиск клиента")
public class UserSearchRequest {

    @Schema(description = "ФИО")
    private String name;

    @Schema(description = "Дата рождения")
    private LocalDate dateOfBirth;

    @Schema(description = "Номер телефона")
    private String phone;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Номер страницы (по умолчанию 0)")
    private int page = 0;

    @Schema(description = "Размер страницы (по умолчанию 10)")
    private int size = 10;

    @Schema(description = "Поле для сортировки.<br> " +
            "Укажите имя поля, по которому нужно сортировать результаты.<br> " +
            "Для сортировки по возрастанию просто укажите имя поля (например, 'name').<br> " +
            "Для сортировки по убыванию добавьте '-' перед именем поля (например, '-dateOfBirth').<br> " +
            "По умолчанию сортировка происходит по полю 'id'.")
    private String sortBy = "id";
}
