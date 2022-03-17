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

import turkcell.rentacar.business.abstracts.BrandService;
import turkcell.rentacar.business.dtos.ListBrandDto;
import turkcell.rentacar.business.requests.create.CreateBrandRequest;
import turkcell.rentacar.business.requests.delete.DeleteBrandRequest;
import turkcell.rentacar.business.requests.update.UpdateBrandRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	private BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {
	
		this.brandService = brandService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListBrandDto>> getAll(){
		return this.brandService.getAll();
	}
	
	@PostMapping("/getAllPaged")
	public DataResult<List<ListBrandDto>> getAllPaged(int pageNo, int pageSize) {
		return this.brandService.getAllPaged(pageNo, pageSize);
	}

	@PostMapping("/getAllSorted")
	public DataResult<List<ListBrandDto>> getAllSorted(Sort.Direction direction) {
		return this.brandService.getAllSorted(direction);
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) {
		return this.brandService.add(createBrandRequest);
	}
	
	@GetMapping("/getBrandId")
	public DataResult<ListBrandDto> getByBrandId(int brandId) {
		return this.brandService.getByBrandId(brandId);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteBrandRequest deleteBrandRequest) {
		return this.brandService.delete(deleteBrandRequest);
	}
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) {
		return this.brandService.update(updateBrandRequest);
	}
	
	
	


}
