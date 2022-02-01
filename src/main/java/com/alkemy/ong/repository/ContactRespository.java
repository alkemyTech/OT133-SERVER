package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.entity.Contact;

@Repository
public interface ContactRespository extends JpaRepository<Contact, String>{

}
