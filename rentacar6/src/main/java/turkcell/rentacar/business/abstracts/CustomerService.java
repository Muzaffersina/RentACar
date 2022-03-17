package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListCustomerDto;
import turkcell.rentacar.business.requests.create.CreateCustomerRequest;
import turkcell.rentacar.business.requests.delete.DeleteCustomerRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface CustomerService {
	Result add(CreateCustomerRequest createCustomerRequest);
	Result delete(DeleteCustomerRequest deleteCustomerRequest);		
	DataResult<List<ListCustomerDto>> getAll();
	DataResult<ListCustomerDto> getById(int id);
	
	boolean checkCustomerExists(int id);
	
}
