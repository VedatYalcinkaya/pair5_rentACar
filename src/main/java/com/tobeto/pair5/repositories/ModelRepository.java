package com.tobeto.pair5.repositories;

import com.tobeto.pair5.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Integer> {
}