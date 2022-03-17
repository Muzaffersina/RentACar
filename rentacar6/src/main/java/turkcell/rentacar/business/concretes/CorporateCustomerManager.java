package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.CorporateCustomerService;
import turkcell.rentacar.business.dtos.ListCorporateCustomerDto;
import turkcell.rentacar.business.requests.create.CreateCorporateCustomerRequest;
import turkcell.rentacar.business.requests.update.UpdateCorporateCustomerRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.CorporateCustomerDao;
import turkcell.rentacar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {
	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest)  {		
		
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		corporateCustomer.setCustomerId(0);
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("corporateCustomer.eklendi");	
		
	}
	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		checkCorporateCustomerExists(updateCorporateCustomerRequest.getId());
		return null;
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		var result = this.corporateCustomerDao.findAll();
		
		List<ListCorporateCustomerDto> response = result.stream()
				.map(corporateCustomer -> this.modelMapperService.forDto().map(corporateCustomer, ListCorporateCustomerDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCorporateCustomerDto>>(response);
	}

	@Override
	public DataResult<ListCorporateCustomerDto> getById(int id) {

		checkCorporateCustomerExists(id);
		
		CorporateCustomer result = this.corporateCustomerDao.getById(id);		
	
		ListCorporateCustomerDto response = this.modelMapperService.forDto().map(result, ListCorporateCustomerDto.class);		
		
		return new SuccessDataResult<ListCorporateCustomerDto>(response);
	}

	@Override
	public boolean checkCorporateCustomerExists(int id) {
		
		var result = this.corporateCustomerDao.existsById(id);
		if (result) {
			return true;
		}
		throw new BusinessException("corporateCustomer için geçersiz Id..!!!!");
	}

	

}
