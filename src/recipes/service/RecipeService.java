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
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }
    public long add(Recipe recipe) {
        return recipeRepository.save(recipe).getId();
    }
     public void deleteById(long id) {
        recipeRepository.deleteById(id);
    }
    public void updateById(long id, Recipe recipe) {
        Optional<Recipe> optional = recipeRepository.findById(id);
        if (optional.isPresent()) {
            Recipe oldRecipe = optional.get();
            oldRecipe.copyOf(recipe);
            recipeRepository.save(oldRecipe);
        }
    }
    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }
    public List<Recipe> findByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
