package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.ListCarDto;
import turkcell.rentacar.business.requests.create.CreateCarRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarRequest;
import turkcell.rentacar.business.requests.update.UpdateCarRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface CarService {
	
	Result add(CreateCarRequest createCarRequest);
	Result delete(DeleteCarRequest deleteCarRequest);
	Result update(UpdateCarRequest updateCarRequest);

	DataResult<List<ListCarDto>> getAll();
	DataResult<ListCarDto> getByCarId(int carId);
	DataResult<List<ListCarDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction);
	DataResult<List<ListCarDto>> getByDailyPriceLessThanEqual(double dailyPrice);
	
	boolean checkCarExists(int carId);
	
	double calculateRentalPrice(int carId);
	double checkDailyKm(int carId);	
	
	boolean isColorUsed(int colorId);
	boolean isBrandUsed(int brandId);
	
	void updateCarKm(int carId , int lastKm);
}
