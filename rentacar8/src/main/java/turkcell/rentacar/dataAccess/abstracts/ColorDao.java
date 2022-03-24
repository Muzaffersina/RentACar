package turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.Color;
@Repository
public interface ColorDao extends JpaRepository<Color, Integer>{
	
	Color getByColorId(int colorId);
	Color getByColorName(String colorName);
}
