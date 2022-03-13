package turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.OrderedAdditional;

@Repository
public interface OrderedAdditionalDao extends JpaRepository<OrderedAdditional, Integer> {

}
