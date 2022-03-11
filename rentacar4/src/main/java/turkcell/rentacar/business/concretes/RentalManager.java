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

import turkcell.rentacar.business.abstracts.AdditionalService;
import turkcell.rentacar.business.abstracts.CarMaintenanceService;
import turkcell.rentacar.business.abstracts.CarService;
import turkcell.rentacar.business.abstracts.CustomerService;
import turkcell.rentacar.business.abstracts.RentalService;
import turkcell.rentacar.business.dtos.GetListRentDto;
import turkcell.rentacar.business.dtos.ListRentDto;
import turkcell.rentacar.business.requests.create.CreateRentalRequest;
import turkcell.rentacar.business.requests.delete.DeleteRentalRequest;
import turkcell.rentacar.business.requests.update.UpdateRentalRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.ErrorDataResult;
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
	private CustomerService customerService;
	private AdditionalService additionalService;
	

	@Lazy
	@Autowired
	public RentalManager(RentalDao rentDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService, CarService carService,
			CustomerService customerService ,AdditionalService additionalService
			) {
		this.rentDao = rentDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.customerService = customerService;
		this.additionalService = additionalService;
		
	}

	@Override
	public Result add(CreateRentalRequest createRentRequest) throws BusinessException {
		////// customer kontrolu ıle ekleme olmayan musterıde hata veriyor...
		this.carService.checkCarId(createRentRequest.getCarId());
		this.carMaintenanceService.checkCarMaintenceReturnDate(createRentRequest.getCarId());
		this.customerService.checkCustomerId(createRentRequest.getCustomerId());
		this.additionalService.getById(createRentRequest.getAdditionalId());
		checkRentCarReturnDate(createRentRequest.getCarId());
		Rental rent = this.modelMapperService.forRequest().map(createRentRequest, Rental.class);		
		this.rentDao.save(rent);			
		return new SuccessResult("Arac kiralama basarili bir sekilde gerceklesti.");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentRequest) throws BusinessException {
		checkRentCarId(deleteRentRequest.getRentalId());
		Rental rent = this.modelMapperService.forRequest().map(deleteRentRequest, Rental.class);
		this.rentDao.deleteById(rent.getRentalId());
		return new SuccessResult(rent.getRentalId() + " " + "RentCar.silindi");

	}

	@Override
	public Result update(UpdateRentalRequest updateRentRequest) throws BusinessException {
		checkRentCarId(updateRentRequest.getRentalId());
		this.additionalService.getById(updateRentRequest.getAdditionalId());
		this.customerService.checkCustomerId(updateRentRequest.getCustomerId());
		this.carService.checkCarId(updateRentRequest.getCarId());
		//rentId nın dogru fakat carId sı tutmadıgında hata mesajı verdirme ..... 		
		Rental rent = this.modelMapperService.forRequest().map(updateRentRequest, Rental.class);
		this.rentDao.save(rent);
		return new SuccessResult(rent.getRentalId() + " " + "RentCar.guncellendi");		
	}

	@Override
	public DataResult<List<ListRentDto>> getAll() {
		List<Rental> result = this.rentDao.findAll();
		if (result.isEmpty()) {
			return new ErrorDataResult<List<ListRentDto>>("Liste Bos");
		}
		List<ListRentDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, ListRentDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListRentDto>>(response, "Listeleme başarılı.");
	}

	@Override
	public DataResult<List<ListRentDto>> getAllPaged(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Rental> result = this.rentDao.findAll(pageable).getContent();
		if (result.isEmpty()) {
			return new ErrorDataResult<List<ListRentDto>>("Liste Bos");
		}		
		List<ListRentDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, ListRentDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListRentDto>>(response, "Listeme başaralı bir şekilde gerceklesti");
	}

	@Override
	public DataResult<List<ListRentDto>> getAllSorted(Direction direction) {
		Sort sort = Sort.by(direction, "returnDate");
		List<Rental> result = this.rentDao.findAll(sort);
		if (result.isEmpty()) {
			return new ErrorDataResult<List<ListRentDto>>("Liste Bos");
		}
		List<ListRentDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, ListRentDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListRentDto>>(response, direction + " Listeme returnDate'e başaralı bir şekilde gerceklesti");
	}

	@Override
	public DataResult<GetListRentDto> getByRentId(int rentalId) throws BusinessException {
		checkRentCarId(rentalId);
		var result = this.rentDao.getByRentalId(rentalId);		
		GetListRentDto response = this.modelMapperService.forDto().map(result, GetListRentDto.class);
		return new SuccessDataResult<GetListRentDto>(response);
	}

	@Override
	public List<ListRentDto> getByCar_CarId(int carId) throws BusinessException {		
		List<Rental> result = this.rentDao.getByCar_CarId(carId);
		if (result.isEmpty()) {
			throw new BusinessException("Bu arac suan kiralamada degildir.!!!!");
		}
		List<ListRentDto> response = result.stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, ListRentDto.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public boolean checkRentCarId(int carId) throws BusinessException {
		var result = this.rentDao.getByRentalId(carId);
		if (result != null) {
			return true;
		}
		throw new BusinessException("RentCar için geçersiz Id..!!!!");
	}

	@Override
	public boolean checkRentCarReturnDate(int carId) throws BusinessException {
		var result = this.rentDao.getRentalByCarCarIdAndReturnDateIsNull(carId);
		if (result != null) {
			throw new BusinessException("Bu arac suan kiralamadadır..!!!!");
		}
		return true;
	}	
	
}
