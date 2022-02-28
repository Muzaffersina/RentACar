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
import turkcell.rentacar.business.dtos.ListCarDto;
import turkcell.rentacar.business.requests.create.CreateCarRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarRequest;
import turkcell.rentacar.business.requests.update.UpdateCarRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.ErrorResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.CarDao;
import turkcell.rentacar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCarDto>> getAll() {
		var result = this.carDao.findAll();
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		return new SuccessResult("Arac.eklendi");

	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		if (checkCarId(car)) {
			this.carDao.deleteById(car.getCarId());
			return new SuccessResult("Arac.silindi");
		}
		return new SuccessResult("Arac.silinmedi");

	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		if (checkCarId(car)) {
			this.carDao.save(car);
			return new SuccessResult("Arac.güncellendi");
		}
		return new ErrorResult("Arac.güncellenmedi");

	}

	private boolean checkCarId(Car car) throws BusinessException {
		var result = this.carDao.getByCarId(car.getCarId());
		if (result != null) {
			return true;
		}
		throw new BusinessException("Araç için geçersiz Id");
	}

	@Override
	public DataResult<ListCarDto> getByCarId(int carId) throws BusinessException {

		var result = this.carDao.getByCarId(carId);
		if (result != null) {
			ListCarDto response = this.modelMapperService.forDto().map(result, ListCarDto.class);
			return new SuccessDataResult<ListCarDto>(response,"Idsi "+carId+ " olan arac getirildi.");
		}
		throw new BusinessException("Bu id boş.");
	}

	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(response, "Listeleme başarılı.");
	}

	@Override
	public DataResult<List<ListCarDto>> getAllSorted(Direction direction) {
		Sort sort = Sort.by(direction, "dailyPrice");
		List<Car> result = this.carDao.findAll(sort);
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	@Override
	public DataResult<List<ListCarDto>> getByDailyPriceLessThanEqual(double dailyPrice) {
		List<Car> result = this.carDao.getByDailyPriceLessThanEqual(dailyPrice);
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

}
