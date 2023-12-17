package com.tobeto.pair5.repositories;

import com.tobeto.pair5.entities.Color;
import com.tobeto.pair5.services.dtos.color.responses.GetAllColorResponse;
import com.tobeto.pair5.services.dtos.color.responses.GetColorNameResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColorRepository extends JpaRepository<Color, Integer> {
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
}
