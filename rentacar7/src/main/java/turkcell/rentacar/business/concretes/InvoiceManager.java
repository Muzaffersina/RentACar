package turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.CustomerService;
import turkcell.rentacar.business.abstracts.InvoiceService;
import turkcell.rentacar.business.abstracts.RentalService;
import turkcell.rentacar.business.dtos.ListInvoiceDto;
import turkcell.rentacar.business.requests.create.CreateInvoiceRequest;
import turkcell.rentacar.business.requests.delete.DeleteInvoiceRequest;
import turkcell.rentacar.business.requests.update.UpdateInvoiceRequest;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.InvoiceDao;
import turkcell.rentacar.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService {

	private ModelMapperService modelMapperService;
	private InvoiceDao invoiceDao;
	private CustomerService customerService;
	private RentalService rentalService;

	@Autowired
	public InvoiceManager(ModelMapperService modelMapperService, InvoiceDao invoiceDao, CustomerService customerService,
			RentalService rentalService) {
		super();
		this.modelMapperService = modelMapperService;
		this.invoiceDao = invoiceDao;
		this.customerService = customerService;
		this.rentalService = rentalService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
//iş kuralları

		checkInvoiceRentalId(createInvoiceRequest.getRentalId());
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		invoice.setInvoiceId(0);
		invoice.setTotalPrice(calculatorTotalPrice(createInvoiceRequest.getRentalId()));
		invoice.setCreatedDate(LocalDate.now());
		invoice.setRentalDaysValue(
				(int) calculatorRentalDays(createInvoiceRequest.getRentalDate(), createInvoiceRequest.getReturnDate()));
		// belki rentalDate ve returnData kısmı da setlenebilir.
		this.invoiceDao.save(invoice);

		return new SuccessResult("Invoice. eklendi");
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {

		checkInvoiceExists(deleteInvoiceRequest.getInvoiceId());

		// is kuralları eklenecek
		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		this.invoiceDao.deleteById(invoice.getInvoiceId());

		return new SuccessResult("Invoice. silindi");
	}

	@Override
	public Result Update(UpdateInvoiceRequest updateInvoiceRequest) {

		checkInvoiceExists(updateInvoiceRequest.getInvoiceId());
		return null;
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {

		var result = this.invoiceDao.findAll();
		List<ListInvoiceDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDate(LocalDate startDate, LocalDate endDate) {

		List<Invoice> invoices = this.invoiceDao.getByCreatedDateBetween(startDate, endDate);
		List<ListInvoiceDto> response = invoices.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
				.collect(Collectors.toList());
		/*
		 * for (int i = 0; i < invoices.size(); i++) {
		 * response.get(i).setCustomerId(invoices.get(i).getCustomer().getCustomerId());
		 * }
		 */
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByCustomerId(int customerId) {

		this.customerService.checkCustomerExists(customerId);

		List<Invoice> result = this.invoiceDao.getByCustomer_CustomerId(customerId);
		List<ListInvoiceDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListInvoiceDto>>(response, customerId + " Idli customer getirildi.");
	}

	@Override
	public boolean checkInvoiceExists(int invoiceId) {

		var result = this.invoiceDao.existsById(invoiceId);
		if (result) {
			return true;
		}
		throw new BusinessException("Invoice için geçersiz Id..!!!!");
	}

	@Override
	public double calculatorTotalPrice(int rentalId) {

		var returnedRental = this.rentalService.returnRental(rentalId);

		return returnedRental.getTotalPrice();
	}

	@Override
	public double calculatorRentalDays(LocalDate rentalDate, LocalDate returnDate) {
		int daysBetween = (int) ChronoUnit.DAYS.between(rentalDate, returnDate);
		return daysBetween;
	}

	@Override
	public boolean checkInvoiceRentalId(int rentalId) {

		var result = this.invoiceDao.getAllByRental_RentalId(rentalId);
		if (result != null) {
			throw new BusinessException("Bu rentalId' nin faturalandırılması daha önce yapılmıştır.");
		}
		return true;

	}
}
