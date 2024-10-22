package com.food.demo.controller;

import com.food.demo.model.Recipe;
import com.food.demo.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {
    public RecipeService recipeService;

    @Autowired
    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/categories/{categoryId}/recipes")
    public Recipe createRecipe(@PathVariable Long categoryId, @RequestBody Recipe recipeObject) {
        return recipeService.createRecipe(categoryId, recipeObject);
    }

    @GetMapping("/categories/{categoryId}/recipes")
    public List<Recipe> getRecipes(@PathVariable Long categoryId) {
        return recipeService.getRecipes(categoryId);
    }

    @GetMapping("/categories/{categoryId}/recipes/{recipeId}")
    public Recipe getRecipe(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "recipeId") Long recipeId) {
        return recipeService.getRecipe(categoryId, recipeId);
    }

    @PutMapping("/categories/{categoryId}/recipes/{recipeId}")
    public Recipe updateRecipe(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "recipeId") Long recipeId, @RequestBody Recipe recipeObject) {

        return recipeService.updateRecipe(categoryId, recipeId, recipeObject);
    }
    @DeleteMapping("/categories/{categoryId}/recipes/{recipeId}")
    public String deleteRecipe(@PathVariable(value = "categoryId") Long categoryId, @PathVariable(value = "recipeId") Long recipeId) {
          return recipeService.deleteRecipe(categoryId,recipeId);
    }

    @GetMapping("/recipes/portions")
    public List<Recipe> top5Portions (){
        return recipeService.top5Portions();
    }

    @GetMapping("/recipes/order")
    public List<Recipe> orderRecipe(){
        return recipeService.orderRecipe();
    }

    @GetMapping("/recipes/notingredient/{ingredient}")
    public List<Recipe> notIncludeIngredient(@PathVariable String ingredient){
        return recipeService.notIncludeIngredient(ingredient);
    }

    @GetMapping("/recipes/ingredient/{ingredient}")
    public List<Recipe> includeIngredient(@PathVariable String ingredient){
        return recipeService.includeIngredient(ingredient);
    }
    }