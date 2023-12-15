package com.tobeto.pair5.services.concretes;

import com.tobeto.pair5.core.utilities.mappers.ModelMapperService;
import com.tobeto.pair5.entities.Car;
import com.tobeto.pair5.entities.Color;
import com.tobeto.pair5.repositories.ColorRepository;
import com.tobeto.pair5.services.abstracts.ColorService;
import com.tobeto.pair5.services.dtos.color.requests.AddColorRequest;
import com.tobeto.pair5.services.dtos.color.requests.DeleteColorRequest;
import com.tobeto.pair5.services.dtos.color.requests.UpdateColorRequest;
import com.tobeto.pair5.services.dtos.color.responses.GetAllColorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ColorManager implements ColorService {
    private ModelMapperService modelMapperService;
    private ColorRepository colorRepository;

    @Override
    public void add(AddColorRequest request) {
        Color color = this.modelMapperService.forRequest().map(request, Color.class);
        colorRepository.save(color);
    }

    @Override
    public void delete(DeleteColorRequest request) {
        Color colorToDelete = colorRepository.findById(request.getId()).orElseThrow();
        colorRepository.delete(colorToDelete);
    }

    @Override
    public void update(UpdateColorRequest request) {
        Color colorToUpdate = colorRepository.findById(request.getId())
                .orElseThrow();

        this.modelMapperService.forRequest().map(request, colorToUpdate);


        colorRepository.saveAndFlush(colorToUpdate);
    }

    @Override
    public GetAllColorResponse getById(int id) {
        Color color = colorRepository.findById(id).orElseThrow();
        GetAllColorResponse response = this.modelMapperService.forResponse().map(color,GetAllColorResponse.class);
        return response;
    }
}
