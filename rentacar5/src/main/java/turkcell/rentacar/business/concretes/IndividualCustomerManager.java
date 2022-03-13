package turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.IndividualCustomerService;
import turkcell.rentacar.business.abstracts.UserService;
import turkcell.rentacar.business.requests.create.CreateIndividualCustomerRequest;
import turkcell.rentacar.business.requests.update.UpdateColorRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.IndividualCustomerDao;
import turkcell.rentacar.entities.concretes.IndividualCustomer;
@Service
public class IndividualCustomerManager implements IndividualCustomerService{
	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	private UserService userService;
	
	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao ,ModelMapperService modelMapperService
			,UserService userService) {		
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
		this.userService = userService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		
		checkIndividualCustomerExists(createIndividualCustomerRequest.getName());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("corporateCustomer.eklendi");	
	}


	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public boolean checkIndividualCustomerExists(String individualCustomerString) throws BusinessException {		
		return true;
	}	
}


