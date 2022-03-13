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
import turkcell.rentacar.business.abstracts.CarService;
import turkcell.rentacar.business.abstracts.RentalService;
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
	private CarService carService;
	private RentalService rentalService;

	@Autowired
	public AdditionalManager(AdditionalDao additionalDao, ModelMapperService modelMapperService,
			CarService carService , RentalService rentalService ) {

		this.additionalDao = additionalDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.rentalService = rentalService;
	}

	@Override
	public Result add(CreateAdditionalRequest createAdditionalRequest) throws BusinessException {

		// aynı isim kontorl..

		Additional additional = this.modelMapperService.forRequest().map(createAdditionalRequest, Additional.class);
		this.additionalDao.save(additional);

		return new SuccessResult("AdditionalService.eklendi");
	}

	@Override
	public Result delete(DeleteAdditionalRequest deleteAdditionalRequest) throws BusinessException {

		checkAdditionalExists(deleteAdditionalRequest.getAdditionalServiceId());
		this.rentalService.IsAdditionalServiceUsed(deleteAdditionalRequest.getAdditionalServiceId());

		this.additionalDao.deleteById(deleteAdditionalRequest.getAdditionalServiceId()); // iyi yontem !!

		return new SuccessResult("AdditionalService.silindi");
	}

	@Override
	public Result update(UpdateAdditionalRequest updateAdditionalRequest) throws BusinessException {

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
	public DataResult<ListAdditionalDto> getById(int additionalId) throws BusinessException {

		checkAdditionalExists(additionalId);

		var result = this.additionalDao.getById(additionalId);
		ListAdditionalDto response = this.modelMapperService.forDto().map(result, ListAdditionalDto.class);

		return new SuccessDataResult<ListAdditionalDto>(response);
	}

	// metot sonunda var mı dediğimiz için exists yapcazzz

	@Override
	public void checkAllAdditional(List<Integer> additionalIds) throws BusinessException {
		for (int additionalId : additionalIds) {
			checkAdditionalExists(additionalId);
		}
	}

	@Override
	public boolean checkAdditionalExists(int additionalId) throws BusinessException {
		if (!this.additionalDao.existsById(additionalId)) {
			throw new BusinessException("AdditionalService için geçersiz Id....!");
		}
		return true;
	}
	/*
	 * @Override public double calculateAdditionalServicePrice(List<Integer>
	 * additionalServiceId) {
	 * 
	 * double additionalServicePrice = 0; for (int additonalService :
	 * additionalServiceId) { Additional additional =
	 * this.additionalDao.getById(additonalService); additionalServicePrice +=
	 * additional.getAdditionalPrice(); } return additionalServicePrice; }
	 */

	@Override
	public double calculateAdditionalServicePrice(int additionalServiceId) {

		double additionalServicePrice = 0;
		Additional additional = this.additionalDao.getById(additionalServiceId);
		additionalServicePrice += additional.getAdditionalPrice();

		return additionalServicePrice;
	}

}
