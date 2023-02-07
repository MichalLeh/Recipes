package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="RECIPES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Column(name = "Name")
    private String name;
    @NotBlank
    @Column(name = "Category")
    private String category;
    @Column(name = "Date")
    private LocalDateTime date = LocalDateTime.now();
    @NotBlank
    @Column(name = "Description")
    private String description;

    @NotNull
    @NotEmpty
    @ElementCollection
    @Column(name = "Ingredients")
    private List<String> ingredients;

    @NotNull
    @NotEmpty
    @ElementCollection
    @Column(name = "Directions")
    private List<String> directions;
    //One recipe can have various users
    @ManyToOne
    @JsonIgnore
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private User user;
    public void copyOf(Recipe recipe) {
        name = recipe.name;
        description = recipe.description;
        category = recipe.category;
        ingredients = recipe.ingredients;
        directions = recipe.directions;
        date = LocalDateTime.now();
    }
}
