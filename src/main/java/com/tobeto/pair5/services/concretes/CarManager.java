package com.tobeto.pair5.services.concretes;

import com.tobeto.pair5.core.utilities.mappers.ModelMapperService;
import com.tobeto.pair5.entities.Car;
import com.tobeto.pair5.entities.Model;
import com.tobeto.pair5.repositories.CarRepository;
import com.tobeto.pair5.services.abstracts.CarService;
import com.tobeto.pair5.services.abstracts.ColorService;
import com.tobeto.pair5.services.abstracts.ModelService;
import com.tobeto.pair5.services.dtos.car.requests.AddCarRequest;
import com.tobeto.pair5.services.dtos.car.requests.DeleteCarRequest;
import com.tobeto.pair5.services.dtos.car.requests.UpdateCarRequest;
import com.tobeto.pair5.services.dtos.car.responses.GetAllCarResponse;
import com.tobeto.pair5.services.dtos.car.responses.GetByIdCarResponse;
import com.tobeto.pair5.services.dtos.car.responses.GetCustomCarResponse;
import com.tobeto.pair5.services.dtos.color.responses.GetAllColorResponse;
import com.tobeto.pair5.services.dtos.model.responses.GetAllModelResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CarManager implements CarService {
    private ModelMapperService modelMapperService;
    private CarRepository carRepository;
    private ModelService modelService;
    private ColorService colorService;




    @Override
    public void add(AddCarRequest request) {
        if (!checkIfModelExists(request.getModelId())) {
            throw new RuntimeException("The model not exist!" );
        }
        if (!checkIfColorExists(request.getColorId())) {
            throw new RuntimeException("The color not exist!" );
        }
        if (carRepository.existsByPlate(request.getPlate())) {
            throw new RuntimeException("Plate is already exist!" );
        }
        Car car = this.modelMapperService.forRequest().map(request, Car.class);
        car.getPlate().replaceAll("\\s","");
        carRepository.save(car);
    }

    @Override
    public void delete(DeleteCarRequest request) {
        Car carToDelete = carRepository.findById(request.getId()).orElseThrow();
        carRepository.delete(carToDelete);
    }

    @Override
    public void update(UpdateCarRequest request) {

        Car carToUpdate = carRepository.findById(request.getId())
                .orElseThrow();

        this.modelMapperService.forRequest().map(request, carToUpdate);


        carRepository.saveAndFlush(carToUpdate);
    }


    @Override
    public List<GetAllCarResponse> getAll() {
        List<Car> cars = carRepository.findAll();
        List<GetAllCarResponse> carResponses = cars.stream()
                .map(car -> this.modelMapperService
                        .forResponse().map(car, GetAllCarResponse.class))
                .collect(Collectors.toList());
        return carResponses;
    }


    @Override
    public GetByIdCarResponse getById(int id) {
       Car car = carRepository.findById(id).orElseThrow();
       GetByIdCarResponse carResponses = this.modelMapperService.forResponse().map(car, GetByIdCarResponse.class);
       return carResponses;
    }

    @Override
    public List<GetCustomCarResponse> getAllCustom() {
        List<Car> cars = carRepository.findAll();
        List<GetCustomCarResponse> carResponses = cars.stream()
                .map(car -> this.modelMapperService
                        .forResponse().map(car, GetCustomCarResponse.class))
                .collect(Collectors.toList());
        return carResponses;

    }

    private boolean checkIfModelExists(int id) {
        GetAllModelResponse model= modelService.getById(id);
        return model != null;
    }
    private boolean checkIfColorExists(int id) {
        GetAllColorResponse color= colorService.getById(id);
        return color != null;
    }



}
