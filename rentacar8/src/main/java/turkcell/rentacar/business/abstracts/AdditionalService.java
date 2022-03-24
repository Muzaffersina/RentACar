package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.ListAdditionalDto;
import turkcell.rentacar.business.requests.create.CreateAdditionalRequest;
import turkcell.rentacar.business.requests.delete.DeleteAdditionalRequest;
import turkcell.rentacar.business.requests.update.UpdateAdditionalRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface AdditionalService {
	
	Result add(CreateAdditionalRequest createAdditionalRequest) ;
	Result delete(DeleteAdditionalRequest deleteAdditionalRequest);
	Result update(UpdateAdditionalRequest updateAdditionalRequest);
	
	DataResult<List<ListAdditionalDto>> getAll();
	DataResult<List<ListAdditionalDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListAdditionalDto>> getAllSorted(Sort.Direction direction);
	DataResult<ListAdditionalDto> getById(int additionalId);
	
	
	double calculateAdditionalServicePrice(List<Integer> additionalServiceId);
	void checkAllAdditional(List<Integer> additionalIds);
	boolean checkAdditionalExists(int additionalId);
	boolean checkAdditionalNameExists(String additionalName);
}
