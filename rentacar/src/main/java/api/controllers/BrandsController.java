package api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.abstracts.BrandService;
import business.dtos.BrandDto;
import business.dtos.ListBrandDto;
import business.requests.CreateBrandRequest;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {

	private BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}

	@GetMapping("/getallbrands")
	public List<ListBrandDto> getAll() {
		return this.brandService.getAll();
	}

	@PostMapping("/addbrand")
	public void add(@RequestBody CreateBrandRequest createBrandRequest) throws Exception {
		this.brandService.add(createBrandRequest);

	}

	@GetMapping("/getbyidbrand")
	public BrandDto getById(@RequestParam int brandId) throws Exception {
		return this.brandService.getById(brandId);
	}

}
