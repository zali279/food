package com.food.demo.service;

import com.food.demo.exception.InformationExistException;
import com.food.demo.exception.InformationNotFoundException;
import com.food.demo.model.Category;
import com.food.demo.model.Recipe;
import com.food.demo.repository.CategoryRepository;
import com.food.demo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RecipeService {
    public RecipeRepository recipeRepository;
    public CategoryRepository categoryRepository;
    @Autowired
    public void setRecipeRepository(RecipeRepository recipeRepository){
        this.recipeRepository=recipeRepository;
    }
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }



    public Recipe createRecipe(Long categoryId , Recipe recipeObject ){
        Category category = categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId());
        if (category == null) {
            throw new InformationNotFoundException(
                    "category with id " + categoryId + " not belongs to this user or category does not exist");
        }
        Recipe recipe = recipeRepository.findByNameAndUserId(recipeObject.getName(), CategoryService.getCurrentLoggedInUser().getId());
        if (recipe != null) {
            throw new InformationExistException("recipe with name " + recipe.getName() + " already exists");
        }
        recipeObject.setUser(CategoryService.getCurrentLoggedInUser());
        recipeObject.setCategory(category);
        return recipeRepository.save(recipeObject);
    }

    public List<Recipe> getRecipes(Long categoryId){
        Category category = categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId + " " +
                    "not belongs to this user or category does not exist");
        }
        return category.getRecipeList();
    }

    public Recipe getRecipe(Long categoryId, Long recipeId){
        Category category = categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId +
                    " not belongs to this user or category does not exist");
        }
        Optional<Recipe> recipe = recipeRepository.findByCategoryId(
                categoryId).stream().filter(p -> p.getId().equals(recipeId)).findFirst();
        if (recipe.isEmpty()) {
            throw new InformationNotFoundException("recipe with id " + recipeId +
                    " not belongs to this user or recipe does not exist");
        }
        return recipe.get();
    }

    public Recipe updateRecipe(Long categoryId, Long recipeId,Recipe recipeObject){
        Category category = categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId +
                    " not belongs to this user or category does not exist");
        }
        Optional<Recipe> recipe = recipeRepository.findByCategoryId(
                categoryId).stream().filter(p -> p.getId().equals(recipeId)).findFirst();
        if (recipe.isEmpty()) {
            throw new InformationNotFoundException("recipe with id " + recipeId +
                    " not belongs to this user or recipe does not exist");
        }
        Recipe oldRecipe = recipeRepository.findByNameAndUserIdAndIdIsNot(
                recipeObject.getName(), CategoryService.getCurrentLoggedInUser().getId(), recipeId);
        if (oldRecipe != null) {
            throw new InformationExistException("recipe with name " + oldRecipe.getName() + " already exists");
        }
        recipe.get().setName(recipeObject.getName());
        recipe.get().setIngredients(recipeObject.getIngredients());
        recipe.get().setSteps(recipeObject.getSteps());
        recipe.get().setTime(recipeObject.getTime());
        recipe.get().setPortions(recipeObject.getPortions());
        return recipeRepository.save(recipe.get());
    }

    public String deleteRecipe(Long categoryId, Long recipeId){
        Category category = categoryRepository.findByIdAndUserId(categoryId, CategoryService.getCurrentLoggedInUser().getId());
        if (category == null) {
            throw new InformationNotFoundException("category with id " + categoryId +
                    " not belongs to this user or category does not exist");
        }
        Optional<Recipe> recipe = recipeRepository.findByCategoryId(
                categoryId).stream().filter(p -> p.getId().equals(recipeId)).findFirst();
        if (recipe.isEmpty()) {
            throw new InformationNotFoundException("recipe with id " + recipeId +
                    " not belongs to this user or recipe does not exist");
        }
        recipeRepository.deleteById(recipe.get().getId());
      return "deleted";
    }

    public  List<Recipe> top5Portions (){
        return recipeRepository.findTop5ByOrderByPortionsDesc();
    }

    public  List<Recipe> orderRecipe(){
        return recipeRepository.findAllByOrderByName();
    }

    public List<Recipe> notIncludeIngredient(String ingredient) {
        return recipeRepository.findAllByIngredientsNotContaining(ingredient);
    }

    public List<Recipe> includeIngredient(String ingredient) {
        return recipeRepository.findAllByIngredientsContaining(ingredient);
    }


    }
