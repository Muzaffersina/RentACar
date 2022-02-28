package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListBrandDto;
import turkcell.rentacar.business.requests.create.CreateBrandRequest;
import turkcell.rentacar.business.requests.delete.DeleteBrandRequest;
import turkcell.rentacar.business.requests.update.UpdateBrandRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface BrandService {
		Result add(CreateBrandRequest createBrandRequest) throws BusinessException;
		Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException;
		Result update(UpdateBrandRequest updateBrandReques) throws BusinessException;
		DataResult<List<ListBrandDto>> getAll();
		DataResult<ListBrandDto> getByBrandId(int brandId) throws BusinessException;
		
		

}
