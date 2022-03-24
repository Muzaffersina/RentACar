package turkcell.rentacar.core.adapters.abstracts;

import turkcell.rentacar.business.requests.create.CreatePaymentInfoRequest;
import turkcell.rentacar.core.utilities.results.Result;

public interface BankAdapterService {

	Result checkIfLimitIsEnough(CreatePaymentInfoRequest createPaymentInfoRequest);

}
