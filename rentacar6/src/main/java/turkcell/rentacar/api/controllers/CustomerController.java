package turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import turkcell.rentacar.business.abstracts.CustomerService;
import turkcell.rentacar.business.dtos.ListCustomerDto;
import turkcell.rentacar.business.requests.create.CreateCustomerRequest;
import turkcell.rentacar.business.requests.delete.DeleteCustomerRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/getAll")
	DataResult<List<ListCustomerDto>> getAll() {
		return this.customerService.getAll();

	}

	@GetMapping("/get")
	DataResult<ListCustomerDto> getById( int id) {
		return this.customerService.getById(id);		
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCustomerRequest createCustomerRequest)  {
		return this.customerService.add(createCustomerRequest);
	}	
	
	
	@DeleteMapping("/delete/IndividualCustomer")
	public Result delete(@RequestBody @Valid DeleteCustomerRequest deleteCustomerRequest){
		return this.customerService.delete(deleteCustomerRequest);
	}
}
