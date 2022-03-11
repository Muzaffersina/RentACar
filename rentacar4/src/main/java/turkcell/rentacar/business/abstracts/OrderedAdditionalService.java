package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListOrderedAdditionalDto;
import turkcell.rentacar.core.utilities.results.DataResult;

public interface OrderedAdditionalService {
	
	DataResult<List<ListOrderedAdditionalDto>> getAll();

}
