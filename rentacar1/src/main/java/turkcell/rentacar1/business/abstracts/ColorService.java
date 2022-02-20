package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.ListColorDto;
import turkcell.rentacar1.business.requests.create.CreateColorRequest;
import turkcell.rentacar1.business.requests.delete.DeleteColorRequest;
import turkcell.rentacar1.business.requests.update.UpdateColorRequest;
import turkcell.rentacar1.core.concretes.BusinessException;

public interface ColorService {
	
		List<ListColorDto> getAll();
		void add(CreateColorRequest createColorRequest) throws BusinessException;
		void delete(DeleteColorRequest deleteColorRequest) throws BusinessException;
		void update(UpdateColorRequest updateColorRequest) throws BusinessException;
		ListColorDto getByColorId(int colorId) throws BusinessException;

}
