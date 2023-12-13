package com.tobeto.pair5.services.dtos.brand.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {
    @Positive(message = "Id must be a positive number")
    private int id;

    @NotNull(message = "Name field can not be empty!")
    private String name;
}
