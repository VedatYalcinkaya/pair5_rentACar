package com.tobeto.pair5.services.dtos.brand.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBrandRequest {

    @NotNull(message = "Name field can not be empty!")
    private String name;

}
