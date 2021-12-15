package com.example.demo.repositories;
import com.example.demo.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LevelRepository extends JpaRepository<Level,Long> {

}