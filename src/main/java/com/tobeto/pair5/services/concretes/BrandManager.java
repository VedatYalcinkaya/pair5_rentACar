package com.tobeto.pair5.services.concretes;

import com.tobeto.pair5.core.utilities.mappers.ModelMapperService;
import com.tobeto.pair5.entities.Brand;
import com.tobeto.pair5.entities.Car;
import com.tobeto.pair5.repositories.BrandRepository;
import com.tobeto.pair5.services.abstracts.BrandService;
import com.tobeto.pair5.services.dtos.brand.requests.AddBrandRequest;
import com.tobeto.pair5.services.dtos.brand.requests.DeleteBrandRequest;
import com.tobeto.pair5.services.dtos.brand.requests.UpdateBrandRequest;
import com.tobeto.pair5.services.dtos.brand.responses.GetAllBrandResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
    private final BrandRepository brandRepository;
    private ModelMapperService modelMapperService;


    @Override
    public void add(AddBrandRequest request) {
        if (brandRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Brand already exists");
        }

        Brand brand = this.modelMapperService.forRequest().map(request, Brand.class);
        brandRepository.save(brand);
    }

    @Override
    public void delete(DeleteBrandRequest request) {
        Brand brandToDelete = brandRepository.findById(request.getId()).orElseThrow();
        brandRepository.delete(brandToDelete);
    }

    @Override
    public void update(UpdateBrandRequest request) {
        Brand brandToUpdate = brandRepository.findById(request.getId())
                .orElseThrow();

        this.modelMapperService.forRequest().map(request, brandToUpdate);


        brandRepository.saveAndFlush(brandToUpdate);
    }

    @Override
    public List<GetAllBrandResponse> getAll() {
        List<Brand> brands = brandRepository.findAll();
        List<GetAllBrandResponse> responses = brands.stream().map(brand -> this.modelMapperService.forResponse().map(brand,GetAllBrandResponse.class))
                .toList();
        return responses;
    }

    @Override
    public GetAllBrandResponse getById(int id) {
        Brand brand = brandRepository.findById(id).orElseThrow();
        GetAllBrandResponse brandResponse = this.modelMapperService.forResponse().map(brand,GetAllBrandResponse.class);
        return brandResponse;
    }
}
