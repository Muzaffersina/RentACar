package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.BrandService;
import turkcell.rentacar.business.dtos.ListBrandDto;
import turkcell.rentacar.business.requests.create.CreateBrandRequest;
import turkcell.rentacar.business.requests.delete.DeleteBrandRequest;
import turkcell.rentacar.business.requests.update.UpdateBrandRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.ErrorDataResult;
import turkcell.rentacar.core.utilities.results.ErrorResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.BrandDao;
import turkcell.rentacar.entities.concretes.Brand;

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
	public DataResult<List<ListBrandDto>> getAll() {
		var result = this.brandDao.findAll();
		if (result.isEmpty()) {
			return new ErrorDataResult<List<ListBrandDto>>("Liste Bos");
		}
		List<ListBrandDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListBrandDto>>(response);
	}
	
	@Override
	public DataResult<List<ListBrandDto>> getAllPaged(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Brand> result = this.brandDao.findAll(pageable).getContent();
		if (result.isEmpty()) {
			return new ErrorDataResult<List<ListBrandDto>>("Liste Bos");
		}
		List<ListBrandDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListBrandDto>>(response, "Listeleme başarılı.");
	}

	@Override
	public DataResult<List<ListBrandDto>> getAllSorted(Direction direction) {
		Sort sort = Sort.by(direction, "brandId");
		List<Brand> result = this.brandDao.findAll(sort);
		if (result.isEmpty()) {
			return new ErrorDataResult<List<ListBrandDto>>("Liste Bos");
		}
		List<ListBrandDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListBrandDto>>(response);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		if(checkBrandName(brand.getBrandName())) {
			this.brandDao.save(brand);
			return new SuccessResult("Brand.eklendi");
		}
		return new ErrorResult("Brand.eklenemedi");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		if(checkBrandId(brand.getBrandId())){
			this.brandDao.deleteById(brand.getBrandId());
			return new SuccessResult("Brand.silindi");
		}
		return new ErrorResult("Brand.silinmedi");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandReques) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(updateBrandReques, Brand.class);
		if(checkBrandId(brand.getBrandId())) {
			this.brandDao.save(brand);
			return new SuccessResult("Brand.guncellendi");
		}
		return new ErrorResult("Brand.guncellenemedi");
	}

	@Override
	public DataResult<ListBrandDto> getByBrandId(int brandId) throws BusinessException {

		var result = this.brandDao.getByBrandId(brandId);
		checkBrandId(brandId);
		ListBrandDto response = this.modelMapperService.forDto().map(result, ListBrandDto.class);
		return new SuccessDataResult<ListBrandDto>(response,"Idsi "+brandId+ " olan marka getirildi.");
		
	}
	@Override
	public boolean checkBrandName(String brandName) throws BusinessException {
		var result = this.brandDao.getByBrandName(brandName);
		if (result == null) {
			return true;
		}
		throw new BusinessException("Bu marka daha önce eklenmiştir.");
	}

	@Override
	public boolean checkBrandId(int brandId) throws BusinessException {
		var result = this.brandDao.getByBrandId(brandId);
		if (result != null) {
			return true;
		}
		throw new BusinessException("Brand için geçersiz Id");
	}	

}
