package abn.adzor.recipeapp.exception;

public class RecipeException extends Exception {

    public RecipeException() {}

    public RecipeException(String errorMessage) {
        super(errorMessage);
    }
}
