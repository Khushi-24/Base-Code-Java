package com.demo.BaristaBucks.Dto.RequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationRequestDto {
    private int page;
    private int pageSize = 2;
}
