package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.BrandService;
import turkcell.rentacar.business.abstracts.CarMaintenanceService;
import turkcell.rentacar.business.abstracts.CarService;
import turkcell.rentacar.business.abstracts.ColorService;
import turkcell.rentacar.business.abstracts.RentalService;
import turkcell.rentacar.business.contants.Messages;
import turkcell.rentacar.business.dtos.ListCarDto;
import turkcell.rentacar.business.requests.create.CreateCarRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarRequest;
import turkcell.rentacar.business.requests.update.UpdateCarRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.CarDao;
import turkcell.rentacar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private ColorService colorService;
	private BrandService brandService;
	private RentalService rentalService;
	private CarMaintenanceService carMaintenanceService;

	@Lazy
	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService, BrandService brandService,
			ColorService colorService, CarMaintenanceService carMaintenanceService, RentalService rentalService) {

		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.brandService = brandService;
		this.colorService = colorService;
		this.carMaintenanceService = carMaintenanceService;
		this.rentalService = rentalService;
	}

	@Override
	public DataResult<List<ListCarDto>> getAll() {

		var result = this.carDao.findAll();

		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarDto>>(response,Messages.CARLIST);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarDto>>(response, Messages.CARLIST);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllSorted(Direction direction) {

		Sort sort = Sort.by(direction, "dailyPrice");

		List<Car> result = this.carDao.findAll(sort);
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarDto>>(response, Messages.CARLIST);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		this.brandService.checkBrandExists(createCarRequest.getBrandId());
		this.colorService.checkColorExists(createCarRequest.getColorId());

		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setCarId(0);
		this.carDao.save(car);

		return new SuccessResult(Messages.CARADD);

	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {

		checkCarExists(deleteCarRequest.getCarId());
		carMaintenanceService.checkCarUsed(deleteCarRequest.getCarId());
		rentalService.checkCarUsed(deleteCarRequest.getCarId());

		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		this.carDao.deleteById(car.getCarId());

		return new SuccessResult(Messages.CARDELETE);

	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		checkCarExists(updateCarRequest.getCarId());
		this.brandService.checkBrandExists(updateCarRequest.getBrandId());
		this.colorService.checkColorExists(updateCarRequest.getColorId());
		

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carDao.save(car);

		return new SuccessResult(Messages.CARUPDATE);

	}

	@Override
	public DataResult<ListCarDto> getByCarId(int carId) {

		checkCarExists(carId);

		var result = this.carDao.getByCarId(carId);
		ListCarDto response = this.modelMapperService.forDto().map(result, ListCarDto.class);

		return new SuccessDataResult<ListCarDto>(response, Messages.CARFOUND);

	}

	@Override
	public DataResult<List<ListCarDto>> getByDailyPriceLessThanEqual(double dailyPrice) {

		List<Car> result = this.carDao.getByDailyPriceLessThanEqual(dailyPrice);
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarDto>>(response,Messages.CARLISTFORPRICE);
	}

	@Override
	public boolean checkCarExists(int carId) {
		var result = this.carDao.getByCarId(carId);
		if (result != null) {
			return true;
		}
		throw new BusinessException(Messages.CARNOTFOUND);
	}

	@Override
	public double calculateRentalPrice(int carId) {

		checkCarExists(carId);

		Car car = this.carDao.getByCarId(carId);
		double price = car.getDailyPrice();
		return price;
	}

	@Override
	public double checkDailyKm(int carId) {
		
		checkCarExists(carId);

		Car car = this.carDao.getByCarId(carId);
		double dailkKm = car.getDailyKm();
		return dailkKm;
	}

	@Override
	public boolean checkColorUsed(int colorId) {

		this.colorService.checkColorExists(colorId);

		if (this.carDao.getByColor_ColorId(colorId) != null) {
			throw new BusinessException(Messages.COLORNOTDELETE);
		}
		return true;
	}

	@Override
	public boolean checkBrandUsed(int brandId) {

		this.brandService.checkBrandExists(brandId);

		if (this.carDao.getByBrand_BrandId(brandId) != null) {
			throw new BusinessException(Messages.BRANDNOTDELETE);
		}
		return true;
	}

	@Override
	public void updateCarKm(int carId, int lastKm) {

		Car car = this.carDao.getById(carId);
		car.setCarTotalKm(lastKm);
	}

}
