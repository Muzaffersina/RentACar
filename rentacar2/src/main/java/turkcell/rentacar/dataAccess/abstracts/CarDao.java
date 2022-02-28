package turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import turkcell.rentacar.entities.concretes.Car;

public interface CarDao extends JpaRepository<Car, Integer>{
	
	Car getByCarId(int carId);
	List<Car> getByDailyPriceLessThanEqual(double dailyPrice);

}
