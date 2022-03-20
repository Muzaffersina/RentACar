package turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.Additional;

@Repository
public interface AdditionalDao extends JpaRepository<Additional, Integer>{
	
	Additional getByAdditionalName(String additionalName);
	
}
