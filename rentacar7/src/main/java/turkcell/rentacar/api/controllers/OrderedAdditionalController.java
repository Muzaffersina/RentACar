package turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar.business.abstracts.OrderedAdditionalService;
import turkcell.rentacar.business.dtos.ListOrderedAdditionalDto;
import turkcell.rentacar.business.requests.create.CreateOrderedAdditionalRequest;
import turkcell.rentacar.business.requests.delete.DeleteOrderedAdditionalRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalController")
public class OrderedAdditionalController {
	
	private OrderedAdditionalService orderedAdditionalService;

	@Autowired
	public OrderedAdditionalController(OrderedAdditionalService orderedAdditionalService) {
		
		this.orderedAdditionalService = orderedAdditionalService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalRequest createOrderedAdditionalRequest) {
		return this.orderedAdditionalService.add(createOrderedAdditionalRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) {
		return this.orderedAdditionalService.delete(deleteOrderedAdditionalRequest);
	}

	
	@GetMapping("/getAll")
	public DataResult<List<ListOrderedAdditionalDto>> getAll() {
		return this.orderedAdditionalService.getAll();
	}
	
	@GetMapping("/getByAdditional_AdditionalId")
	public List<ListOrderedAdditionalDto> getByAdditional_AdditionalId(int additionalId)  {
		return this.orderedAdditionalService.getByAdditional_AdditionalId(additionalId);
	}

}
