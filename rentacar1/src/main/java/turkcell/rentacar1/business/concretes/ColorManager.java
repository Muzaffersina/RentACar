package turkcell.rentacar1.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.ColorService;
import turkcell.rentacar1.business.dtos.ListColorDto;
import turkcell.rentacar1.business.requests.create.CreateColorRequest;
import turkcell.rentacar1.business.requests.delete.DeleteColorRequest;
import turkcell.rentacar1.business.requests.update.UpdateColorRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.dataAccess.abstracts.ColorDao;
import turkcell.rentacar1.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {

		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<ListColorDto> getAll() {
		var result = this.colorDao.findAll();
		List<ListColorDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public void add(CreateColorRequest createColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		checkIfColorName(color);
		this.colorDao.save(color);	

	}

	@Override
	public void delete(DeleteColorRequest deleteColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		checkColorId(color);
		this.colorDao.deleteById(color.getColorId());
	}

	@Override
	public void update(UpdateColorRequest updateColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		checkColorId(color);
		this.colorDao.save(color);
	}

	@Override
	public ListColorDto getByColorId(int colorId) throws BusinessException {
		var result = this.colorDao.getByColorId(colorId);
		if (result != null) {
			ListColorDto response = this.modelMapperService.forDto().map(result, ListColorDto.class);
			return response;
		}
		throw new BusinessException("Bu id boş.");
	}

	private boolean checkIfColorName(Color color) throws BusinessException {
		var result = this.colorDao.getByColorName(color.getColorName());
		if (result == null) {
			return true;
		}
		throw new BusinessException("Bu renk daha önce eklenmiştir.");
	}
	
	private boolean checkColorId(Color color) throws BusinessException{
		var result = this.colorDao.getByColorId(color.getColorId());
		if(result !=null) {
			return true;
		}
		throw new BusinessException("Color için geçersiz ıd");
	}

}
