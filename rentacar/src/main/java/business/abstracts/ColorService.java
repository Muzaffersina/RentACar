package business.abstracts;

import java.util.List;

import business.dtos.ColorDto;
import business.dtos.ListColorDto;
import business.requests.CreateColorRequest;


public interface ColorService {
	
	List<ListColorDto>	getAll();	
	void add(CreateColorRequest createColorRequest) throws Exception;
	ColorDto getById(int colorId) throws Exception ;
}
