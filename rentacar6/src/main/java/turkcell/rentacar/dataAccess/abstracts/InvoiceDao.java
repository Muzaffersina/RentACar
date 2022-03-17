package turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.Invoice;

@Repository
public interface InvoiceDao  extends JpaRepository<Invoice,Integer>{
	
	List<Invoice> getByCustomer_CustomerId(int customerId);
	//query olmadi
	
}
