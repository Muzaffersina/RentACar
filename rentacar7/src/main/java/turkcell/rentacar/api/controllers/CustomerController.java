package turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar.business.abstracts.CustomerService;
import turkcell.rentacar.business.dtos.ListCustomerDto;
import turkcell.rentacar.core.utilities.results.DataResult;

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
}
