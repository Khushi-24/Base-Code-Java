package com.demo.BaristaBucks.Common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PaginationResponseDto<T> {
    private Long totalRecords;
    private Integer pageNo;
    private List<T> list;

    public PaginationResponseDto(Long totalRecords, Integer pageNo, List<T> list) {
        this.totalRecords = totalRecords;
        this.pageNo = pageNo;
        this.list = list;
    }
}
