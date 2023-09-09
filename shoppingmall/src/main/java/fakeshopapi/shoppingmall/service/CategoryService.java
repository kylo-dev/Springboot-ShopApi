package fakeshopapi.shoppingmall.service;

import fakeshopapi.shoppingmall.domain.Category;
import fakeshopapi.shoppingmall.dto.AddCategoryDto;
import fakeshopapi.shoppingmall.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // 카테고리 생성하기
    @Transactional
    public Category addCategory(AddCategoryDto addCategoryDto){
        Category category = new Category();
        category.setName(addCategoryDto.getName());

        return categoryRepository.save(category);
    }

    // 모든 카테고리 조회
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    // 한 카테고리 정보 가져오기
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }
}
