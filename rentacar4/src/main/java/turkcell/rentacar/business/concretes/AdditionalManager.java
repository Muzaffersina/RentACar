package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.AdditionalService;
import turkcell.rentacar.business.dtos.ListAdditionalDto;
import turkcell.rentacar.business.requests.create.CreateAdditionalRequest;
import turkcell.rentacar.business.requests.delete.DeleteAdditionalRequest;
import turkcell.rentacar.business.requests.update.UpdateAdditionalRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.ErrorDataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.AdditionalDao;
import turkcell.rentacar.entities.concretes.Additional;

@Service
public class AdditionalManager implements AdditionalService {

	private AdditionalDao additionalDao;
	private ModelMapperService modelMapperService;
		
	@Autowired
	public AdditionalManager(AdditionalDao additionalDao, ModelMapperService modelMapperService) {
		this.additionalDao = additionalDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalRequest createAdditionalRequest) throws BusinessException{		
		Additional additional = this.modelMapperService.forRequest().map(createAdditionalRequest, Additional.class);
		this.additionalDao.save(additional);
		return new SuccessResult("AdditionalService.eklendi");	
	}

	@Override
	public Result delete(DeleteAdditionalRequest deleteAdditionalRequest) throws BusinessException {
		checkAdditionalId(deleteAdditionalRequest.getAdditionalServiceId());		
		this.additionalDao.deleteById(deleteAdditionalRequest.getAdditionalServiceId()); // iyi yontem !!
		return new SuccessResult("AdditionalService.silindi");	
	}

	@Override
	public Result update(UpdateAdditionalRequest updateAdditionalRequest) throws BusinessException{
		checkAdditionalId(updateAdditionalRequest.getAdditionalServiceId());	
		Additional additional = this.modelMapperService.forRequest().map(updateAdditionalRequest,Additional.class);
		this.additionalDao.save(additional);
		return new SuccessResult("AdditionalService.guncellendi");			
	}

	@Override
	public DataResult<List<ListAdditionalDto>> getAll() {
		var result = this.additionalDao.findAll();
		if (result.isEmpty()) {
			return new ErrorDataResult<List<ListAdditionalDto>>("Liste Bos");
		}
		List<ListAdditionalDto> response = result.stream()
				.map(additional -> this.modelMapperService.forDto().map(additional, ListAdditionalDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListAdditionalDto>>(response);
	}

	@Override
	public DataResult<ListAdditionalDto> getById(int additionalId) throws BusinessException {
		checkAdditionalId(additionalId);
		var result = this.additionalDao.getById(additionalId);		
		ListAdditionalDto response =  this.modelMapperService.forDto().map(result, ListAdditionalDto.class);
		return new SuccessDataResult<ListAdditionalDto>(response);
	}

	@Override
	public boolean checkAdditionalId(int additionalId) throws BusinessException {			
		if (!this.additionalDao.existsById(additionalId)) {
			throw new BusinessException("AdditionalService için geçersiz Id....!");
		}
		return true;
	}

}
