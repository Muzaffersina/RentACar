package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.CustomerService;
import turkcell.rentacar.business.dtos.ListCustomerDto;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.dataAccess.abstracts.CustomerDao;
import turkcell.rentacar.entities.concretes.Customer;

@Service

public class CustomerManager implements CustomerService {

	private CustomerDao customerDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {
		super();
		this.customerDao = customerDao;
		this.modelMapperService = modelMapperService;
	}
	@Override
	public DataResult<List<ListCustomerDto>> getAll() {
		
		var result = this.customerDao.findAll();
		
		List<ListCustomerDto> response = result.stream()
				.map(customer -> this.modelMapperService.forDto().map(customer, ListCustomerDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCustomerDto>>(response);
	}

	@Override
	public DataResult<ListCustomerDto> getById(int id) {
		
		checkCustomerExists(id);
		
		Customer result = this.customerDao.getById(id);			
		ListCustomerDto response = this.modelMapperService.forDto().map(result, ListCustomerDto.class);	
		
		return new SuccessDataResult<ListCustomerDto>(response);
	}

	@Override
	public boolean checkCustomerExists(int id) {
		
		var result = this.customerDao.existsById(id);
		if (result) {
			return true;
		}
		throw new BusinessException("Customer için geçersiz Id..!!!!");
	}	
	
}
