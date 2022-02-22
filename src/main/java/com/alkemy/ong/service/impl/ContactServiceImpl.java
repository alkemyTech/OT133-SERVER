package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.entity.Contact;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactMapper mapper;

    @Autowired
    private ContactRepository repository;

    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        Contact contact = mapper.dto2Contact(contactDTO);
        repository.save(contact);
        ContactDTO contactResponse = mapper.contact2DTO(contact);
        return contactResponse;
    }


    @Override
    public List<ContactDTO> getAll() {
        List<Contact> contactList = this.repository.findAll();
        if (contactList.isEmpty()) {
            return null;
        } else {
            return contactList.stream().
                    map(contact -> this.mapper.contact2DTO(contact)).
                    collect(Collectors.toList());
        }
    }

}
