package turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar.business.abstracts.CarDamageService;
import turkcell.rentacar.business.dtos.ListCarDamageDto;
import turkcell.rentacar.business.requests.create.CreateCarDamageRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarDamageRequest;
import turkcell.rentacar.business.requests.update.UpdateCarDamageRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carDamage")
public class CarDamageController {

	
	private CarDamageService carDamageService;

	@Autowired
	public CarDamageController(CarDamageService carDamageService) {	
		this.carDamageService = carDamageService;
	} 
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest)  {
		return this.carDamageService.add(createCarDamageRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarDamageRequest deleteCarDamageRequest)  {
		return this.carDamageService.delete(deleteCarDamageRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest)  {
		return this.carDamageService.update(updateCarDamageRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<ListCarDamageDto>> getAll() {
		return this.carDamageService.getAll();
	}

	@PostMapping("/getAllPaged")
	public DataResult<List<ListCarDamageDto>> getAllPaged(int pageNo, int pageSize) {
		return this.carDamageService.getAllPaged(pageNo, pageSize);
	}

	@GetMapping("/getCarDamageId")
	public DataResult<ListCarDamageDto> getByCarDamageId(int carDamageId)  {
		return this.carDamageService.getByCarDamageId(carDamageId);
	}
	
	@GetMapping("/getCarDamageByCarId")
	DataResult<List<ListCarDamageDto>> getAllByCarId(int carId){
		return this.carDamageService.getAllByCarId(carId);
	}
	
	
}
