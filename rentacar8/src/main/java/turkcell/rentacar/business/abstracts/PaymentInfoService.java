package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListPaymentInfoDto;
import turkcell.rentacar.business.requests.create.CreatePaymentInfoRequest;
import turkcell.rentacar.business.requests.delete.DeletePaymentInfoRequest;
import turkcell.rentacar.business.requests.update.UpdatePaymentInfoRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface PaymentInfoService {
	
	Result add(CreatePaymentInfoRequest createPaymentInfoRequest);
	Result delete(DeletePaymentInfoRequest deletePaymentInfoRequest);
	Result update(UpdatePaymentInfoRequest updatePaymentInfoRequest);
	
	void save(CreatePaymentInfoRequest paymentInfo);
	
	DataResult<List<ListPaymentInfoDto>> getAll();
	
}
