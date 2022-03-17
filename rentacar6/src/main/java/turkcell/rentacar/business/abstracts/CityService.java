package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.ListCityDto;
import turkcell.rentacar.business.requests.create.CreateCityRequest;
import turkcell.rentacar.business.requests.delete.DeleteCityRequest;
import turkcell.rentacar.business.requests.update.UpdateCityRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface CityService {
	
	Result add(CreateCityRequest createCityRequest);
	Result delete(DeleteCityRequest deleteCityRequest);
	Result update(UpdateCityRequest updateCityRequest);
	
	DataResult<List<ListCityDto>> getAll();
	DataResult<List<ListCityDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListCityDto>> getAllSorted(Sort.Direction direction);	
	DataResult<ListCityDto> getById(int cityId);
	
	boolean checkCityExists(int cityId);
	boolean checkCityNameExists(String cityName);
		
}
