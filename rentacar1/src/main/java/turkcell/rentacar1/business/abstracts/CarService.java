package turkcell.rentacar1.business.abstracts;

import java.util.List;

import turkcell.rentacar1.business.dtos.ListCarDto;
import turkcell.rentacar1.business.requests.create.CreateCarRequest;
import turkcell.rentacar1.business.requests.delete.DeleteCarRequest;
import turkcell.rentacar1.business.requests.update.UpdateCarRequest;
import turkcell.rentacar1.core.concretes.BusinessException;

public interface CarService {

	List<ListCarDto> getAll();

	void add(CreateCarRequest createCarRequest) throws BusinessException;

	void delete(DeleteCarRequest deleteCarRequest) throws BusinessException;

	void update(UpdateCarRequest updateCarRequest) throws BusinessException;

	ListCarDto getByCarId(int carId) throws BusinessException;
}
