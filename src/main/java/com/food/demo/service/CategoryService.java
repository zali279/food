package com.food.demo.service;

import com.food.demo.exception.InformationExistException;
import com.food.demo.exception.InformationNotFoundException;
import com.food.demo.model.Category;
import com.food.demo.model.User;
import com.food.demo.repository.CategoryRepository;
import com.food.demo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }

    public Category createCategory( Category categoryObject) {
//        return "creating a category " + body;
        System.out.println("calling createCategory ==>");
        Category category = categoryRepository.findByUserIdAndName(
                getCurrentLoggedInUser().getId(), categoryObject.getName());
        if (category != null) {
            throw new InformationExistException("category with name " + category.getName() + " already exists");
        } else {
            categoryObject.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(categoryObject);
        }
    }

    public List<Category> getCategories() {
        Long userId = getCurrentLoggedInUser().getId();
        List<Category> category = categoryRepository.findByUserId(userId);
        if (category.isEmpty()) {
            throw new InformationNotFoundException("no categories found for user id " + getCurrentLoggedInUser().getId());
        }
            return category;
//        return categoryRepository.findAll();


    }

    public Category getCategory(Long categoryId) {
        System.out.println("service getCategory ==>");
        Category category = categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId());
        //orElse
        if (category != null) {
            return category;
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }
    //                    category.orElse(newCategory);
//                    categoryRepository.save(newCategory)

    public Category updateCategory (Long categoryId , Category newCategory ){
        Category category = categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId());
        if (category != null) {
            Category updatedCategory  = category;
            updatedCategory.setName(newCategory.getName());
            updatedCategory.setDescription(newCategory.getDescription());
            return categoryRepository.save(updatedCategory);
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }

    public String deleteCategory (Long categoryId ){
        Category category = categoryRepository.findByIdAndUserId(categoryId, getCurrentLoggedInUser().getId());
        if (category != null) {
            categoryRepository.deleteById(categoryId);
            return "deleted";
        }else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }







}
