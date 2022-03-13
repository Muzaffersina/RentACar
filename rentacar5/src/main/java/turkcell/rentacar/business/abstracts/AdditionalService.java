package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.ListAdditionalDto;
import turkcell.rentacar.business.requests.create.CreateAdditionalRequest;
import turkcell.rentacar.business.requests.delete.DeleteAdditionalRequest;
import turkcell.rentacar.business.requests.update.UpdateAdditionalRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface AdditionalService {
	
	Result add(CreateAdditionalRequest createAdditionalRequest) throws BusinessException;
	Result delete(DeleteAdditionalRequest deleteAdditionalRequest)throws BusinessException;
	Result update(UpdateAdditionalRequest updateAdditionalRequest)throws BusinessException;
	
	DataResult<List<ListAdditionalDto>> getAll();
	DataResult<List<ListAdditionalDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListAdditionalDto>> getAllSorted(Sort.Direction direction);
	DataResult<ListAdditionalDto> getById(int additionalId) throws BusinessException;
	
	void checkAllAdditional(List<Integer> additionalIds)throws BusinessException;	
	boolean checkAdditionalExists(int additionalId) throws BusinessException;
	//double calculateAdditionalServicePrice(List<Integer> additionalServiceId);
	double calculateAdditionalServicePrice(int additionalServiceId);
	

}
