package turkcell.rentacar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar.business.abstracts.InvoiceService;
import turkcell.rentacar.business.dtos.ListInvoiceDto;
import turkcell.rentacar.business.requests.create.CreateInvoiceRequest;
import turkcell.rentacar.business.requests.delete.DeleteInvoiceRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

	private InvoiceService invoiceService;

	@Autowired
	public InvoiceController(InvoiceService invoiceService) {

		this.invoiceService = invoiceService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) {
		return this.invoiceService.add(createInvoiceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteInvoiceRequest deleteInvoiceRequest) {
		return this.invoiceService.delete(deleteInvoiceRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<ListInvoiceDto>> getAll() {
		return this.invoiceService.getAll();
	}
	
	@GetMapping("/getCustomerId")
	public DataResult<List<ListInvoiceDto>> getByCustomerId(int customerId)  {
		return this.invoiceService.getByCustomerId(customerId);
	}
	
	@GetMapping("/getByDate")
	public DataResult<List<ListInvoiceDto>> getByDate(LocalDate rentalDate , LocalDate returnDate) {
		return this.invoiceService.getByDate(rentalDate,returnDate);
	}

}
