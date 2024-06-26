package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.WashingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IWashingMachineRepository extends JpaRepository<WashingMachine, UUID> {
    
    List<WashingMachine> findByDorm(Dorm dorm);
    List<WashingMachine> findByDormAndAssociatedDryerIsNull(Dorm dorm);
}
