package turkcell.rentacar.business.abstracts;

import java.util.List;

import turkcell.rentacar.business.dtos.ListPaymentDto;
import turkcell.rentacar.business.requests.create.CreatePaymentRequest;
import turkcell.rentacar.business.requests.delete.DeletePaymentRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

public interface PaymentService {

	Result add(CreatePaymentRequest createPaymentRequest);
	Result delete(DeletePaymentRequest deletePaymentRequest);

	DataResult<List<ListPaymentDto>> getAll();
	DataResult<List<ListPaymentDto>> getAllPaged(int pageNo, int pageSize);
	DataResult<ListPaymentDto> getByRentalId(int rentalId);

	boolean checkPaymentExists(int paymentId);
	double calculatorTotalPrice(int rentalId) ;
	boolean checkPaymentRentalId(int rentalId);
}
