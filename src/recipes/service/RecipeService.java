package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.persistence.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    /**
     * Find a recipe
     *
     * @param id Find a recipe by id
     * @return Recipe if exists otherwise null
     */
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }
    /**
     * Add a new recipe
     *
     * @param recipe Object containing the recipe information
     * @return The ID of the newly added recipe
     */
    public long add(Recipe recipe) {
        return recipeRepository.save(recipe).getId();
    }
    /**
     * Delete the recipe
     *
     * @param id The ID of the recipe to delete
     */
     public void deleteById(long id) {
        recipeRepository.deleteById(id);
    }
    /**
     * Update the recipe
     *
     * @param id The ID of the recipe to update
     * @param recipe The recipe to update
     */
    public void updateById(long id, Recipe recipe) {
        Optional<Recipe> optional = recipeRepository.findById(id);
        if (optional.isPresent()) {
            Recipe oldRecipe = optional.get();
            oldRecipe.copyOf(recipe);
            recipeRepository.save(oldRecipe);
        }
    }
    /**
     * Find recipe(s)
     *
     * @param category The recipe(s) to be retrieved by a category
     * @return List containing the recipe(s)
     */
    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }
    /**
     * Find recipe(s)
     *
     * @param name The recipe(s) to be retrieved by a name
     * @return List containing the recipe(s)
     */
    public List<Recipe> findByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
