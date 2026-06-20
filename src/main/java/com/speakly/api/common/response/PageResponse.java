package com.speakly.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> records;

    private Integer current;

    private Integer size;

    private Long total;
}
