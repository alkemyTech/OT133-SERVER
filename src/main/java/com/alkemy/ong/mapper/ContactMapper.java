package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.entity.Contact;

import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    
    public ContactDTO contact2DTO(Contact contact){
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setName(contact.getName());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setMessage(contact.getMessage());
        contactDTO.setTimestamps(contact.getTimestamps());
        return contactDTO;
    }

    public Contact dto2Contact(ContactDTO contactDTO){
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());
        contact.setMessage(contactDTO.getMessage());
        return contact;
    }

}
