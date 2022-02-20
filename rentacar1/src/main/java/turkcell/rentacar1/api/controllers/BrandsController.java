package turkcell.rentacar1.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.BrandService;
import turkcell.rentacar1.business.dtos.ListBrandDto;
import turkcell.rentacar1.business.requests.create.CreateBrandRequest;
import turkcell.rentacar1.business.requests.delete.DeleteBrandRequest;
import turkcell.rentacar1.business.requests.update.UpdateBrandRequest;
import turkcell.rentacar1.core.concretes.BusinessException;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	private BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {
	
		this.brandService = brandService;
	}
	
	@GetMapping("/getall")
	public List<ListBrandDto> getAll(){
		return brandService.getAll();
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateBrandRequest createBrandRequest) throws BusinessException{
		this.brandService.add(createBrandRequest);
	}
	
	@GetMapping("/getbrandid")
	public ListBrandDto getByBrandId(int brandId) throws BusinessException{
		return this.brandService.getByBrandId(brandId);
	}
	@PostMapping("/delete")
	public void delete(@RequestBody DeleteBrandRequest deleteBrandRequest) throws BusinessException{
		this.brandService.delete(deleteBrandRequest);
	}
	@PostMapping("/update")
	public void update(@RequestBody UpdateBrandRequest updateBrandRequest) throws BusinessException{
		this.brandService.update(updateBrandRequest);
	}

}
