package turkcell.rentacar.business.abstracts;

import java.util.List;

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
	DataResult<ListAdditionalDto> getById(int additionalId) throws BusinessException;	
	
	boolean checkAdditionalId(int additionalId) throws BusinessException;
	

}
