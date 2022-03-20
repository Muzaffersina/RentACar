package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.GetListCarMaintenanceDto;
import turkcell.rentacar.business.dtos.ListCarMaintenanceDto;
import turkcell.rentacar.business.requests.create.CreateCarMaintenanceRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarMaintenanceRequest;
import turkcell.rentacar.business.requests.update.UpdateCarMaintenanceRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface CarMaintenanceService {	
	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);
	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest);
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);	
	
	DataResult<List<ListCarMaintenanceDto>> getAll();	
	DataResult<List<ListCarMaintenanceDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListCarMaintenanceDto>> getAllSorted(Sort.Direction direction);
	
	DataResult<GetListCarMaintenanceDto> getByMaintenanceId(int carMaintenanceId);
	List<ListCarMaintenanceDto> getByCar_CarId(int carId);
	
	boolean checkCarMaintenanceExists(int carMaintenanceId);	
	boolean checkCarMaintenceReturnDate(int maintenanceId);
	boolean checkCarMaintenanceCar_CarIdExists(int carId);
	boolean checkCarMaintenceCar_CarIdReturnDate(int carId);
	
	boolean checkCarUsed(int carId);
	
	
}
