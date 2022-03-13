package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import turkcell.rentacar.business.dtos.ListUserDto;
import turkcell.rentacar.business.requests.delete.DeleteUserRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface UserService {
	
	Result delete(DeleteUserRequest deleteUserRequest)throws BusinessException;	
	
	DataResult<List<ListUserDto>> getAll();
	DataResult<List<ListUserDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListUserDto>> getAllSorted(Sort.Direction direction);
	
	boolean checkUserExists(int userId) throws BusinessException;

}
