package com.neoflex.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EmploymentDTO {
    private Long id;
    private String employmentStatus;
    private String employer;
    private BigDecimal salary;
    private String position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
