package abn.adzor.recipeapp.repository;

import abn.adzor.recipeapp.domain.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
}
