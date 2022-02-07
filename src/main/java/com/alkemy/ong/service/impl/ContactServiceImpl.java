package com.alkemy.ong.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.entity.Contact;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ContactMapper contactMapper;

	@Override
	public Optional<List<ContactDTO>> getAll() {
		List<Contact> contactList = this.contactRepository.findAll();
		if(contactList.isEmpty()) {
			return Optional.empty();
		}else {
			return Optional.of(
						contactList.stream().
						map(contact -> this.contactMapper.toContactDTO(contact)).
						collect(Collectors.toList())
					);
		}
	}
	
	
}
