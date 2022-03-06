package turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.Rental;

@Repository
public interface RentalDao extends JpaRepository<Rental, Integer>{
	Rental getByRentId(int rentId);	
	Rental getRentalByCarCarIdAndReturnDateIsNull(int carId);
	List<Rental> getByCar_CarId(int carId);		
}
