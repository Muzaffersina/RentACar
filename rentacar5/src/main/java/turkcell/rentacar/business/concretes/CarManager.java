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
	private RentalService rentalService ;
	private CarMaintenanceService carMaintenanceService;

	@Lazy
	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService
			,BrandService brandService,ColorService colorService,
			CarMaintenanceService carMaintenanceService,RentalService rentalService ) {
		
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.brandService = brandService ;
		this.colorService = colorService ;
		this.carMaintenanceService = carMaintenanceService; 
		this.rentalService = rentalService;
	}

	@Override
	public DataResult<List<ListCarDto>> getAll() {
		
		var result = this.carDao.findAll();
		
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
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
		
		return new SuccessDataResult<List<ListCarDto>>(response,"dailyPrice göre listelenmiştir.");
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {
		
		this.brandService.checkBrandExists(createCarRequest.getBrandId());
		this.colorService.checkColorExists(createCarRequest.getColorId());
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		
		return new SuccessResult("Arac.eklendi");

	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {
		
		checkCarExists(deleteCarRequest.getCarId());
		
		carMaintenanceService.IsCarUsed(deleteCarRequest.getCarId());
		rentalService.IsCarUsed(deleteCarRequest.getCarId());
		
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);	
		
		this.carDao.deleteById(car.getCarId());
		
		return new SuccessResult("Arac.silindi");
		
		

	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
		
		this.brandService.checkBrandExists(updateCarRequest.getBrandId());
		this.colorService.checkColorExists(updateCarRequest.getColorId());
		checkCarExists(updateCarRequest.getCarId());
		
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);		
		this.carDao.save(car);
		
		return new SuccessResult("Arac.güncellendi");
	
	

	}

	@Override
	public DataResult<ListCarDto> getByCarId(int carId) throws BusinessException {
		
		checkCarExists(carId);
		
		var result = this.carDao.getByCarId(carId);		
		ListCarDto response = this.modelMapperService.forDto().map(result, ListCarDto.class);
		
		return new SuccessDataResult<ListCarDto>(response, "Idsi " + carId + " olan arac getirildi.");

	}

	@Override
	public DataResult<List<ListCarDto>> getByDailyPriceLessThanEqual(double dailyPrice) {
		
		List<Car> result = this.carDao.getByDailyPriceLessThanEqual(dailyPrice);
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	@Override
	public boolean checkCarExists(int carId) throws BusinessException {
		var result = this.carDao.getByCarId(carId);
		if (result != null) {
			return true;
		}
		throw new BusinessException("Araç için geçersiz Id");
	}

	@Override
	public double calculateRentalPrice(int carId) throws BusinessException {
		
		checkCarExists(carId);
		
		Car car = this.carDao.getByCarId(carId);
		double price = car.getDailyPrice();
		
		return price;
	}
	
	@Override
	public boolean IsColorUsed(int colorId) throws BusinessException {
		
		this.colorService.checkColorExists(colorId);		
		
		if(this.carDao.getByColor_ColorId(colorId) != null) {
			throw new BusinessException("Silinemez. Bu renk kullanımdadır.");
		}
		return true;
	}

	@Override
	public boolean IsBrandUsed(int brandId) throws BusinessException {
		
		this.brandService.checkBrandExists(brandId);
		
		if(this.carDao.getByBrand_BrandId(brandId) != null) {
			throw new BusinessException("Silinemez. Bu model kullanımdadır.");
		}
		return true;		
		
	}

	

}
