package business.abstracts;

import java.util.List;

import business.dtos.BrandDto;
import business.dtos.ListBrandDto;
import business.requests.CreateBrandRequest;



public interface BrandService {
	
	List<ListBrandDto> getAll();
	void add(CreateBrandRequest createBrandRequest) throws Exception;	
	BrandDto getById(int brandId) throws Exception;
	

}
