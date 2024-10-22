package com.food.demo.repository;

import com.food.demo.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{
    Recipe findByName(String recipeName);

    List<Recipe> findByCategoryId(Long recipeId);

    List<Recipe> findTop5ByOrderByPortionsDesc();
    List<Recipe> findAllByOrderByName();
    List<Recipe> findAllByIngredientsNotContaining(String ingredient);
    List<Recipe> findAllByIngredientsContaining(String ingredient);
    Recipe findByNameAndUserIdAndIdIsNot(String recipeName, Long userId, Long recipeId);
    Recipe findByNameAndUserId(String recipeName, Long userId);


}
