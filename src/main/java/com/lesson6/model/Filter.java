package com.lesson6.model;

import lombok.*;

import java.util.Date;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Filter {
    //список рейсов:
    // по дате (в один день)
    // по промежутку даты (с даты-по дату)
    // городу отправки
    // городу назначения
    // модели самолета
    private String model;
    private Date dateFrom;
    private Date dateTo;
    private String cityFrom;
    private String cityTo;
}
