package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.UserService;
import turkcell.rentacar.business.dtos.ListUserDto;
import turkcell.rentacar.business.requests.delete.DeleteUserRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.UserDao;
import turkcell.rentacar.entities.concretes.User;

@Service
public class UserManager implements UserService {

	private ModelMapperService modelMapperService;
	private UserDao userDao;

	@Autowired
	public UserManager(ModelMapperService modelMapperService, UserDao userDao) {

		this.modelMapperService = modelMapperService;
		this.userDao = userDao;

	}

	@Override
	public Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException {

		checkUserExists(deleteUserRequest.getId());

		User user = this.modelMapperService.forRequest().map(deleteUserRequest, User.class);
		this.userDao.deleteById(user.getId());

		return new SuccessResult("User silindi");
	}

	@Override
	public DataResult<List<ListUserDto>> getAll() {

		var result = this.userDao.findAll();

		List<ListUserDto> response = result.stream()
				.map(user -> this.modelMapperService.forDto().map(user, ListUserDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListUserDto>>(response);
	}

	@Override
	public DataResult<List<ListUserDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		var result = this.userDao.findAll(pageable).getContent();
		List<ListUserDto> response = result.stream()
				.map(user -> this.modelMapperService.forDto().map(user, ListUserDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListUserDto>>(response, "Listeleme başarılı.");
	}

	@Override
	public DataResult<List<ListUserDto>> getAllSorted(Direction direction) {

		Sort sort = Sort.by(direction, "id");

		var result = this.userDao.findAll(sort);
		List<ListUserDto> response = result.stream()
				.map(user -> this.modelMapperService.forDto().map(user, ListUserDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListUserDto>>(response);
	}

	@Override
	public boolean checkUserExists(int userId) throws BusinessException {
		if (!this.userDao.existsById(userId)) {
			throw new BusinessException("User için geçersiz Id..!!!!");
		}
		return true;
	}


}
