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
    // Receives a recipe as a JSON object and returns a JSON object with one id field.
    // This is a uniquely generated number by which we can identify and retrieve a recipe later.
    // The service should accept only valid recipes – all fields are obligatory, name and description shouldn't be blank, and JSON arrays should contain at least one item.
    // If a recipe doesn't meet these requirements, the service should respond with the 400 (Bad Request) status code.
    @PostMapping("/api/recipe/new")
    public Map<String, Long> addRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails details) {
        String userEmail = details.getUsername();
        User currentUser = userService.findByEmailIgnoreCase(userEmail);
        recipe.setUser(currentUser);
        return Map.of("id", recipeService.add(recipe));
    }
    // Receives a recipe as a JSON object and updates a recipe with a specified id. Also, update the date field too. The server should return the 204 (No Content) status code.
    // If a recipe with a specified id does not exist, the server should return 404 (Not found).
    // The server should respond with 400 (Bad Request)  if a recipe doesn't follow the restrictions indicated above.
    // Only an author of a recipe can update a recipe. If a user is not the author of a recipe the service should respond with the 403 (Forbidden) status code.
    @PutMapping("/api/recipe/{id}")
    public ResponseEntity updateRecipe(@Valid @RequestBody Recipe newRecipe, @PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        String userEmail = details.getUsername();
        User currentUser = userService.findByEmailIgnoreCase(userEmail);
        Optional<Recipe> optional = recipeService.findById(id);

        if(optional.isPresent()) {
            if(currentUser.equals(optional.get().getUser())){
                recipeService.updateById(id, newRecipe);
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.status(404).build();
    }
    // Returns a recipe with a specified id as a JSON object. The server should respond with the 200 (Ok) status code.
    // If a recipe with a specified id does not exist, the server should respond with 404 (Not found).
    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable @Min(1) long id) {
        return ResponseEntity.of(recipeService.findById(id));
    }
    // Deletes a recipe with a specified {id}. The server should respond with the 204 (No Content) status code.
    // If a recipe with a specified id does not exist, the server should return 404 (Not found)
    // Only an author of a recipe can delete a recipe. If a user is not the author of a recipe the service should respond with the 403 (Forbidden) status code.
    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity deleteRecipe(@PathVariable @Min(1) Long id, @AuthenticationPrincipal UserDetails details) {
        String userEmail = details.getUsername();
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
    // category – if this parameter is specified, it returns a JSON array of all recipes of the specified category.
    // name – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter.
    // Search is case-insensitive, sort the recipes by date (newer first).
    // If no recipes are found, the program should return an empty JSON array. If 0 parameters were passed, or more than 1, the server should return 400 (Bad Request).
    // The same response should follow if the specified parameters are not valid. If everything is correct, it should return 200 (Ok).
	@GetMapping("/api/recipe/search")
	public ResponseEntity<List<Recipe>> getRecipes(@RequestParam(required = false) String category, @RequestParam(required = false) String name){
		if ((name != null && category != null) || (name == null && category == null)){
            return ResponseEntity.status(400).build();
        }
        List<Recipe> recipes = name != null ? recipeService.findByName(name) : recipeService.findByCategory(category);
        return ResponseEntity.status(200).body(recipes);
	}
}
