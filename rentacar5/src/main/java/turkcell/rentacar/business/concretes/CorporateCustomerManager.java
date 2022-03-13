package turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.CorporateCustomerService;
import turkcell.rentacar.business.requests.create.CreateCorporateCustomerRequest;
import turkcell.rentacar.business.requests.update.UpdateCorporateCustomerRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.Result;
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
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		
		checkCorporateCustomerExists(createCorporateCustomerRequest.getName());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("corporateCustomer.eklendi");	
		
	}
	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkCorporateCustomerExists(String corporateCustomerName) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

}
