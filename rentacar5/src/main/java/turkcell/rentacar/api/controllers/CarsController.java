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

import turkcell.rentacar.business.abstracts.CarService;
import turkcell.rentacar.business.dtos.ListCarDto;
import turkcell.rentacar.business.requests.create.CreateCarRequest;
import turkcell.rentacar.business.requests.delete.DeleteCarRequest;
import turkcell.rentacar.business.requests.update.UpdateCarRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

	private CarService carService;

	@Autowired
	public CarsController(CarService carService) {

		this.carService = carService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) throws BusinessException {
		return this.carService.add(createCarRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarRequest deleteCarRequest) throws BusinessException {
		return this.carService.delete(deleteCarRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) throws BusinessException {
		return this.carService.update(updateCarRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<ListCarDto>> getAll() {
		return carService.getAll();
	}

	@PostMapping("/getAllPaged")
	public DataResult<List<ListCarDto>> getAllPaged(int pageNo, int pageSize) {
		return this.carService.getAllPaged(pageNo, pageSize);
	}

	@PostMapping("/getAllSorted")
	public DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction) {
		return this.carService.getAllSorted(direction);
	}

	@PostMapping("/getByDailyPriceLessThanEqual")
	public DataResult<List<ListCarDto>> getByDailyPriceLessThanEqual(double dailyPrice) {
		return this.carService.getByDailyPriceLessThanEqual(dailyPrice);
	}

	@GetMapping("/getCarId")
	public DataResult<ListCarDto> getByCarId(int carId) throws BusinessException {
		return this.carService.getByCarId(carId);
	}

}