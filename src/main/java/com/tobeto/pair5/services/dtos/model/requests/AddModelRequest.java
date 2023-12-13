package com.tobeto.pair5.services.dtos.model.requests;

import com.tobeto.pair5.services.dtos.brand.responses.GetAllBrandResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddModelRequest {

    @NotNull(message = "Name field can not be empty")
    private String name;

    private GetAllBrandResponse brand;
}
