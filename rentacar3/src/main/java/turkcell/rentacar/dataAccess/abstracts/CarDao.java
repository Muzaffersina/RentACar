package turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.Car;
@Repository
public interface CarDao extends JpaRepository<Car, Integer>{
	
	Car getByCarId(int carId);
	List<Car> getByDailyPriceLessThanEqual(double dailyPrice);

}
