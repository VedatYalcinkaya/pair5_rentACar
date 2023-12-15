package com.tobeto.pair5.services.concretes;

import com.tobeto.pair5.core.utilities.mappers.ModelMapperService;
import com.tobeto.pair5.entities.Car;
import com.tobeto.pair5.entities.Color;
import com.tobeto.pair5.entities.Model;
import com.tobeto.pair5.repositories.ModelRepository;
import com.tobeto.pair5.services.abstracts.BrandService;
import com.tobeto.pair5.services.abstracts.ModelService;
import com.tobeto.pair5.services.dtos.brand.responses.GetAllBrandResponse;
import com.tobeto.pair5.services.dtos.brand.responses.GetBrandIdResponse;
import com.tobeto.pair5.services.dtos.model.requests.AddModelRequest;
import com.tobeto.pair5.services.dtos.model.requests.DeleteModelRequest;
import com.tobeto.pair5.services.dtos.model.requests.UpdateModelRequest;
import com.tobeto.pair5.services.dtos.model.responses.GetAllModelResponse;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ModelManager implements ModelService {
    private ModelMapperService modelMapperService;
    private ModelRepository modelRepository;
    private BrandService brandService;


    @Override
    public void add(AddModelRequest request) {
        if (checkIfBrandNotExists(request.getBrand().getId())) {
            throw new RuntimeException("Brand does not exist");
        }
        Model model = this.modelMapperService.forRequest().map(request, Model.class);
        modelRepository.save(model);
    }


    @Override
    public void delete(DeleteModelRequest request) {
        Model modelToDelete = modelRepository.findById(request.getId()).orElseThrow();
        modelRepository.delete(modelToDelete);
    }

    @Override
    public void update(UpdateModelRequest request) {
        Model modelToUpdate = modelRepository.findById(request.getId())
                .orElseThrow();

        this.modelMapperService.forRequest().map(request, modelToUpdate);

        modelRepository.saveAndFlush(modelToUpdate);
    }

    @Override
    public List<GetAllModelResponse> getAll() {
        List<Model> model = modelRepository.findAll();
        return model.stream()
                .map(model1 -> this.modelMapperService.forRequest().map(model,GetAllModelResponse.class))
                .toList();
    }

    @Override
    public GetAllModelResponse getById(int id) {
        Model model = modelRepository.findById(id).orElseThrow();
        GetAllModelResponse response = this.modelMapperService.forResponse().map(model, GetAllModelResponse.class);
        return response;
    }

    private boolean checkIfBrandNotExists(int id){
        GetAllBrandResponse brand = brandService.getById(id);
        if(brand != null){
            return false;
        }
        return true;
    }
}
