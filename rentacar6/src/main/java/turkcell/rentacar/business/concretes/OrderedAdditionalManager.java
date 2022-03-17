package turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import turkcell.rentacar.business.abstracts.AdditionalService;
import turkcell.rentacar.business.abstracts.OrderedAdditionalService;
import turkcell.rentacar.business.dtos.ListOrderedAdditionalDto;
import turkcell.rentacar.business.requests.create.CreateOrderedAdditionalRequest;
import turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import turkcell.rentacar.core.utilities.results.DataResult;
import turkcell.rentacar.core.utilities.results.Result;
import turkcell.rentacar.core.utilities.results.SuccessDataResult;
import turkcell.rentacar.core.utilities.results.SuccessResult;
import turkcell.rentacar.dataAccess.abstracts.OrderedAdditionalDao;
import turkcell.rentacar.entities.concretes.OrderedAdditional;

@Service
public class OrderedAdditionalManager implements OrderedAdditionalService {

	private ModelMapperService modelMapperService;
	private OrderedAdditionalDao orderedAdditionalDao;
	private AdditionalService additionalService;

	@Autowired
	public OrderedAdditionalManager(ModelMapperService modelMapperService, OrderedAdditionalDao orderedAdditionalDao,
			AdditionalService additionalService) {
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalDao = orderedAdditionalDao;
		this.additionalService = additionalService;
	}

	@Override
	public Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) {

		OrderedAdditional orderedAdditional = this.modelMapperService.forRequest().map(createOrderedAdditionalRequest,
				OrderedAdditional.class);
		orderedAdditional.setOrderedId(0);		
		this.orderedAdditionalDao.save(orderedAdditional);

		return new SuccessResult("OrderedAdditional. eklendi");
	}

	@Override
	public DataResult<List<ListOrderedAdditionalDto>> getAll() {

		var result = this.orderedAdditionalDao.findAll();

		List<ListOrderedAdditionalDto> response = result.stream().map(orderedAdditional -> this.modelMapperService
				.forDto().map(orderedAdditional, ListOrderedAdditionalDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListOrderedAdditionalDto>>(response);
	}

	@Override
	public void saveOrderedAdditional(List<Integer> additionalIds, int rentalId) {
		
		CreateOrderedAdditionalRequest createOrderedAdditionalRequest = new CreateOrderedAdditionalRequest();		
		for (int additionalId : additionalIds) {
			if(additionalId != 0) {
				createOrderedAdditionalRequest.setAdditionalId(additionalId);
				createOrderedAdditionalRequest.setRentalId(rentalId);
				add(createOrderedAdditionalRequest);
			}			
		}
	}

	@Override
	public List<ListOrderedAdditionalDto> getByAdditional_AdditionalId(int additionalId) {
		
		this.additionalService.checkAdditionalExists(additionalId);
		List<OrderedAdditional> result = this.orderedAdditionalDao.getByAdditional_AdditionalId(additionalId);
		List<ListOrderedAdditionalDto> response = result.stream()
				.map(orderedAdditional -> this.modelMapperService.forDto().map(orderedAdditional, ListOrderedAdditionalDto.class))
				.collect(Collectors.toList());

		return response;
	}

}
