package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryDTO;

public interface CategoryService {

    void delete(String id);

    CategoryDTO findById(String id);

}
