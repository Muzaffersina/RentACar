package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.AdditionalService;
import turkcell.rentacar.business.dtos.ListAdditionalDto;
import turkcell.rentacar.business.requests.create.CreateAdditionalRequest;
import turkcell.rentacar.business.requests.delete.DeleteAdditionalRequest;
import turkcell.rentacar.business.requests.update.UpdateAdditionalRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
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
	public Result add(CreateAdditionalRequest createAdditionalRequest) {

		// aynı isim kontorl..

		Additional additional = this.modelMapperService.forRequest().map(createAdditionalRequest, Additional.class);
		this.additionalDao.save(additional);

		return new SuccessResult("AdditionalService.eklendi");
	}

	@Override
	public Result delete(DeleteAdditionalRequest deleteAdditionalRequest) {

		checkAdditionalExists(deleteAdditionalRequest.getAdditionalServiceId());

		// ordered da kontrol
		this.additionalDao.deleteById(deleteAdditionalRequest.getAdditionalServiceId()); // iyi yontem !!

		return new SuccessResult("AdditionalService.silindi");
	}

	@Override
	public Result update(UpdateAdditionalRequest updateAdditionalRequest) {

		checkAdditionalExists(updateAdditionalRequest.getAdditionalServiceId());

		Additional additional = this.modelMapperService.forRequest().map(updateAdditionalRequest, Additional.class);
		this.additionalDao.save(additional);

		return new SuccessResult("AdditionalService.guncellendi");
	}

	@Override
	public DataResult<List<ListAdditionalDto>> getAll() {

		var result = this.additionalDao.findAll();
		List<ListAdditionalDto> response = result.stream()
				.map(additional -> this.modelMapperService.forDto().map(additional, ListAdditionalDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListAdditionalDto>>(response);
	}

	@Override
	public DataResult<List<ListAdditionalDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<Additional> result = this.additionalDao.findAll(pageable).getContent();
		List<ListAdditionalDto> response = result.stream()
				.map(additional -> this.modelMapperService.forDto().map(additional, ListAdditionalDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListAdditionalDto>>(response, "Listeleme başarılı.");
	}

	@Override
	public DataResult<List<ListAdditionalDto>> getAllSorted(Direction direction) {

		Sort sort = Sort.by(direction, "additionalPrice");

		List<Additional> result = this.additionalDao.findAll(sort);
		List<ListAdditionalDto> response = result.stream()
				.map(additional -> this.modelMapperService.forDto().map(additional, ListAdditionalDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListAdditionalDto>>(response, "additionalPrice'e gore listelenmiştir");
	}

	@Override
	public DataResult<ListAdditionalDto> getById(int additionalId) {

		checkAdditionalExists(additionalId);

		var result = this.additionalDao.getById(additionalId);
		ListAdditionalDto response = this.modelMapperService.forDto().map(result, ListAdditionalDto.class);

		return new SuccessDataResult<ListAdditionalDto>(response);
	}

	@Override
	public void checkAllAdditional(List<Integer> additionalIds) {

		for (int additionalId : additionalIds) {
			if (additionalId != 0) {
				checkAdditionalExists(additionalId);
			}
		}

	}

	@Override
	public boolean checkAdditionalExists(int additionalId) {

		if (!this.additionalDao.existsById(additionalId)) {
			throw new BusinessException("AdditionalService için geçersiz Id....!");

		}

		return true;
	}

	@Override
	public double calculateAdditionalServicePrice(List<Integer> additionalServiceId) {

		double additionalServicePrice = 0;
		for (int additonalService : additionalServiceId) {
			if (additonalService != 0) {
				Additional additional = this.additionalDao.getById(additonalService);
				additionalServicePrice += additional.getAdditionalPrice();
			}
		}
		return additionalServicePrice;
	}
}
