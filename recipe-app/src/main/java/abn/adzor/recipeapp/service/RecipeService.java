package abn.adzor.recipeapp.service;

import abn.adzor.recipeapp.domain.RecipeEntity;
import abn.adzor.recipeapp.exception.RecipeException;
import abn.adzor.recipeapp.repository.RecipeRepository;
import java.util.ArrayList;

import java.util.HashSet;

import java.util.List;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SuperBuilder(toBuilder = true)
public class RecipeService {

    private RecipeRepository recipeRepository;

    private static final Logger LOGGER = LogManager.getLogger(RecipeService.class);

    public void addRecipe(RecipeEntity recipe) throws RecipeException {
        LOGGER.info("In RecipeService - Adding recipe");
        try {
            recipeRepository.save(recipe);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("In RecipeService - Error while adding recipe");
            throw new RecipeException(e.getMessage());
        }
    }

    public List<RecipeEntity> getRecipes() throws RecipeException {
        LOGGER.info("In RecipeService - Getting all recipe");
        try {
            return recipeRepository.findAll();
        } catch (Exception e) {
            LOGGER.info("In RecipeService - Error while retrieving all recipes");
            e.printStackTrace();
            throw new RecipeException(e.getMessage());
        }
    }

    public List<RecipeEntity> getRecipes(RecipeEntity recipeFilter) throws RecipeException {
        LOGGER.info("In RecipeService - Getting recipe based on filter");
        try {
            return createNewRecipeList(recipeFilter);
        } catch (Exception e) {
            LOGGER.info("In RecipeService - Error while retrieving recipes based on filter");
            e.printStackTrace();
            throw new RecipeException(e.getMessage());
        }
    }

    public void updateRecipe(Map<String, RecipeEntity> updateRecipe) throws RecipeException {
        LOGGER.info("In RecipeService - Updating recipe");
        try {
            RecipeEntity existingRecipe = updateRecipe.get("existing");
            RecipeEntity newRecipe = updateRecipe.get("new");

            if (existingRecipe.equals(newRecipe)) {
                throw new RecipeException("Recipe already exists.");
            }
            recipeRepository.delete(existingRecipe);
            recipeRepository.save(newRecipe);
            LOGGER.info("Recipe updated!.");
        } catch (Exception e) {
            LOGGER.info("In RecipeService - Error while attempting to update recipe");
            e.printStackTrace();
            throw new RecipeException(e.getMessage());
        }
    }

    public void deleteRecipe(RecipeEntity recipe) throws RecipeException {
        LOGGER.info("In RecipeService - deleting recipe");
        try {
            recipeRepository.delete(recipe);
        } catch (Exception e) {
            LOGGER.info("In RecipeService - Error while deleting recipe");
            e.printStackTrace();
            throw new RecipeException(e.getMessage());
        }
    }

    private List<RecipeEntity> createNewRecipeList(RecipeEntity recipeFilter) {
        String mealName = recipeFilter.getName();
        String mealType = recipeFilter.getRecipeType().toString();
        int mealServings = recipeFilter.getServings();
        String mealInstructions = recipeFilter.getInstructions();
        String mealIngredients = recipeFilter.getIngredients();

        List<RecipeEntity> recipeEntityListIngredients = recipeRepository.findAll();
        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        for (RecipeEntity recipe : recipeEntityListIngredients) {
            if (recipe.getName().contains(mealName)) {
                recipeEntityList.add(recipe);
            }
            if (recipe.getRecipeType().toString().contains(mealType)) {
                recipeEntityList.add(recipe);
            }
            if (recipe.getServings() == mealServings) {
                recipeEntityList.add(recipe);
            }
            if (recipe.getInstructions().contains(mealInstructions)) {
                recipeEntityList.add(recipe);
            }
            if (recipe.getIngredients().contains(mealIngredients)) {
                recipeEntityList.add(recipe);
            }
        }

        return removeDuplicateRecipes(recipeEntityList);
    }

    private List<RecipeEntity> removeDuplicateRecipes(List<RecipeEntity> listWithDuplicates) {
        return new ArrayList<>(new HashSet<>(listWithDuplicates));
    }
}
