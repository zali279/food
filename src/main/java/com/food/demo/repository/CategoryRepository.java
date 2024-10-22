package com.food.demo.repository;

import org.springframework.stereotype.Repository;
import com.food.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  CategoryRepository  extends JpaRepository<Category, Long>{
    Category findByName(String categoryName);

    Category findByIdAndUserId(Long categoryId, Long userId);

    List<Category> findByUserId(Long userId);

    Category findByUserIdAndName(Long UserId, String categoryName);
}
