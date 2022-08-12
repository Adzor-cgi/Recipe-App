package abn.adzor.recipeapp.service;

import abn.adzor.recipeapp.domain.RecipeEntity;
import abn.adzor.recipeapp.domain.RecipeType;
import abn.adzor.recipeapp.exception.RecipeException;
import abn.adzor.recipeapp.repository.RecipeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    RecipeService recipeService;

    public static final String RECIPE_NAME = "Fish stew";
    public static final RecipeType RECIPE_TYPE = RecipeType.NON_VEGETARIAN;
    public static final int RECIPE_SERVINGS = 4;
    public static final String RECIPE_INSTRUCTIONS = "Cook 500 grams of white Fish in a pot with all the ingredients for 20 minutes";
    public static final String RECIPE_INGREDIENTS = "Fish, Salt, Pepper, Ginger, Broth";

    @BeforeEach
    void setUp() {

    }

    @Test
    void addRecipeTest() throws RecipeException {
        recipeService.addRecipe(createRecipe(RECIPE_NAME, RECIPE_TYPE, RECIPE_SERVINGS, RECIPE_INSTRUCTIONS, RECIPE_INGREDIENTS));

        List<RecipeEntity> recipe = recipeService.getRecipes();

        assertThat(recipe, hasItem(allOf(
                hasProperty("name", is(RECIPE_NAME)),
                hasProperty("recipeType", is(RECIPE_TYPE)),
                hasProperty("servings", is(RECIPE_SERVINGS)),
                hasProperty("instructions", is(RECIPE_INSTRUCTIONS)),
                hasProperty("ingredients", is(RECIPE_INGREDIENTS))
        )));
    }

    @Test
    void getRecipeWithFilterTest()throws RecipeException {
        recipeService.addRecipe(createRecipe(RECIPE_NAME, RECIPE_TYPE, RECIPE_SERVINGS, RECIPE_INSTRUCTIONS, RECIPE_INGREDIENTS));

        RecipeEntity recipeFilter = createRecipe(RECIPE_NAME);

        List<RecipeEntity> recipe = recipeService.getRecipes(recipeFilter);

        assertThat(recipe, hasItem(allOf(
                hasProperty("name", is(RECIPE_NAME)),
                hasProperty("recipeType", is(RECIPE_TYPE)),
                hasProperty("servings", is(RECIPE_SERVINGS)),
                hasProperty("instructions", is(RECIPE_INSTRUCTIONS)),
                hasProperty("ingredients", is(RECIPE_INGREDIENTS))
        )));
    }


    public RecipeEntity createRecipe(String name, RecipeType type, int servings, String instructions, String ingredients) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setName(name);
        recipeEntity.setRecipeType(type);
        recipeEntity.setServings(servings);
        recipeEntity.setInstructions(instructions);
        recipeEntity.setIngredients(ingredients);
        return recipeEntity;
    }

    public RecipeEntity createRecipe(String name) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setName(name);
        return recipeEntity;
    }

}
