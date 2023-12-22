package com.sparta.plusweek.global.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public class PageDTO {

    @Positive // 0보다 큰수
    private Integer currentPage;
    private Integer size;
    private String sortBy;
    private String orderBy;

    public Pageable toPageable() {
        if (this.orderBy.equals("desc")) {
            return PageRequest.of(currentPage - 1, size, Sort.by(this.sortBy).descending());
        }
        return PageRequest.of(currentPage - 1, size, Sort.by(this.sortBy).ascending());
    }
}
