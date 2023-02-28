package recipes.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.persistence.RecipeRepository;
import recipes.service.RecipeService;
import recipes.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipeController {
	final RecipeService recipeService;
    final UserService userService;
    final RecipeRepository recipeRepository;
	
    public RecipeController(RecipeService recipeService, UserService userService, RecipeRepository recipeRepository) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.recipeRepository = recipeRepository;
    }
    /**
     * Add a new recipe.
     *
     * @param userDetails The user's authentication parameters that is creating the recipe
     * @param recipe      The recipe to be added
     * @return A Map with the id and the recipe that was added
     */
    @PostMapping("/api/recipe/new")
    public Map<String, Long> addRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        User currentUser = userService.findByEmailIgnoreCase(userEmail);
        recipe.setUser(currentUser);
        return Map.of("id", recipeService.add(recipe));
    }
    /**
     * Update a specific recipe.
     *
     * @param userDetails The user's authentication parameters that is updating the recipe
     * @param id          The id of the recipe to be updated
     * @param recipe      The recipe to be updated
     * @return A response entity with the status of the update
     */
    @PutMapping("/api/recipe/{id}")
    public ResponseEntity updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        User currentUser = userService.findByEmailIgnoreCase(userEmail);
        Optional<Recipe> optional = recipeService.findById(id);

        if(optional.isPresent()) {
            if(currentUser.equals(optional.get().getUser())){
                recipeService.updateById(id, recipe);
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.status(404).build();
    }
    /**
     * Get a specific recipe.
     *
     * @param id The id of the recipe to be retrieved
     * @return A response entity with retrieved recipe
     */
    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable @Min(1) long id) {
        return ResponseEntity.of(recipeService.findById(id));
    }
    /**
     * Delete a specific recipe.
     *
     * @param userDetails The user's authentication parameters that is deleting the recipe
     * @param id          The id of the recipe to be deleted
     * @return A response entity with the status of the deletion
     */
    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity deleteRecipe(@PathVariable @Min(1) Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        User currentUser = userService.findByEmailIgnoreCase(userEmail);
        Optional<Recipe> optional = recipeService.findById(id);

        if(optional.isPresent()) {
            if(currentUser.equals(optional.get().getUser())){
                recipeService.deleteById(id);
                return ResponseEntity.status(204).build(); // no content
            }
            return ResponseEntity.status(403).build(); // forbidden
        }
        return ResponseEntity.status(404).build(); // bad request
    }
    /**
     * Get all recipes queried by category or name.
     *
     * @param category The category of the recipe(s) to be retrieved
     * @param name     The name of the recipe to be retrieved
     * @return A response entity with the retrieved recipe(s)
     */
	@GetMapping("/api/recipe/search")
	public ResponseEntity<List<Recipe>> getRecipes(@RequestParam(required = false) String category, @RequestParam(required = false) String name){
		if ((name != null && category != null) || (name == null && category == null)){
            return ResponseEntity.status(400).build();
        }
        List<Recipe> recipes = name != null ? recipeService.findByName(name) : recipeService.findByCategory(category);
        return ResponseEntity.status(200).body(recipes);
	}
}
