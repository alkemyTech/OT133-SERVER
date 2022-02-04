package com.alkemy.ong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.ong.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, String>{

}
