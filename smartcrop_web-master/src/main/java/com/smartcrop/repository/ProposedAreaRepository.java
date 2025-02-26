package com.smartcrop.repository;

import com.smartcrop.entity.ProposedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposedAreaRepository extends JpaRepository<ProposedArea, Long> {
}
