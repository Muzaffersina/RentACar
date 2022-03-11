package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.GetListCarMaintenanceDto;
import turkcell.rentacar.business.dtos.ListCarMaintenanceDto;
import turkcell.rentacar.business.requests.create.CreateCarMaintenanceRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarMaintenanceRequest;
import turkcell.rentacar.business.requests.update.UpdateCarMaintenanceRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface CarMaintenanceService {	
	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException;
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;	
	
	DataResult<List<ListCarMaintenanceDto>> getAll();	
	DataResult<List<ListCarMaintenanceDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListCarMaintenanceDto>> getAllSorted(Sort.Direction direction);
	
	DataResult<GetListCarMaintenanceDto> getByMaintenanceId(int carMaintenanceId) throws BusinessException;
	List<ListCarMaintenanceDto> getByCar_CarId(int carId) throws BusinessException;
	
	boolean checkCarMaintenanceId(int carMaintenanceId) throws BusinessException;
	boolean checkCarMaintenceReturnDate(int carId) throws BusinessException;
	
	
}
