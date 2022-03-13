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

import turkcell.rentacar.business.abstracts.AdditionalService;
import turkcell.rentacar.business.abstracts.CarMaintenanceService;
import turkcell.rentacar.business.abstracts.CarService;
import turkcell.rentacar.business.abstracts.CityService;
import turkcell.rentacar.business.abstracts.RentalService;
import turkcell.rentacar.business.abstracts.UserService;
import turkcell.rentacar.business.dtos.GetListRentDto;
import turkcell.rentacar.business.dtos.ListRentDto;
import turkcell.rentacar.business.requests.create.CreateRentalRequest;
import turkcell.rentacar.business.requests.delete.DeleteRentalRequest;
import turkcell.rentacar.business.requests.update.UpdateRentalRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.RentalDao;
import turkcell.rentacar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;	
	private AdditionalService additionalService;
	private CityService cityService;
	private UserService userService;

	@Lazy
	@Autowired
	public RentalManager(RentalDao rentDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService, CarService carService,
			AdditionalService additionalService,
			CityService cityService , UserService userService) {

		this.rentDao = rentDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.userService = userService;
		this.additionalService = additionalService;
		this.cityService = cityService;

	}

	@Override
	public Result add(CreateRentalRequest createRentRequest) throws BusinessException {

		
		this.carService.checkCarExists(createRentRequest.getCarId());
		this.carMaintenanceService.checkCarMaintenceReturnDate(createRentRequest.getCarId());		
		this.cityService.checkCityExists(createRentRequest.getRentalCityId());
		this.cityService.checkCityExists(createRentRequest.getReturnCityId());
		this.userService.checkUserExists(createRentRequest.getUserId());
		// this.additionalService.checkAllAdditional(createRentRequest.getAdditionalId());
		this.additionalService.checkAdditionalExists(createRentRequest.getAdditionalId());

		//checkRentCarReturnDate(createRentRequest.getCarId());
		checkRentCarDate(createRentRequest.getCarId());

		Rental rent = this.modelMapperService.forRequest().map(createRentRequest, Rental.class);
		rent.setTotalPrice(calculatorTotalPrice(rent, createRentRequest.getAdditionalId(), createRentRequest.getCarId(),
				createRentRequest.getRentalCityId(), createRentRequest.getReturnCityId()));
		
		this.rentDao.save(rent);

		return new SuccessResult("Arac kiralama basarili bir sekilde gerceklesti.");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentRequest) throws BusinessException {

		checkRentCarExists(deleteRentRequest.getRentalId());

		Rental rent = this.modelMapperService.forRequest().map(deleteRentRequest, Rental.class);
		this.rentDao.deleteById(rent.getRentalId());

		return new SuccessResult(rent.getRentalId() + " " + "RentCar.silindi");

	}

	@Override
	public Result update(UpdateRentalRequest updateRentRequest) throws BusinessException {

		checkRentCarExists(updateRentRequest.getRentalId());		
		this.carService.checkCarExists(updateRentRequest.getCarId());
		this.cityService.checkCityExists(updateRentRequest.getRentalCityId());
		this.cityService.checkCityExists(updateRentRequest.getReturnCityId());
		this.userService.checkUserExists(updateRentRequest.getUserId());
		// this.additionalService.checkAllAdditional(updateRentRequest.getAdditionalId());

		// rentId nın dogru fakat carId sı tutmadıgında hata mesajı verdirme .....
		Rental rent = this.modelMapperService.forRequest().map(updateRentRequest, Rental.class);

		rent.setTotalPrice(calculatorTotalPrice(rent, updateRentRequest.getAdditionalId(), updateRentRequest.getCarId(),
				updateRentRequest.getRentalCityId(), updateRentRequest.getReturnCityId()));

		this.rentDao.save(rent);

		return new SuccessResult(rent.getRentalId() + " " + "RentCar.guncellendi");
	}

	@Override
	public DataResult<List<ListRentDto>> getAll() {

		List<Rental> result = this.rentDao.findAll();
		List<ListRentDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, ListRentDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListRentDto>>(response, "Listeleme başarılı.");
	}

	@Override
	public DataResult<List<ListRentDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Rental> result = this.rentDao.findAll(pageable).getContent();
		List<ListRentDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, ListRentDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListRentDto>>(response, "Listeme başaralı bir şekilde gerceklesti");
	}

	@Override
	public DataResult<List<ListRentDto>> getAllSorted(Direction direction) {

		Sort sort = Sort.by(direction, "returnDate");

		List<Rental> result = this.rentDao.findAll(sort);
		List<ListRentDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, ListRentDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListRentDto>>(response,
				direction + " Listeme returnDate'e başaralı bir şekilde gerceklesti");
	}

	@Override
	public DataResult<GetListRentDto> getByRentId(int rentalId) throws BusinessException {

		checkRentCarExists(rentalId);

		var result = this.rentDao.getByRentalId(rentalId);
		GetListRentDto response = this.modelMapperService.forDto().map(result, GetListRentDto.class);

		return new SuccessDataResult<GetListRentDto>(response);
	}

	@Override
	public List<ListRentDto> getByCar_CarId(int carId) throws BusinessException {

		List<Rental> result = this.rentDao.getByCar_CarId(carId);
		List<ListRentDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, ListRentDto.class))
				.collect(Collectors.toList());

		return response;
	}

	@Override
	public boolean checkRentCarExists(int carId) throws BusinessException {

		var result = this.rentDao.getByRentalId(carId);
		if (result != null) {
			return true;
		}
		throw new BusinessException("RentCar için geçersiz Id..!!!!");
	}
	
	
	/*
	 * @Override public double calculatorTotalPrice(Rental rental ,List<Integer>
	 * additionalServiceId , int carId, String rentalCityName , String
	 * returnCityName) throws BusinessException {
	 * 
	 * double totalPrice = 0; long daysBetween =
	 * ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate());
	 * if(daysBetween == 0) { daysBetween = 1; } double additionalServicePrice =
	 * this.additionalService.calculateAdditionalServicePrice(additionalServiceId)
	 * *daysBetween; double carRentalPrice =
	 * this.carService.calculateRentalPrice(carId) * daysBetween ; totalPrice =
	 * additionalServicePrice + carRentalPrice; if(rentalCityName == returnCityName)
	 * { double differentCityPrice = 100 * daysBetween ; totalPrice +=
	 * differentCityPrice; }
	 * 
	 * return totalPrice; }
	 */

	@Override
	public double calculatorTotalPrice(Rental rental, int additionalServiceId, int carId,int rentalCityId,
			int returnCityId ) throws BusinessException {

		double totalPrice = 0;
		long daysBetween = ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate());
		if (daysBetween == 0) {
			daysBetween = 1;
		}
		if (daysBetween < 0) {
			throw new BusinessException("Tarihler yanlış girilmiştir.");
		}	
		
		double additionalServicePrice = this.additionalService.calculateAdditionalServicePrice(additionalServiceId)
				* daysBetween;
		double carRentalPrice = this.carService.calculateRentalPrice(carId) * daysBetween;
		totalPrice = additionalServicePrice + carRentalPrice;
		if (rentalCityId != returnCityId) {
			double differentCityPrice = 100 * daysBetween;
			totalPrice += differentCityPrice;
		}

		return totalPrice;
	}

	@Override
	public boolean IsCarUsed(int carId) throws BusinessException {
		var result = checkRentCarDate(carId);
		if (!result) {
			throw new BusinessException("Silinemez...Bu arac suan kiralamadadır..!!!!");
		}
		return true;
	}

	@Override
	public boolean IsAdditionalServiceUsed(int additionalId) throws BusinessException {
		
		this.additionalService.checkAdditionalExists(additionalId);
		
		if(this.rentDao.getByAdditional_AdditionalId(additionalId) != null) {
			throw new BusinessException("Silinemez. Bu ek hizmet kullanımdadır.");
		}
		return true;		
		
	}

	@Override
	public boolean checkRentCarDate(int carId) throws BusinessException {
		var result = this.rentDao.getByCar_CarId(carId);
		for (Rental rental : result) {
			long daysBetween = ChronoUnit.DAYS.between(rental.getReturnDate(),LocalDate.now() );
			System.out.println(daysBetween);
			if(daysBetween<=0) {
				throw new BusinessException("Bu araç şuan kiralamadadır.");
			}		
		}		
		return true;
	}

}
