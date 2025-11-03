package org.example.monopatin.repository;

import org.example.monopatin.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {


        List<Monopatin> findByUserId(Long userId);

}
