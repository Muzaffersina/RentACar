package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.PaymentService;
import turkcell.rentacar.business.abstracts.RentalService;
import turkcell.rentacar.business.dtos.ListPaymentDto;
import turkcell.rentacar.business.requests.create.CreatePaymentRequest;
import turkcell.rentacar.business.requests.delete.DeletePaymentRequest;
import turkcell.rentacar.core.adapters.abstracts.BankAdapterService;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import turkcell.rentacar.entities.concretes.Payment;

@Service

public class PaymentManager implements PaymentService {

	private ModelMapperService modelMapperService;
	private PaymentDao paymentDao;
	private RentalService rentalService;
	private BankAdapterService bankAdapterService;

	@Autowired
	public PaymentManager(ModelMapperService modelMapperService, PaymentDao paymentDao, RentalService rentalService,
			BankAdapterService bankAdapterService) {
		this.modelMapperService = modelMapperService;
		this.paymentDao = paymentDao;
		this.rentalService = rentalService;
		this.bankAdapterService = bankAdapterService;
		
	}
	// iş kuraları sonra eklenecek

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {

		this.rentalService.checkRentCarExists(createPaymentRequest.getRentalId());
		checkPaymentRentalId(createPaymentRequest.getRentalId());
		
		//Banka seçiminde problem var
		this.bankAdapterService.checkIfLimitIsEnough(createPaymentRequest.getCardNo(), createPaymentRequest.getYear(), 
				createPaymentRequest.getMounth(), createPaymentRequest.getCVV(),
				calculatorTotalPrice(createPaymentRequest.getRentalId()));
		
			
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setTotalPayment(calculatorTotalPrice(createPaymentRequest.getRentalId()));
		payment.setPaymentId(0);
		this.paymentDao.save(payment);

		return new SuccessResult(payment.getPaymentId() + " payment.eklendi");
	}

	@Override
	public Result delete(DeletePaymentRequest deletePaymentRequest) {

		checkPaymentExists(deletePaymentRequest.getPaymentId());

		Payment payment = this.modelMapperService.forRequest().map(deletePaymentRequest, Payment.class);
		this.paymentDao.deleteById(payment.getPaymentId());

		return new SuccessResult(payment.getPaymentId() + " " + "payment.silindi");
	}

	@Override
	public DataResult<List<ListPaymentDto>> getAll() {

		List<Payment> result = this.paymentDao.findAll();
		List<ListPaymentDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListPaymentDto>>(response, "Listeleme başarılı.");
	}

	@Override
	public DataResult<List<ListPaymentDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<Payment> result = this.paymentDao.findAll(pageable).getContent();
		List<ListPaymentDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, ListPaymentDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListPaymentDto>>(response, "Listeme başaralı bir şekilde gerceklesti");
	}

	@Override
	public DataResult<ListPaymentDto> getByRentalId(int rentalId) {

		this.rentalService.checkRentCarExists(rentalId);

		var result = this.paymentDao.getAllByRental_RentalId(rentalId);
		ListPaymentDto response = this.modelMapperService.forDto().map(result, ListPaymentDto.class);
		
		return new SuccessDataResult<ListPaymentDto>(response);
	}

	@Override
	public boolean checkPaymentExists(int paymentId) {

		var result = this.paymentDao.existsById(paymentId);
		if (result) {
			return true;
		}
		throw new BusinessException("Payment için geçersiz Id..!!!!");
	}

	@Override
	public double calculatorTotalPrice(int rentalId) {

		var returnedRental = this.rentalService.returnRental(rentalId);

		return returnedRental.getTotalPrice();
	}

	@Override
	public boolean checkPaymentRentalId(int rentalId) {

		var result = this.paymentDao.getAllByRental_RentalId(rentalId);
		if (result != null) {
			throw new BusinessException("Bu rentalId' nin ödemesi daha önce yapılmıştır.");
		}
		return true;
	}	
}
