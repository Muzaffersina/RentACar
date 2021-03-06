package entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brands")
@Entity

public class Brand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // otomatik artmasi.
	@Column(name = "brand_id")
	private int id;
	
	@Column(name = "brand_name")
	private String name;

}
