package com.tobeto.pair5.services.concretes;

import com.tobeto.pair5.core.utilities.mappers.ModelMapperService;
import com.tobeto.pair5.entities.concretes.Rental;
import com.tobeto.pair5.repositories.RentalRepository;
import com.tobeto.pair5.services.abstracts.CarService;
import com.tobeto.pair5.services.abstracts.RentalService;
import com.tobeto.pair5.services.abstracts.UserService;
import com.tobeto.pair5.services.dtos.car.responses.GetByIdCarResponse;
import com.tobeto.pair5.services.dtos.rental.requests.AddRentalRequest;
import com.tobeto.pair5.services.dtos.rental.requests.DeleteRentalRequest;
import com.tobeto.pair5.services.dtos.rental.requests.UpdateRentalRequest;
import com.tobeto.pair5.services.dtos.rental.responses.GetAllRentalResponse;
import com.tobeto.pair5.services.dtos.rental.responses.GetByIdRentalResponse;
import com.tobeto.pair5.services.dtos.user.responses.GetByIdUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private final RentalRepository rentalRepository;
    private ModelMapperService modelMapperService;
    private CarService carService;
    private UserService userService;

    @Override
    public void add(AddRentalRequest request) {

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new RuntimeException("When renting a car, the end date must be later than the start date");
        }

        if (!checkIfCarNotExists(request.getCar().getId())) {
            throw new RuntimeException("Car not found!");
        }

        if(!checkIfUserNotExists(request.getUser().getId())){
            throw new RuntimeException("User not found!");
        }

        if(!checkIfRentalIsValid(request.getStartDate(), request.getEndDate(), 25)){
            throw new RuntimeException("A car cannot be rented more than 25 days!");
        }

        Rental rental = this.modelMapperService.forRequest().map(request, Rental.class);

        GetByIdCarResponse carResponse = carService.getById(request.getCar().getId());

        rental.setStartKilometer(carResponse.getKilometer());

        //rental.setTotalPrice(calculateTotalPrice(request.getStartDate(), request.getEndDate(),carResponse.getDailyPrice()));

        rentalRepository.save(rental);
    }

    @Override
    public void delete(DeleteRentalRequest request) {
        Rental rentalToDelete = rentalRepository.findById(request.getId()).orElseThrow();
        rentalRepository.delete(rentalToDelete);
    }

    @Override
    public void update(UpdateRentalRequest request) {
        Rental rentalToUpdate = rentalRepository.findById(request.getId()).orElseThrow();

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new RuntimeException("When renting a car, the end date must be later than the start date");
        }

        if (!checkIfCarNotExists(request.getCar().getId())) {
            throw new RuntimeException("Car not found!");
        }

        if(!checkIfUserNotExists(request.getUser().getId())){
            throw new RuntimeException("User not found!");
        }

        if(!checkIfRentalIsValid(request.getStartDate(), request.getEndDate(), 25)){
            throw new RuntimeException("A car cannot be rented more than 25 days!");
        }

        Rental rental = this.modelMapperService.forRequest().map(request, Rental.class);

        GetByIdCarResponse carResponse = carService.getById(request.getCar().getId());

        rental.setStartKilometer(carResponse.getKilometer());

        //rental.setTotalPrice(calculateTotalPrice(request.getStartDate(), request.getEndDate(),carResponse.getDailyPrice()));

        this.modelMapperService.forRequest().map(request, rentalToUpdate);
        rentalRepository.saveAndFlush(rentalToUpdate);
    }

    @Override
    public List<GetAllRentalResponse> getAll() {
        List<Rental> rentals = rentalRepository.findAll();
        List<GetAllRentalResponse> responses = rentals.stream().map(rental -> this.modelMapperService.forResponse().map(rental, GetAllRentalResponse.class))
                .toList();
        return responses;
    }

    @Override
    public GetByIdRentalResponse getById(int id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        GetByIdRentalResponse response = this.modelMapperService.forResponse().map(rental, GetByIdRentalResponse.class);
        return response;
    }

    private boolean checkIfCarNotExists(int id) {
        GetByIdCarResponse car = this.carService.getById(id);

        if (car != null) {
            return true;
        }
        return false;
    }

    private boolean checkIfUserNotExists(int id) {
        GetByIdUserResponse user = this.userService.getById(id);

        if (user != null) {
            return true;
        }
        return false;
    }

    private boolean checkIfRentalIsValid(LocalDate start, LocalDate end, int maxDays) {
            long daysBetween = ChronoUnit.DAYS.between(start, end);
            return daysBetween >= 0 && daysBetween <= maxDays;
    }

    private double calculateTotalPrice(LocalDate start, LocalDate end, double dailyPrice){
        long daysBetween = ChronoUnit.DAYS.between(start, end);
        return daysBetween * dailyPrice;
    }

}

