package com.tobeto.pair5.services.abstracts;

import com.tobeto.pair5.services.dtos.brand.requests.AddBrandRequest;
import com.tobeto.pair5.services.dtos.brand.requests.DeleteBrandRequest;
import com.tobeto.pair5.services.dtos.brand.requests.UpdateBrandRequest;

public interface BrandService {
    void add(AddBrandRequest request);
    void delete(DeleteBrandRequest request);
    void update(UpdateBrandRequest request);
}
