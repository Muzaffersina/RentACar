package turkcell.rentacar1.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import turkcell.rentacar1.business.abstracts.ColorService;
import turkcell.rentacar1.business.dtos.ListColorDto;
import turkcell.rentacar1.business.requests.create.CreateColorRequest;
import turkcell.rentacar1.business.requests.delete.DeleteColorRequest;
import turkcell.rentacar1.business.requests.update.UpdateColorRequest;
import turkcell.rentacar1.core.concretes.BusinessException;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	
	private ColorService colorService;

	@Autowired
	public ColorsController(ColorService colorService) {
		
		this.colorService = colorService;
	}
	
	@GetMapping("/getall")
	public List<ListColorDto> getAll(){
		return colorService.getAll();
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException{
		this.colorService.add(createColorRequest);
	}
	
	@GetMapping("/getcolorid")
	public ListColorDto getByColorId(int colorId) throws BusinessException{
		return this.colorService.getByColorId(colorId);
	}
	
	@PostMapping("/delete")
	public void delete(@RequestBody DeleteColorRequest deleteColorRequest ) throws BusinessException{
		this.colorService.delete(deleteColorRequest);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody UpdateColorRequest updateColorRequst) throws BusinessException{
		this.colorService.update(updateColorRequst);
	}

	

}
