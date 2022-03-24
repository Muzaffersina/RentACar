package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListCustomerDto;
import turkcell.rentacar.core.utilities.results.DataResult;

public interface CustomerService {	
	DataResult<List<ListCustomerDto>> getAll();
	DataResult<ListCustomerDto> getById(int id);
	
	boolean checkCustomerExists(int id);
	
}
