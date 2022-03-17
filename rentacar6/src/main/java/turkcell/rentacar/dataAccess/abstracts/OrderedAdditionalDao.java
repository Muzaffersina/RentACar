package turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.OrderedAdditional;

@Repository
public interface OrderedAdditionalDao extends JpaRepository<OrderedAdditional, Integer> {
	List<OrderedAdditional> getByAdditional_AdditionalId(int additionalId);
}
