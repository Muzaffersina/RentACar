package turkcell.rentacar.core.adapters.concretes;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.contants.Messages;
import turkcell.rentacar.business.requests.create.CreatePaymentInfoRequest;
import turkcell.rentacar.core.adapters.abstracts.BankAdapterService;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.externalServices.banks.GarantiBank;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessResult;

@Service
@Primary
public class GarantiBankAdapterManager implements BankAdapterService{

	@Override
	public Result checkIfLimitIsEnough(CreatePaymentInfoRequest createPaymentInfoRequest) {
		
		GarantiBank garantiBank = new GarantiBank();
		if(garantiBank.isLimitExists(
				createPaymentInfoRequest.getCardNo(), 
				createPaymentInfoRequest.getYear(),
				createPaymentInfoRequest.getMonth(),
				createPaymentInfoRequest.getCVV(),
				createPaymentInfoRequest.getName()))	{
			System.out.println(Messages.GARANTIBANKLIMITENOUGH);
			return new SuccessResult(Messages.GARANTIBANKLIMITENOUGH);
		}
		else {
			throw new BusinessException(Messages.GARANTIBANKNOTLIMITENOUGH);
		}
	}

}
