/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryDTOList;
import com.alkemy.ong.exceptions.CategoryServiceException;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.Category;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImp implements Category {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryDTOList> getAllName() throws CategoryServiceException {
        Iterable<Object[]> categories = categoryRepository.getAllName();
        List<CategoryDTOList> result = new ArrayList<CategoryDTOList>();
        for (Object[] category : categories) {
            CategoryDTOList categoryListRequestDTO = modelMapper.map(category, CategoryDTOList.class);
            result.add(categoryListRequestDTO);
        }
        return result;
    }

}
