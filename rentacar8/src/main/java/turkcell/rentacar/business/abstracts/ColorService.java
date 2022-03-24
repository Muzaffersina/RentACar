package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.ListColorDto;
import turkcell.rentacar.business.requests.create.CreateColorRequest;
import turkcell.rentacar.business.requests.delete.DeleteColorRequest;
import turkcell.rentacar.business.requests.update.UpdateColorRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface ColorService {		
		Result add(CreateColorRequest createColorRequest);
		Result delete(DeleteColorRequest deleteColorRequest);
		Result update(UpdateColorRequest updateColorRequest);
		
		DataResult<List<ListColorDto>> getAll();
		DataResult<List<ListColorDto>> getAllPaged(int pageNo , int pageSize);
		DataResult<List<ListColorDto>> getAllSorted(Sort.Direction direction);	
		
		DataResult<ListColorDto> getByColorId(int colorId);
		
		boolean checkColorNameExists(String colorName);
		boolean checkColorExists(int colorId);
		
}
