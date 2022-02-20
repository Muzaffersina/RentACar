package turkcell.rentacar1.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.CarService;
import turkcell.rentacar1.business.dtos.ListCarDto;
import turkcell.rentacar1.business.requests.create.CreateCarRequest;
import turkcell.rentacar1.business.requests.delete.DeleteCarRequest;
import turkcell.rentacar1.business.requests.update.UpdateCarRequest;
import turkcell.rentacar1.core.concretes.BusinessException;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
	
	private CarService carService;

	@Autowired
	public CarsController(CarService carService) {
	
		this.carService = carService;
	}
	
	@GetMapping("/getall")
	public List<ListCarDto> getAll(){
		return carService.getAll();
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateCarRequest createCarRequest) throws BusinessException{
		this.carService.add(createCarRequest);
	}
	
	@GetMapping("/getcarid")
	public ListCarDto getByCarId(int carId) throws BusinessException{
		return this.carService.getByCarId(carId);
	}
	@PostMapping("/delete")
	public void delete(@RequestBody DeleteCarRequest deleteCarRequest) throws BusinessException{
		this.carService.delete(deleteCarRequest);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody UpdateCarRequest updateCarRequest) throws BusinessException{
		this.carService.update(updateCarRequest);
	}


}