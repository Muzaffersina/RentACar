package turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.CarMaintenanceService;
import turkcell.rentacar.business.abstracts.CarService;
import turkcell.rentacar.business.abstracts.RentalService;
import turkcell.rentacar.business.dtos.GetListCarMaintenanceDto;
import turkcell.rentacar.business.dtos.ListCarMaintenanceDto;
import turkcell.rentacar.business.requests.create.CreateCarMaintenanceRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarMaintenanceRequest;
import turkcell.rentacar.business.requests.update.UpdateCarMaintenanceRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.CarMaintenanceDao;
import turkcell.rentacar.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private RentalService rentService;

	@Lazy
	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,
			CarService carService, RentalService rentService) {

		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.rentService = rentService;
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {

		this.carService.checkCarExists(createCarMaintenanceRequest.getCarId());		
		this.rentService.checkRentCarDate(createCarMaintenanceRequest.getCarId());
		

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);
		carMaintenance.setMaintenanceId(0);
		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(carMaintenance.getMaintenanceId() + "nolu CarMaintenance.eklendi");

	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {

		checkCarMaintenanceExists(deleteCarMaintenanceRequest.getMaintenanceId());
		checkCarMaintenceReturnDate(deleteCarMaintenanceRequest.getMaintenanceId());

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.deleteById(carMaintenance.getMaintenanceId());

		return new SuccessResult(carMaintenance.getMaintenanceId() + "nolu CarMaintenance.silindi");

	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {

		checkCarMaintenanceExists(updateCarMaintenanceRequest.getMaintenanceId());

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(carMaintenance.getMaintenanceId() + "nolu CarMaintenance.guncellendi");
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAll() {

		List<CarMaintenance> result = this.carMaintenanceDao.findAll();
		List<ListCarMaintenanceDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response, "Listeme başaralı bir şekilde gerceklesti");
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<CarMaintenance> result = this.carMaintenanceDao.findAll(pageable).getContent();
		List<ListCarMaintenanceDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response, "Listeme başaralı bir şekilde gerceklesti");
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAllSorted(Direction direction) {

		Sort sort = Sort.by(direction, "returnDate");

		List<CarMaintenance> result = this.carMaintenanceDao.findAll(sort);

		List<ListCarMaintenanceDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response,
				direction + " Listeme returnDate'e başaralı bir şekilde gerceklesti");
	}

	@Override
	public DataResult<GetListCarMaintenanceDto> getByMaintenanceId(int carMaintenanceId) throws BusinessException {

		checkCarMaintenanceExists(carMaintenanceId);

		var result = this.carMaintenanceDao.getById(carMaintenanceId);
		GetListCarMaintenanceDto response = this.modelMapperService.forDto().map(result,
				GetListCarMaintenanceDto.class);

		return new SuccessDataResult<GetListCarMaintenanceDto>(response);

	}

	@Override
	public List<ListCarMaintenanceDto> getByCar_CarId(int carId) throws BusinessException {

		List<CarMaintenance> result = this.carMaintenanceDao.getByCar_CarId(carId);
		List<ListCarMaintenanceDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
				.collect(Collectors.toList());

		return response;
	}

	@Override
	public boolean checkCarMaintenanceExists(int carMaintenanceId) throws BusinessException {
		var result = this.carMaintenanceDao.getByMaintenanceId(carMaintenanceId);
		if (result != null) {
			return true;
		}
		throw new BusinessException("CarMaintenance için geçersiz Id.!!!!");
	}

	@Override
	public boolean checkCarMaintenceCarIdReturnDate(int carId) throws BusinessException {
		var result = this.carMaintenanceDao.getCarMaintenanceByCarCarIdAndReturnDateIsNull(carId);
		if (result != null) {
			throw new BusinessException("Bu arac suan bakimdadir.!!!!");
		}
		return true;
	}

	@Override
	public boolean checkCarMaintenceReturnDate(int maintenanceId) throws BusinessException {

		checkCarMaintenanceExists(maintenanceId);

		var result = this.carMaintenanceDao.getByMaintenanceId(maintenanceId);
		if (result != null) {
			long daysBetween = ChronoUnit.DAYS.between(result.getReturnDate(), LocalDate.now());
			System.out.println(daysBetween);
			if (daysBetween <= 0) {
				System.out.println(daysBetween+"!!!!!!!!!!!!!!!!!!");
				throw new BusinessException("Bu araç şuan bakımdadır!!!!...");
			}
		}
		return true;
	}

	@Override
	public boolean IsCarUsed(int carId) throws BusinessException {
		var result = this.carMaintenanceDao.getCarMaintenanceByCarCarIdAndReturnDateIsNull(carId);
		if (result != null) {
			throw new BusinessException("Silinemez... Bu arac suan bakimdadir.!!!!");
		}
		return true;
	}

}
