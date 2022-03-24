package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.CarService;
import turkcell.rentacar.business.abstracts.ColorService;
import turkcell.rentacar.business.contants.Messages;
import turkcell.rentacar.business.dtos.ListColorDto;
import turkcell.rentacar.business.requests.create.CreateColorRequest;
import turkcell.rentacar.business.requests.delete.DeleteColorRequest;
import turkcell.rentacar.business.requests.update.UpdateColorRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.ColorDao;
import turkcell.rentacar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;
	private CarService carService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService,CarService carService) {

		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}

	@Override
	public DataResult<List<ListColorDto>> getAll() {
		
		var result = this.colorDao.findAll();		
		List<ListColorDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(response,Messages.COLORLIST);
	}
	
	@Override
	public DataResult<List<ListColorDto>> getAllPaged(int pageNo, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		
		List<Color> result = this.colorDao.findAll(pageable).getContent();		
		List<ListColorDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, ListColorDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(response,Messages.COLORLIST);
	}

	@Override
	public DataResult<List<ListColorDto>> getAllSorted(Direction direction) {
		
		Sort sort = Sort.by(direction, "colorId");
		
		List<Color> result = this.colorDao.findAll(sort);		
		List<ListColorDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, ListColorDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(response,Messages.COLORLIST);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest)  {
		
		checkColorNameExists(createColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		color.setColorId(0);
		this.colorDao.save(color);	
		
		return new SuccessResult(Messages.COLORADD);
		
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest)  {
		
		checkColorExists(deleteColorRequest.getColorId());		
		this.carService.checkColorUsed(deleteColorRequest.getColorId());
		
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		this.colorDao.deleteById(color.getColorId());
		
		return new SuccessResult(Messages.COLORDELETE);			
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {
		
		checkColorExists(updateColorRequest.getColorId());
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		this.colorDao.save(color);
		
		return new SuccessResult(Messages.COLORUPDATE);		
	}

	@Override
	public DataResult<ListColorDto> getByColorId(int colorId)  {
		
		checkColorExists(colorId);
		
		var result = this.colorDao.getByColorId(colorId);		
		ListColorDto response = this.modelMapperService.forDto().map(result, ListColorDto.class);
		
		return new SuccessDataResult<ListColorDto>(response,Messages.COLORFOUND);
		
	}
	
	@Override
	public boolean checkColorNameExists(String colorName)  {
		
		var result = this.colorDao.getByColorName(colorName);
		if (result == null) {
			return true;
		}
		throw new BusinessException(Messages.COLORNAMEERROR);
	}
	@Override
	public boolean checkColorExists(int colorId) {
		
		var result = this.colorDao.getByColorId(colorId);
		if(result !=null) {
			return true;
		}
		throw new BusinessException(Messages.COLORNOTFOUND);
	}
}
