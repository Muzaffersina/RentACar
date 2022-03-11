package turkcell.rentacar.business.abstracts;

import turkcell.rentacar.core.concretes.BusinessException;

public interface CustomerService {
	boolean checkCustomerId(int customerId) throws BusinessException;
}
