package recipes.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.model.Recipe;
import java.util.List;
/**
 * Interface is used to interact with the recipe table in the database.
 */
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
}
