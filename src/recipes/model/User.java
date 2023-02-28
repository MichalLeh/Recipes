package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import recipes.model.Recipe;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a user. It is used for authentication and authorization.
 */
@Entity
@Table(name="USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    @NotBlank
    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @Column
    @NotBlank
    @Length(min=8)
    private String password;
    @JsonIgnore
    //One user can have various recipes
    @OneToMany(mappedBy = "user")
    private List<Recipe> recipesAuthored = new ArrayList<>();
}
