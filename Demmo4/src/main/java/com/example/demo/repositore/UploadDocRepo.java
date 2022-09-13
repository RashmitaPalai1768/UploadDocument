package com.example.demo.repositore;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UploadDoc;

public interface UploadDocRepo extends JpaRepository<UploadDoc, Integer>{

}
