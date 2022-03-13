package turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turkcell.rentacar.entities.concretes.CarMaintenance;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer> { //
	CarMaintenance getByMaintenanceId(int carMaintenanceId);							
	CarMaintenance getCarMaintenanceByCarCarIdAndReturnDateIsNull(int carId);		
	List<CarMaintenance> getByCar_CarId(int carId);	 									
}
