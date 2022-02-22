package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactDTO;

import java.util.List;

public interface ContactService {

  ContactDTO save(ContactDTO contactDTO);

  List<ContactDTO> getAll();

}
