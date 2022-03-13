package turkcell.rentacar.business.abstracts;

import turkcell.rentacar.business.requests.create.CreateCorporateCustomerRequest;
import turkcell.rentacar.business.requests.update.UpdateCorporateCustomerRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.Result;

public interface CorporateCustomerService {
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;	
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;	
	boolean checkCorporateCustomerExists(String corporateCustomerName) throws BusinessException;
}
