package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.ListCarDto;
import turkcell.rentacar.business.requests.create.CreateCarRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarRequest;
import turkcell.rentacar.business.requests.update.UpdateCarRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface CarService {
	
	Result add(CreateCarRequest createCarRequest) throws BusinessException;
	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;
	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;

	DataResult<List<ListCarDto>> getAll();
	DataResult<ListCarDto> getByCarId(int carId) throws BusinessException;
	DataResult<List<ListCarDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction);
	DataResult<List<ListCarDto>> getByDailyPriceLessThanEqual(double dailyPrice);
	
	boolean checkCarExists(int carId) throws BusinessException;
	double calculateRentalPrice(int carId) throws BusinessException;
	
	
	boolean IsColorUsed(int colorId) throws BusinessException;
	boolean IsBrandUsed(int brandId) throws BusinessException;
}
