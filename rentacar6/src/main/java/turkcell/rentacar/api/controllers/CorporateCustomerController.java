package turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import turkcell.rentacar.business.abstracts.CorporateCustomerService;
import turkcell.rentacar.business.dtos.ListCorporateCustomerDto;
import turkcell.rentacar.business.requests.create.CreateCorporateCustomerRequest;
import turkcell.rentacar.business.requests.update.UpdateCorporateCustomerRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/CorporateCustomers")
public class CorporateCustomerController {

	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomerController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}

	@GetMapping("/getAll")
	DataResult<List<ListCorporateCustomerDto>> getAll() {
		return this.corporateCustomerService.getAll();

	}

	@GetMapping("/get/CorporateCustomerId")
	DataResult<ListCorporateCustomerDto> getById( int id) {
		return this.corporateCustomerService.getById(id);		
	}

	@PostMapping("/add/CorporateCustomer")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest)  {
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
	}
	
	@PutMapping("/update/CorporateCustomer")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest)  {
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}
}
