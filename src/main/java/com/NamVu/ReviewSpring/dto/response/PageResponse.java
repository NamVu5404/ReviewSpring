package com.NamVu.ReviewSpring.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class PageResponse<T> implements Serializable {

    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalElement;
    private List<T> data;
}
