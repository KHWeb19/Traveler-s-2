package com.example.demo.repository.hotel;

import com.example.demo.entity.Hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}