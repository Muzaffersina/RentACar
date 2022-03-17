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
	Result add(CreateRentalRequest createRentRequest) ;
	Result delete(DeleteRentalRequest deleteRentRequest) ;
	Result update(UpdateRentalRequest updateRentRequest);
		
	DataResult<List<ListRentDto>> getAll();
	DataResult<List<ListRentDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListRentDto>> getAllSorted(Sort.Direction direction);	
	
	DataResult<GetListRentDto> getByRentId(int rentalId) ;
	List<ListRentDto> getByCar_CarId(int carId) ;
	
	
	boolean checkRentCarExists(int carId);
	boolean checkRentCarDate(int carId);	
	boolean checkDate(LocalDate rentalDate , LocalDate returnDate);
	
	double calculatorTotalPrice(Rental rental ,List<Integer> additionalServiceId ,
			int carId ,int rentalCityId , int returnCityId);	
	
	boolean isCarUsed(int carId);	
	void updateCarKm(int carId , int lastKm);
}

