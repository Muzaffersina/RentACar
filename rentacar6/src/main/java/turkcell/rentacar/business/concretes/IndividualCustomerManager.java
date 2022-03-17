package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.IndividualCustomerService;
import turkcell.rentacar.business.dtos.ListIndividualCustomerDto;
import turkcell.rentacar.business.requests.create.CreateIndividualCustomerRequest;
import turkcell.rentacar.business.requests.update.UpdateIndividualCustomerRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.IndividualCustomerDao;
import turkcell.rentacar.entities.concretes.IndividualCustomer;
@Service
public class IndividualCustomerManager implements IndividualCustomerService{
	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	
	
	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao ,ModelMapperService modelMapperService) {		
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;		
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest)  {	
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("corporateCustomer.eklendi");	
	}


	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest)  {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		
		var result = this.individualCustomerDao.findAll();
		
		List<ListIndividualCustomerDto> response = result.stream()
				.map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer, ListIndividualCustomerDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListIndividualCustomerDto>>(response);
	}

	@Override
	public DataResult<ListIndividualCustomerDto> getById(int id) {
		
		checkIndividualCustomerExists(id);
		
		IndividualCustomer result = this.individualCustomerDao.getById(id);		
		
		ListIndividualCustomerDto response = this.modelMapperService.forDto().map(result, ListIndividualCustomerDto.class);		
		
		return new SuccessDataResult<ListIndividualCustomerDto>(response);
		
	}

	@Override
	public boolean checkIndividualCustomerExists(int id) {
		var result = this.individualCustomerDao.existsById(id);
		if (result) {
			return true;
		}
		throw new BusinessException("individualCustomeriçin geçersiz Id..!!!!");
	}	
}


