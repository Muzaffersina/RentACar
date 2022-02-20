package business.concretes;

import java.util.List;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import business.abstracts.BrandService;
import business.dtos.BrandDto;
import business.dtos.ListBrandDto;
import business.requests.CreateBrandRequest;
import core.utilitys.mapping.ModelMapperService;
import dataAccess.abstracts.BrandDao;
import entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<ListBrandDto> getAll() {
		var result = this.brandDao.findAll();
		List<ListBrandDto> response = result.stream()
				.map(product -> this.modelMapperService.forDto().map(product, ListBrandDto.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public void add(CreateBrandRequest createBrandrequest) throws Exception {
		Brand brand = this.modelMapperService.forRequest().map(createBrandrequest, Brand.class);
		if (checkName(brand)) {
			this.brandDao.save(brand);
		}

	}

	@Override
	public BrandDto getById(int brandId) throws Exception {
		var result = this.brandDao.getByBrandId(brandId);
		if (result == null) {
			throw new Exception("Id boş.");
		}
		BrandDto response = this.modelMapperService.forRequest().map(result, BrandDto.class);
		return response;
	}

	private boolean checkName(Brand brand) throws Exception {
		var result = this.brandDao.getByBrandName(brand.getName());
		if (result == null) {
			return true;
		}
		throw new Exception("Marka mevcut.");
	}

}
