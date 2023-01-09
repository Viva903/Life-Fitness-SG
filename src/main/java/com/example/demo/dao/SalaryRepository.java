package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Salary;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {

}
