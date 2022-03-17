package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListOrderedAdditionalDto;
import turkcell.rentacar.business.requests.create.CreateOrderedAdditionalRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface OrderedAdditionalService {
	
	Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest);
	
	DataResult<List<ListOrderedAdditionalDto>> getAll();
	List<ListOrderedAdditionalDto> getByAdditional_AdditionalId(int additionalId);

	void saveOrderedAdditional(List<Integer> additionalIds, int rentalId);

}
