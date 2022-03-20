package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.CarDamageService;
import turkcell.rentacar.business.abstracts.CarService;
import turkcell.rentacar.business.dtos.ListCarDamageDto;
import turkcell.rentacar.business.requests.create.CreateCarDamageRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarDamageRequest;
import turkcell.rentacar.business.requests.update.UpdateCarDamageRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.CarDamageDao;
import turkcell.rentacar.entities.concretes.CarDamage;

@Service

public class CarDamageManager implements CarDamageService{

	
	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;
	private CarService carService;	
	
	@Autowired
	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService,
			CarService carService) {
		
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;		 				
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {
		
		this.carService.checkCarExists(createCarDamageRequest.getCarId());
		
		CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);
		this.carDamageDao.save(carDamage);
		
		return new SuccessResult("carDamage eklenmiştir");
	}

	@Override
	public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) {		
		
		checkCarDamageExists(deleteCarDamageRequest.getDamageId());
		
		CarDamage carDamage = this.modelMapperService.forRequest().map(deleteCarDamageRequest, CarDamage.class);
		this.carDamageDao.deleteById(carDamage.getDamageId());

		return new SuccessResult(carDamage.getDamageId() + " " + "carDamage.silindi");
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {
		
		this.carService.checkCarExists(updateCarDamageRequest.getCarId());		
		checkCarDamageExists(updateCarDamageRequest.getDamageId());
		
		CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
		this.carDamageDao.save(carDamage);
		
		return new SuccessResult("carDamage.güncellendi");	
		
	}

	@Override
	public DataResult<List<ListCarDamageDto>> getAll() {
		
		List<CarDamage> result = this.carDamageDao.findAll();
		List<ListCarDamageDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, ListCarDamageDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarDamageDto>>(response, "Listeleme başarılı.");
	}

	@Override
	public DataResult<List<ListCarDamageDto>> getAllPaged(int pageNo, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<CarDamage> result = this.carDamageDao.findAll(pageable).getContent();
		List<ListCarDamageDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, ListCarDamageDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarDamageDto>>(response, "Listeme başaralı bir şekilde gerceklesti");
	}

	@Override
	public DataResult<ListCarDamageDto> getByCarDamageId(int carDamageId) {

		checkCarDamageExists(carDamageId);
		
		CarDamage result = this.carDamageDao.getById(carDamageId);			
		ListCarDamageDto response = this.modelMapperService.forDto().map(result, ListCarDamageDto.class);	
		
		return new SuccessDataResult<ListCarDamageDto>(response);
	}
	
	@Override
	public DataResult<List<ListCarDamageDto>> getAllByCarId(int carId) {		
	

		List<CarDamage> result = this.carDamageDao.getAllByCar_CarId(carId);
		List<ListCarDamageDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, ListCarDamageDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarDamageDto>>(response);
	}	

	@Override
	public boolean checkCarDamageExists(int carDamageId) {
		
		var result = this.carDamageDao.existsById(carDamageId);		
		if (result) {
			return true;
		}
		throw new BusinessException("CarDamage için geçersiz Id");
	}

	
}
