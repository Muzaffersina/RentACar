package turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.Customer;
@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer>{

}
