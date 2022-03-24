package turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar.business.abstracts.PaymentInfoService;
import turkcell.rentacar.business.dtos.ListPaymentInfoDto;
import turkcell.rentacar.business.requests.create.CreatePaymentInfoRequest;
import turkcell.rentacar.business.requests.delete.DeletePaymentInfoRequest;
import turkcell.rentacar.business.requests.update.UpdatePaymentInfoRequest;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/paymentInfo")
public class PaymentInfosController {
	
	
	private PaymentInfoService paymentInfoService;

	@Autowired
	public PaymentInfosController(PaymentInfoService paymentInfoService) {	
		this.paymentInfoService = paymentInfoService;
	} 
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreatePaymentInfoRequest createPaymentInfoRequest)  {
		return this.paymentInfoService.add(createPaymentInfoRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeletePaymentInfoRequest deletePaymentInfoRequest)  {
		return this.paymentInfoService.delete(deletePaymentInfoRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdatePaymentInfoRequest updatePaymentInfoRequest)  {
		return this.paymentInfoService.update(updatePaymentInfoRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<ListPaymentInfoDto>> getAll() {
		return this.paymentInfoService.getAll();
	}
}
