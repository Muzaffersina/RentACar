package turkcell.rentacar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.CustomerService;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.dataAccess.abstracts.CustomerDao;
@Service
public class CustomerManager implements CustomerService{
	private CustomerDao customerDao;
	
	
	@Autowired
	public CustomerManager(CustomerDao customerDao) {
		
		this.customerDao = customerDao;
	}

	@Override
	public boolean checkCustomerId(int customerId) throws BusinessException {
		if (!this.customerDao.existsById(customerId)) {
			throw new BusinessException("Customer için geçersiz Id....!");
		}
		return true;
	}
}


