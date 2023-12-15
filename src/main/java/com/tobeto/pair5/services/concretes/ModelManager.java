package com.tobeto.pair5.services.concretes;

import com.tobeto.pair5.core.utilities.mappers.ModelMapperService;
import com.tobeto.pair5.entities.Car;
import com.tobeto.pair5.entities.Color;
import com.tobeto.pair5.entities.Model;
import com.tobeto.pair5.repositories.ModelRepository;
import com.tobeto.pair5.services.abstracts.ModelService;
import com.tobeto.pair5.services.dtos.model.requests.AddModelRequest;
import com.tobeto.pair5.services.dtos.model.requests.DeleteModelRequest;
import com.tobeto.pair5.services.dtos.model.requests.UpdateModelRequest;
import com.tobeto.pair5.services.dtos.model.responses.GetAllModelResponse;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ModelManager implements ModelService {
    private ModelMapperService modelMapperService;
    private ModelRepository modelRepository;

    @Override
    public void add(AddModelRequest request) {
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
    public GetAllModelResponse getById(int id) {
        Model model = modelRepository.findById(id).orElseThrow();
        GetAllModelResponse response = this.modelMapperService.forResponse().map(model, GetAllModelResponse.class);
        return response;
    }
}
