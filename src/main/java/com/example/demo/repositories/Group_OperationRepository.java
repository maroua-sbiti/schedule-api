package com.example.demo.repositories;

import com.example.demo.entity.Group_Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Group_OperationRepository extends JpaRepository<Group_Operation,Long> {
}
