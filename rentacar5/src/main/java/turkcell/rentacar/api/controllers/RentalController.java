package turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar.business.abstracts.RentalService;
import turkcell.rentacar.business.dtos.GetListRentDto;
import turkcell.rentacar.business.dtos.ListRentDto;
import turkcell.rentacar.business.requests.create.CreateRentalRequest;
import turkcell.rentacar.business.requests.delete.DeleteRentalRequest;
import turkcell.rentacar.business.requests.update.UpdateRentalRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rents")
public class RentalController {
	
	private RentalService rentService;

	@Autowired
	public RentalController(RentalService rentService) {
		this.rentService = rentService;
	}	

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateRentalRequest createRentRequest) throws BusinessException{
		return this.rentService.add(createRentRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteRentalRequest deleteRentRequest) throws BusinessException{
		return this.rentService.delete(deleteRentRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateRentalRequest updateRentRequest) throws BusinessException{
		return this.rentService.update(updateRentRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListRentDto>> getAll() {
		return this.rentService.getAll();
	}
	
	@PostMapping("/getAllPaged")
	public DataResult<List<ListRentDto>> getAllPaged(int pageNo, int pageSize) {
		return this.rentService.getAllPaged(pageNo, pageSize);
	}

	@PostMapping("/getAllSorted")
	public DataResult<List<ListRentDto>> getAllSorted(Sort.Direction direction) {
		return this.rentService.getAllSorted(direction);
	}

	@GetMapping("/getRentId")
	public DataResult<GetListRentDto> getByCarMaintenanceId(int carMaintenanceId) throws BusinessException {
		return this.rentService.getByRentId(carMaintenanceId);
	}

	@GetMapping("/getByCar_CarId")
	public List<ListRentDto> getByCar_CarId(int carId) throws BusinessException {
		return this.rentService.getByCar_CarId(carId);
	}
 	
}
