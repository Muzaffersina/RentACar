package api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.abstracts.ColorService;
import business.dtos.ColorDto;
import business.dtos.ListColorDto;
import business.requests.CreateColorRequest;


@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	
	private ColorService colorService;
	
	@Autowired                                                  // proje tarayıp otomatik new ' leme.. 
	public ColorsController(ColorService colorService) {
	
		this.colorService = colorService;
	}
	
	@GetMapping("/getall")
	public List<ListColorDto> getAll(){
		return this.colorService.getAll();
		
	}
	@PostMapping("/add")
	public void add(@RequestBody CreateColorRequest createColorRequest)  throws Exception  {
		this.colorService.add(createColorRequest);
			
	}
	@GetMapping("/getbyidcolor")
	public ColorDto getById(@RequestParam int colorId) throws Exception {
		return this.colorService.getById(colorId);
				
	}
}
