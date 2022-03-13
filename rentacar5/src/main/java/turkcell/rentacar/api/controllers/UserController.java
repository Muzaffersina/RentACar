package turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar.business.abstracts.CorporateCustomerService;
import turkcell.rentacar.business.abstracts.IndividualCustomerService;
import turkcell.rentacar.business.abstracts.UserService;
import turkcell.rentacar.business.dtos.ListUserDto;
import turkcell.rentacar.business.requests.create.CreateCorporateCustomerRequest;
import turkcell.rentacar.business.requests.create.CreateIndividualCustomerRequest;
import turkcell.rentacar.business.requests.delete.DeleteUserRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/userController")
public class UserController {
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService  corporateCustomerService;
	private UserService userService;
	
	@Autowired
	public UserController(IndividualCustomerService individualCustomerService,
			CorporateCustomerService corporateCustomerService,
			UserService userService) {
		
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.userService = userService;
	}
	
	@PostMapping("/add/individualCustomer")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest)
			throws BusinessException {
		return this.individualCustomerService.add(createIndividualCustomerRequest);		
	}
	
	@PostMapping("/add/corporateCustomerService")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest)
			throws BusinessException {
		return this.corporateCustomerService.add(createCorporateCustomerRequest);		
	}
	
	@DeleteMapping("/deleteUser")
	public Result delete(@RequestBody @Valid DeleteUserRequest deleteUserRequest) throws BusinessException {
		return this.userService.delete(deleteUserRequest);
	}
	
	@GetMapping("/getAllUser")
	public DataResult<List<ListUserDto>> getAll() {
		return this.userService.getAll();
	}
	
	@PostMapping("/getAllPaged")
	public DataResult<List<ListUserDto>> getAllPaged(int pageNo, int pageSize) {
		return this.userService.getAllPaged(pageNo, pageSize);
	}

	@PostMapping("/getAllSorted")
	public DataResult<List<ListUserDto>> getAllSorted(Sort.Direction direction) {
		return this.userService.getAllSorted(direction);
	}	
	
}
