package abn.adzor.recipeapp.domain;

import com.sun.istack.NotNull;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
@Embeddable
public class RecipeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recipeId;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;

    @NotNull
    private int servings;

    @NotNull
    private String instructions;

    @NotNull
    private String ingredients;
}
