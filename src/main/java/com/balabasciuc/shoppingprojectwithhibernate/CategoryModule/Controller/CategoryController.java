package com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Controller;

import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Domain.Category;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Service.CategoryService;
import com.balabasciuc.shoppingprojectwithhibernate.CategoryModule.Utility.Projections.CategoryProjections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createCategory(@RequestBody Category category)
    {
        categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Category", "CREATED")
                .body("Category was created!");
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<Category> getCategoryBy(@PathVariable Long categoryId)
    {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Category Entity", "FOUND")
                .body(categoryService.findCategoryById(categoryId));
    }

    @GetMapping(value = "/projections/{categoryId}")
    List<CategoryProjections.DescriptionOnly> findCategories(@PathVariable Long categoryId)
    {
        return categoryService.findByCategoryDescription(categoryId);
    }

    @GetMapping(value ="/name/{categoryName}", produces = "APPLICATION/JSON")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String categoryName)
    {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Category name is ", categoryName.toString())
                .body(categoryService.getCategoryByName(categoryName));
    }


    @PostMapping("/addProducts/{categoryName}/{productName}")
    public ResponseEntity<Category> addProductsForThisCategory(@PathVariable ("categoryName") String categoryName, @PathVariable String productName)
    {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryService.addProductForThisCategory(categoryName, productName));
    }

    @PostMapping(value = "/addPromotionToCategory/{categoryName}/{promotionType}")
    public ResponseEntity<Category> getCategoryWithPromotion(@PathVariable String categoryName, @PathVariable String promotionType)
    {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryService.addPromotionToCategory(categoryName, promotionType));
    }
}
