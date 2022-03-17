package turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.CustomerService;
import turkcell.rentacar.business.abstracts.InvoiceService;
import turkcell.rentacar.business.dtos.ListInvoiceDto;
import turkcell.rentacar.business.requests.create.CreateInvoiceRequest;
import turkcell.rentacar.business.requests.delete.DeleteInvoiceRequest;
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

	@Autowired
	public InvoiceManager(ModelMapperService modelMapperService, InvoiceDao invoiceDao,
			CustomerService customerService) {
		super();
		this.modelMapperService = modelMapperService;
		this.invoiceDao = invoiceDao;
		this.customerService = customerService ;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setInvoiceId(0);
		;
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
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		var result = this.invoiceDao.findAll();		
		List<ListInvoiceDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByDate(LocalDate rentalDate , LocalDate returnDate) {
		
		
	
		
		return null;
	}

	@Override
	public DataResult<List<ListInvoiceDto>> getByCustomerId(int customerId) {
		
		this.customerService.checkCustomerExists(customerId);
		
		List<Invoice> result = this.invoiceDao.getByCustomer_CustomerId(customerId);
		List<ListInvoiceDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, ListInvoiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListInvoiceDto>>(response, customerId+" Idli customer getirildi.");		
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
	public void saveInvoice(int customerId, int rentalId, LocalDate rentalDate, LocalDate returnDate,
			double totalPrice) {


		CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
		int invoiceNumber = (int) (Math.random()*10000);
		long rentalDaysValue = ChronoUnit.DAYS.between(rentalDate ,  returnDate);
		
		createInvoiceRequest.setInvoiceNo(invoiceNumber);
		createInvoiceRequest.setRentalDaysValue(rentalDaysValue);
		createInvoiceRequest.setCustomerId(customerId);
		createInvoiceRequest.setCreatedDate(LocalDate.now());	
		createInvoiceRequest.setRentalDate(rentalDate);
		createInvoiceRequest.setRentalId(rentalId);
		createInvoiceRequest.setReturnDate(returnDate);
		createInvoiceRequest.setTotalPrice(totalPrice);	
		
		add(createInvoiceRequest);
	}

}
