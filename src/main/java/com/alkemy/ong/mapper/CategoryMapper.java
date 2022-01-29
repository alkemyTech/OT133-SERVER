package com.alkemy.ong.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;
import org.springframework.stereotype.Component;


@Component
public class CategoryMapper {

  public Category categoryDTO2Entity(CategoryDTO dto) {
    Category category = new Category();
    category.setName(dto.getName());
    category.setDescription(dto.getDescription());
    category.setSoftDelete(dto.isSoftDelete());
    category.setTimestamps(string2LocalDate(dto.getTimestamps()));
    category.setImage(dto.getImage());
    return category;
  }

  public CategoryDTO categoryEntity2DTO(Category entity) {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setName(entity.getName());
    categoryDTO.setDescription(entity.getDescription());
    categoryDTO.setImage(entity.getImage());
    categoryDTO.setSoftDelete(entity.isSoftDelete());
    categoryDTO.setTimestamps(LocalDate2String(entity.getTimestamps()));
    return categoryDTO;

  }


  public LocalDateTime string2LocalDate(String stringDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDateTime.parse(stringDate, formatter);
  }

  public String LocalDate2String(LocalDateTime localDateTime) {
    DateTimeFormatter isoFecha = DateTimeFormatter.ISO_LOCAL_DATE;
    return localDateTime.format(isoFecha);
  }


}
