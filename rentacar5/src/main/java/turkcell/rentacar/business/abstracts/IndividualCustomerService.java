package turkcell.rentacar.business.abstracts;

import turkcell.rentacar.business.requests.create.CreateIndividualCustomerRequest;
import turkcell.rentacar.business.requests.update.UpdateColorRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.Result;

public interface IndividualCustomerService {
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;	
	Result update(UpdateColorRequest updateIndividualCustomerRequest) throws BusinessException;	
	boolean checkIndividualCustomerExists(String individualCustomerName) throws BusinessException;	
}
