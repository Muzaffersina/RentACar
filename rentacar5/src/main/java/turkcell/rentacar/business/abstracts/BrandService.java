package turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

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
	DataResult<List<ListBrandDto>> getAllPaged(int pageNo , int pageSize);
	DataResult<List<ListBrandDto>> getAllSorted(Sort.Direction direction);

	DataResult<ListBrandDto> getByBrandId(int brandId) throws BusinessException;

	boolean checkBrandExists(int brandId) throws BusinessException;
	boolean checkBrandNameExists(String brandName) throws BusinessException;
}
