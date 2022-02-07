package com.alkemy.ong.service;

import java.util.List;
import java.util.Optional;

import com.alkemy.ong.dto.ContactDTO;

public interface ContactService {

	Optional<List<ContactDTO>> getAll();
}
