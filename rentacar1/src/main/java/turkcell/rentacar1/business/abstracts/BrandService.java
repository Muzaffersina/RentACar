package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.ListBrandDto;
import turkcell.rentacar1.business.requests.create.CreateBrandRequest;
import turkcell.rentacar1.business.requests.delete.DeleteBrandRequest;
import turkcell.rentacar1.business.requests.update.UpdateBrandRequest;
import turkcell.rentacar1.core.concretes.BusinessException;

public interface BrandService {
	
		List<ListBrandDto> getAll();
		void add(CreateBrandRequest createBrandRequest) throws BusinessException;
		void delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException;
		void update(UpdateBrandRequest updateBrandReques) throws BusinessException;
		ListBrandDto getByBrandId(int brandId) throws BusinessException;
		
		

}
