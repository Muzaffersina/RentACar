package turkcell.rentacar.core.adapters.concretes;


import org.springframework.stereotype.Service;

import turkcell.rentacar.business.contants.Messages;
import turkcell.rentacar.business.requests.create.CreatePaymentInfoRequest;
import turkcell.rentacar.core.adapters.abstracts.BankAdapterService;
import turkcell.rentacar.core.concretes.BusinessException;
import turkcell.rentacar.core.externalServices.banks.IsBank;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessResult;

@Service

public class IsBankAdapterManager implements BankAdapterService{

	@Override
	public Result checkIfLimitIsEnough(CreatePaymentInfoRequest createPaymentInfoRequest) {
		IsBank isBank= new IsBank();
		if(isBank.isLimitExists(
				createPaymentInfoRequest.getCardNo(),
				createPaymentInfoRequest.getYear(),
				createPaymentInfoRequest.getMonth(),
				createPaymentInfoRequest.getName(),
				createPaymentInfoRequest.getCVV()))	{
			System.out.println(Messages.ISBANKLIMITENOUGH);;
			return new SuccessResult(Messages.ISBANKLIMITENOUGH);
		}
		else {
			throw new BusinessException(Messages.ISBANKNOTLIMITENOUGH);
		}
	}

}
