package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.ColorService;
import turkcell.rentacar.business.dtos.ListBrandDto;
import turkcell.rentacar.business.dtos.ListColorDto;
import turkcell.rentacar.business.requests.create.CreateColorRequest;
import turkcell.rentacar.business.requests.delete.DeleteColorRequest;
import turkcell.rentacar.business.requests.update.UpdateColorRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.ErrorResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.ColorDao;
import turkcell.rentacar.entities.concretes.Color;

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
	public DataResult<List<ListColorDto>> getAll() {
		var result = this.colorDao.findAll();
		List<ListColorDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListColorDto>>(response);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		if(checkIfColorName(color)) {
			this.colorDao.save(color);	
			return new SuccessResult("Renk.eklendi");
		}
		return new ErrorResult("Renk.silinemedi");
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		if(checkColorId(color)) {
			this.colorDao.deleteById(color.getColorId());
			return new SuccessResult("Renk.silindi");
		}
		return new ErrorResult("Renk.silinemedi");
		
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		if(checkColorId(color)){
			this.colorDao.save(color);
			return new SuccessResult("Renk.güncellendi");
		}
		return new ErrorResult("Renk.güncellenemedi");
	}

	@Override
	public DataResult<ListColorDto> getByColorId(int colorId) throws BusinessException {
		var result = this.colorDao.getByColorId(colorId);
		if (result != null) {
			ListColorDto response = this.modelMapperService.forDto().map(result, ListColorDto.class);
			return new SuccessDataResult<ListColorDto>(response,"Idsi "+colorId+ " olan renk getirildi.");
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
