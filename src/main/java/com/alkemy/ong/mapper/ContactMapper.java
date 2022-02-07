package com.alkemy.ong.mapper;

import org.springframework.stereotype.Component;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.entity.Contact;

@Component
public class ContactMapper {

	public ContactDTO toContactDTO(Contact contact) {
		return new ContactDTO(contact.getName(),contact.getPhone(),contact.getEmail(),contact.getMessage(),contact.getTimestamps());
	}
}
