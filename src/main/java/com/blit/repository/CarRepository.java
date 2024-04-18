package com.blit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blit.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long>{
	
	 List<Car> findCarByBrand(String brand);
	
	 List<Car>findCarByColor(String color);

	 List<Car> findCarByYear(int year);
	 
	 List<Car> findCarByBrandAndModel(String brand,String model);
	 
	 List<Car> findCarByBrandOrderByYearAsc(String brand);
}
