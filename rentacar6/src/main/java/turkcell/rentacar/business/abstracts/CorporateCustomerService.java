package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListCorporateCustomerDto;
import turkcell.rentacar.business.requests.create.CreateCorporateCustomerRequest;
import turkcell.rentacar.business.requests.update.UpdateCorporateCustomerRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface CorporateCustomerService {
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);
	
	
	DataResult<List<ListCorporateCustomerDto>> getAll();
	DataResult<ListCorporateCustomerDto> getById(int id);
	
	boolean checkCorporateCustomerExists(int id);
	
}
