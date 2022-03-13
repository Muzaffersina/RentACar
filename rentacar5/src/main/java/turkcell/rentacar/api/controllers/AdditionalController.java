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

import turkcell.rentacar.business.abstracts.AdditionalService;
import turkcell.rentacar.business.dtos.ListAdditionalDto;
import turkcell.rentacar.business.requests.create.CreateAdditionalRequest;
import turkcell.rentacar.business.requests.delete.DeleteAdditionalRequest;
import turkcell.rentacar.business.requests.update.UpdateAdditionalRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalServiceController")
public class AdditionalController {
	
	private AdditionalService additionalService;

	@Autowired
	public AdditionalController(AdditionalService additionalService) {		
		this.additionalService = additionalService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateAdditionalRequest createAdditionalRequest)
			throws BusinessException {
		return this.additionalService.add(createAdditionalRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteAdditionalRequest deleteAdditionalRequest)
			throws BusinessException {
		return this.additionalService.delete(deleteAdditionalRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateAdditionalRequest updateAdditionalRequest )
			throws BusinessException {
		return this.additionalService.update(updateAdditionalRequest);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListAdditionalDto>> getAll() {
		return this.additionalService.getAll();
	}
	@PostMapping("/getAllPaged")
	public DataResult<List<ListAdditionalDto>> getAllPaged(int pageNo, int pageSize) {
		return this.additionalService.getAllPaged(pageNo, pageSize);
	}

	@PostMapping("/getAllSorted")
	public DataResult<List<ListAdditionalDto>> getAllSorted(Sort.Direction direction) {
		return this.additionalService.getAllSorted(direction);
	}

	@GetMapping("/getAdditionalServiceId")
	public DataResult<ListAdditionalDto> getByBrandId(int additionalId) throws BusinessException{
		return this.additionalService.getById(additionalId);
	}
	

}
