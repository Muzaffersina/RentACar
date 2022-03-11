package turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar.business.abstracts.OrderedAdditionalService;
import turkcell.rentacar.business.dtos.ListOrderedAdditionalDto;
import turkcell.rentacar.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/orderedAdditionalControllers")
public class OrderedAdditionalController {
	private OrderedAdditionalService orderedAdditionalService;

	public OrderedAdditionalController(OrderedAdditionalService orderedAdditionalService) {		
		this.orderedAdditionalService = orderedAdditionalService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListOrderedAdditionalDto>> getAll(){
		return this.orderedAdditionalService.getAll();
	}
}
