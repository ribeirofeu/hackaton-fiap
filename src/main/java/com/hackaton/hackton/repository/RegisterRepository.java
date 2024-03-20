package com.hackaton.hackton.repository;

import com.hackaton.hackton.model.entity.RegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterEntity, UUID> {

    List<RegisterEntity> findByUserIDAndRegisterTimeBetweenOrderByRegisterTimeAsc(UUID userId, LocalDateTime registerTimeStart, LocalDateTime registerTimeEnd);
}
