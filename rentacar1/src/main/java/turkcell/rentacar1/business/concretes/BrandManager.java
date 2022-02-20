package turkcell.rentacar1.business.concretes;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar1.business.abstracts.BrandService;
import turkcell.rentacar1.business.dtos.ListBrandDto;
import turkcell.rentacar1.business.requests.create.CreateBrandRequest;
import turkcell.rentacar1.business.requests.delete.DeleteBrandRequest;
import turkcell.rentacar1.business.requests.update.UpdateBrandRequest;
import turkcell.rentacar1.core.concretes.BusinessException;
import turkcell.rentacar1.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar1.dataAccess.abstracts.BrandDao;
import turkcell.rentacar1.entities.concretes.Brand;

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
				.map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public void add(CreateBrandRequest createBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		checkIfBrandName(brand);
		this.brandDao.save(brand);
	}

	@Override
	public void delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		checkBrandId(brand);
		this.brandDao.deleteById(brand.getBrandId());
	}

	@Override
	public void update(UpdateBrandRequest updateBrandReques) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(updateBrandReques, Brand.class);
		checkBrandId(brand);
		this.brandDao.save(brand);
	}

	@Override
	public ListBrandDto getByBrandId(int brandId) throws BusinessException {

		var result = this.brandDao.getByBrandId(brandId);
		if (result != null) {
			ListBrandDto response = this.modelMapperService.forDto().map(result, ListBrandDto.class);
			return response;
		}
		throw new BusinessException("Bu id boş.");
	}

	private boolean checkIfBrandName(Brand brand) throws BusinessException {
		var result = this.brandDao.getByBrandName(brand.getBrandName());
		if (result == null) {
			return true;
		}
		throw new BusinessException("Bu marka daha önce eklenmiştir.");
	}

	private boolean checkBrandId(Brand brand) throws BusinessException {
		var result = this.brandDao.getByBrandId(brand.getBrandId());
		if (result != null) {
			return true;
		}
		throw new BusinessException("Brand için geçersiz Id");
	}

}
