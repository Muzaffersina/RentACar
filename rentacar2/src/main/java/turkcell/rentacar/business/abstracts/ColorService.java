package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListColorDto;
import turkcell.rentacar.business.requests.create.CreateColorRequest;
import turkcell.rentacar.business.requests.delete.DeleteColorRequest;
import turkcell.rentacar.business.requests.update.UpdateColorRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface ColorService {		
		Result add(CreateColorRequest createColorRequest) throws BusinessException;
		Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException;
		Result update(UpdateColorRequest updateColorRequest) throws BusinessException;
		DataResult<List<ListColorDto>> getAll();
		DataResult<ListColorDto> getByColorId(int colorId) throws BusinessException;

}
