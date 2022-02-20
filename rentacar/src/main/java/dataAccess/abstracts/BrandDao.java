package dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.concretes.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand , Integer>{
	
	Brand getByBrandId(int brandId);
	Brand getByBrandName(String brandName);
	
}
