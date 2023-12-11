package com.tobeto.pair5.services.concretes;

import com.tobeto.pair5.repositories.BrandRepository;
import com.tobeto.pair5.services.abstracts.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
    private final BrandRepository brandRepository;

}
