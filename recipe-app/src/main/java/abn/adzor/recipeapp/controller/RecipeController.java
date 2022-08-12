package abn.adzor.recipeapp.controller;

import abn.adzor.recipeapp.domain.RecipeEntity;
import abn.adzor.recipeapp.domain.Response;
import abn.adzor.recipeapp.exception.RecipeException;
import abn.adzor.recipeapp.service.RecipeService;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "recipe")
public class RecipeController {

    private static final Logger LOGGER = LogManager.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    /**
     * Produces greetings phrase on the landing page.
     *
     */
    @GetMapping(value = "landing", produces = "application/json")
    public ResponseEntity<String> landing() {
        LOGGER.info("Returning landing message");
        return new ResponseEntity<>("Welcome to the ABN recipe app", HttpStatus.OK);
    }

    /**
     * Creates a new recipe to the database, created by the user.
     *
     * @param recipe RecipeEntity
     */
    @PostMapping(value = "add-recipe", produces = "application/json")
    public ResponseEntity<Response> addRecipe(RecipeEntity recipe) {
        LOGGER.info("In RecipeController - Adding recipe");
        try {
            recipeService.addRecipe(recipe);
        } catch (RecipeException e) {
            LOGGER.error("In RecipeController - Error while adding recipe: " + e.getMessage());
        }
        return new ResponseEntity<>(new Response("Recipe has been added"), HttpStatus.CREATED);
    }

    /**
     * Reads recipes from the database based on selected options determined by the user.
     * If no selected options are provided, will return all stored recipes.
     *
     * @param recipeFilter RecipeEntity
     */
    @GetMapping(value = "retrive-recipe", produces = "application/json")
    public ResponseEntity<?> getRecipe(@RequestBody(required = false) RecipeEntity recipeFilter) {
        LOGGER.info("In RecipeController - Get recipe");
        try {
            List<RecipeEntity> recipeEntityList;
            if (recipeFilter != null) {
                recipeEntityList = recipeService.getRecipes(recipeFilter);
            } else {
                recipeEntityList = recipeService.getRecipes();
            }
            LOGGER.info("In RecipeController - Recipe/s Retrieved");
            return new ResponseEntity<>(recipeEntityList, HttpStatus.OK);
        } catch (RecipeException e) {
            LOGGER.error("In RecipeController - Error retrieving recipe list");
            return new ResponseEntity<>(new Response("Error retrieving recipe list"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates the selected recipe by the user from the database.
     *
     * @param updateRecipe Map<String, RecipeEntity>
     */
    @RequestMapping(value = "update-recipe",method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateRecipe(Map<String, RecipeEntity> updateRecipe) {
        LOGGER.info("In RecipeController - Get recipe");
        if (updateRecipe != null) {
            try {
                recipeService.updateRecipe(updateRecipe);
            } catch (Exception e) {
                LOGGER.error("In RecipeController - Error retrieving recipe list");
                return new ResponseEntity<>(new Response("Error retrieving recipe list"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new Response("No recipe selected to be updated"), HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("In RecipeController - Recipe updated.");
        return new ResponseEntity<>(new Response("Recipe updated."), HttpStatus.OK);
    }

    /**
     * Deletes recipes from the database based selected by the user.
     *
     * @param recipe RecipeEntity
     */
    @DeleteMapping(value = "delete", produces = "application/json")
    public ResponseEntity<Response> deleteRecipe(RecipeEntity recipe) {
        LOGGER.info("In RecipeController - Delete recipe");
        try {
            recipeService.deleteRecipe(recipe);
        } catch (RecipeException e) {
            LOGGER.error("In RecipeController - Error while deleting recipe");
            return new ResponseEntity<>(new Response("Error while deleting recipe"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Response("Recipe deleted."), HttpStatus.OK);
    }

}
