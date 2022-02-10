package com.alkemy.ong.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.entity.Category;
import com.alkemy.ong.exception.CategoryException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  public CategoryDTO findCategoryById(String id) {
    try {
      Category foundCategory = categoryRepository.findById(id).get();
      return categoryMapper.categoryEntity2DTO(foundCategory);
    } catch (Exception e) {
      System.out.println("No encontrado");
    }
    return null;

  }

  @Override
  public CategoryDTO findById(String id) {
    Category category = categoryRepository.findById(id).get();
    return categoryMapper.categoryEntity2DTO(category);
  }

  @Override
  public void delete(String id) {
    Category category = categoryRepository.findById(id).get();
    categoryRepository.delete(category);
  }


  @Override
  public Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO, UUID id) {
    Optional<Category> optCategory = this.categoryRepository.findById(id.toString());

    if (!optCategory.isPresent()) {
      return Optional.empty();
    } else {
      Category categoryUpdate =
          this.categoryMapper.categoryDTO2Entity(categoryDTO, optCategory.get());

      return Optional
          .of(this.categoryMapper.categoryEntity2DTO(this.categoryRepository.save(categoryUpdate)));
    }
  }

  @Override
  public CategoryDTO create(CategoryDTO categoryDTO) throws Exception {
    verifyCategory(categoryDTO);
    Category category = categoryMapper.categoryDTO2Entity2(categoryDTO);
    Category categorySaved = categoryRepository.save(category);
    CategoryDTO result = categoryMapper.categoryEntity2DTO(categorySaved);
    return result;
  }

  public void verifyCategory(CategoryDTO category) throws CategoryException {

    if (category.getName() == null || category.getName().isEmpty()) {
      throw new CategoryException("Name null or empty");
    }

  }
  	/***
  	 * Lo que se hace es pasarle al obj Pageable result el número de pagina y el tamaño del pageable que se recibe como argumento.
  	 * De esta manera retornamos por medio del respository la lista convertida en Entity a DTO
  	 * @author Mauro
  	 */
	@Override
	public List<CategoryDTO> findAllPage(Pageable pageable) {
		
		Pageable result = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		
		return categoryRepository.findAll(result).getContent().stream().map(this.categoryMapper::categoryEntity2DTO)
				.collect(Collectors.toList());
	}
}
