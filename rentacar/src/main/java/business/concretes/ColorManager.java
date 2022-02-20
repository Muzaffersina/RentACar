package business.concretes;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.abstracts.ColorService;
import business.dtos.ColorDto;
import business.dtos.ListColorDto;
import business.requests.CreateColorRequest;
import core.utilitys.mapping.ModelMapperService;
import dataAccess.abstracts.ColorDao;
import entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao) {
		this.colorDao = colorDao;
	}

	@Override
	public List<ListColorDto> getAll() {
		var result = this.colorDao.findAll();
		List<ListColorDto> response = result.stream()
				.map(product -> this.modelMapperService.forDto().map(product, ListColorDto.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public void add(CreateColorRequest createColorRequest) throws Exception {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		if (checkName(color)) {			
			this.colorDao.save(color);
		}

	}

	@Override
	public ColorDto getById(int colorId) throws Exception {
		var result = this.colorDao.getByColorId(colorId);
		if ( result == null) {
			throw new Exception("Id boş.");
		}
		ColorDto response = this.modelMapperService.forRequest().map(result, ColorDto.class);
		return response;
	}

	private boolean checkName(Color color) throws Exception {
		var result = this.colorDao.getByColorName(color.getName());
		if (result == null) {
			return true;
		}
		throw new Exception("Marka mevcut.");
	}

}

/*
 * 
 */
