package turkcell.rentacar.business.abstracts;


import java.util.List;

import turkcell.rentacar.business.dtos.ListCarDamageDto;
import turkcell.rentacar.business.requests.create.CreateCarDamageRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarDamageRequest;
import turkcell.rentacar.business.requests.update.UpdateCarDamageRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface CarDamageService {
	
	Result add(CreateCarDamageRequest createCarDamageRequest);
	Result delete(DeleteCarDamageRequest deleteCarDamageRequest);
	Result update(UpdateCarDamageRequest updateCarDamageRequest);
	
	DataResult<List<ListCarDamageDto>> getAll();
	DataResult<List<ListCarDamageDto>> getAllPaged(int pageNo , int pageSize);	
	DataResult<ListCarDamageDto> getByCarDamageId(int carDamageId);
	DataResult<List<ListCarDamageDto>> getAllByCarId(int carId);
	
	// getByCarId ye gore yapılabilir ******
	boolean checkCarDamageExists(int carDamageId);

}
