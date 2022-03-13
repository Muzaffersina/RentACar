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

import turkcell.rentacar.business.abstracts.CityService;
import turkcell.rentacar.business.dtos.ListCityDto;
import turkcell.rentacar.business.requests.create.CreateCityRequest;
import turkcell.rentacar.business.requests.delete.DeleteCityRequest;
import turkcell.rentacar.business.requests.update.UpdateCityRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cities")
public class CityController {

	private CityService cityService;

	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws BusinessException {
		return this.cityService.add(createCityRequest);

	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCityRequest deleteCityRequest) throws BusinessException {
		return this.cityService.delete(deleteCityRequest);

	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCityRequest updateAdditionalRequest) throws BusinessException {
		return this.cityService.update(updateAdditionalRequest);

	}

	@GetMapping("/getAll")
	public DataResult<List<ListCityDto>> getAll() {
		return this.cityService.getAll();
	}

	@PostMapping("/getAllPaged")
	public DataResult<List<ListCityDto>> getAllPaged(int pageNo, int pageSize) {
		return this.cityService.getAllPaged(pageNo, pageSize);
	}

	@PostMapping("/getAllSorted")
	public DataResult<List<ListCityDto>> getAllSorted(Sort.Direction direction) {
		return this.cityService.getAllSorted(direction);
	}

	@GetMapping("/getCityId")
	public DataResult<ListCityDto> getById(int cityId) throws BusinessException {
		return this.cityService.getById(cityId);
	}

}
