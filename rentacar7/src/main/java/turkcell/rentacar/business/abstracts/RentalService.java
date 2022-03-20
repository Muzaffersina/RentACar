package turkcell.rentacar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.GetListRentDto;
import turkcell.rentacar.business.dtos.ListRentDto;
import turkcell.rentacar.business.requests.create.CreateRentalRequest;
import turkcell.rentacar.business.requests.delete.DeleteRentalRequest;
import turkcell.rentacar.business.requests.update.UpdateRentalRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.entities.concretes.Rental;

public interface RentalService {
	Result add(CreateRentalRequest createRentRequest);
	// Ind ve Corp musteriler icin farklı add
	Result delete(DeleteRentalRequest deleteRentRequest) ;
	Result update(UpdateRentalRequest updateRentRequest);
	// Ind ve Corp musteriler icin farklı add
		
	DataResult<List<ListRentDto>> getAll();
	DataResult<List<ListRentDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListRentDto>> getAllSorted(Sort.Direction direction);	
	
	DataResult<GetListRentDto> getByRentId(int rentalId) ;
	List<ListRentDto> getByCarCarId(int carId) ;
	
	
	boolean checkRentCarExists(int rentalId);
	boolean checkRentCarDate(int carId);	
	boolean checkDate(LocalDate rentalDate , LocalDate returnDate);
	
	double calculatorTotalPrice(Rental rental ,List<Integer> additionalServiceId ,
			int carId ,int rentalCityId , int returnCityId);	
	
	boolean checkCarUsed(int carId);
	boolean checkCityUsed(int cityId);
	boolean checkCustomerUsed(int customerId);
	void updateCarKm(int carId , int lastKm);
	Rental returnRental(int rentalId);
}

