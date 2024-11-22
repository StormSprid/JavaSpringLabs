package stormsprid.emilspring.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stormsprid.emilspring.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
