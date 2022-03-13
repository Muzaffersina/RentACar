package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.GetListRentDto;
import turkcell.rentacar.business.dtos.ListRentDto;
import turkcell.rentacar.business.requests.create.CreateRentalRequest;
import turkcell.rentacar.business.requests.delete.DeleteRentalRequest;
import turkcell.rentacar.business.requests.update.UpdateRentalRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.entities.concretes.Rental;

public interface RentalService {
	Result add(CreateRentalRequest createRentRequest) throws BusinessException ;
	Result delete(DeleteRentalRequest deleteRentRequest) throws BusinessException;
	Result update(UpdateRentalRequest updateRentRequest) throws BusinessException;
		
	DataResult<List<ListRentDto>> getAll();
	DataResult<List<ListRentDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListRentDto>> getAllSorted(Sort.Direction direction);	
	
	DataResult<GetListRentDto> getByRentId(int rentalId) throws BusinessException;
	List<ListRentDto> getByCar_CarId(int carId) throws BusinessException;
	
	//boolean checkRentCarReturnDate(int carId) throws BusinessException;
	boolean checkRentCarExists(int carId) throws BusinessException;	
	boolean checkRentCarDate(int carId) throws BusinessException;	
	
	//double calculatorTotalPrice(Rental rental ,List<Integer> additionalServiceId , int carId ,String rentalCityName , String returnCityName) throws BusinessException ;
	double calculatorTotalPrice(Rental rental ,int additionalServiceId , int carId ,int rentalCityId , int returnCityId) throws BusinessException ;
	
	boolean IsCarUsed(int carId) throws BusinessException;
	boolean IsAdditionalServiceUsed(int additionalId) throws BusinessException;
}
