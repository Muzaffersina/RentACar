package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListOrderedAdditionalDto;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface OrderedAdditionalService {
	
	Result add(int additionalId , int rentalId) throws BusinessException;
	
	DataResult<List<ListOrderedAdditionalDto>> getAll();

}
