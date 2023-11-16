package com.imageUploding.demo.Repository;


import com.imageUploding.demo.model.images;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UplodeRepository extends JpaRepository<images, Integer> {

}