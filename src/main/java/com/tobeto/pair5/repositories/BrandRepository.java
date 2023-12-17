package com.tobeto.pair5.repositories;

import com.tobeto.pair5.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BrandRepository extends JpaRepository <Brand, Integer>{
    boolean existsByNameIgnoreCase(String name);
}
