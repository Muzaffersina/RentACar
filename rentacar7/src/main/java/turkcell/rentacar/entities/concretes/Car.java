package turkcell.rentacar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cars")
public class Car {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="car_id")
	private int carId;
	
	@Column(name="daily_price")
	private double dailyPrice;
	
	@Column(name="model_year")
	private int modelYear;
	
	@Column(name = "car_total_km")
	private int carTotalKm;
	
	@Column(name = "daily_km")
	private int dailyKm;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="brand_id")
	private Brand brand;
	
	@ManyToOne
	@JoinColumn(name="color_id")
	private Color color;
	
	@OneToMany(mappedBy = "car") //
	private List<CarMaintenance> carMaintenances;
	
	@OneToMany(mappedBy = "car")
	private List<Rental> rents;
	
	@OneToMany(mappedBy = "car")
	private List<CarDamage> carDamages;
}

// 17-Arabaya ait hasarlar girilebilmelidir. (Id,CarId,HasarBilgisi) ++++++++++

// BUNDA SANIRIM SADECE BU BILGISI OLAN ARACLAR CARMAINTENANCE EKLENEBILIR OLMALI SANKII ??  


// 18- Pos Servisi ekleyiniz. Servis olumsuz döndüğünde kiralama   ----->>>
//				gerçekleşmemelidir.
//		payments , rental , addiServi , ınvoice

//   	Yapılan ödemeleri bir tabloda tutunuz
//	    Bir ödeme en fazla bir kere alınmalıdır


// POS SERVİCE LERİ FARKLI PARAMETRELER SAHİP OLABİLİR.. 
// 2-3 TANE FAKE POS SERVİCİ OLUSTURULMALI
// 1. şirket =  birinci parametresi k kartı no , cvv, .. gibi bilgiler..
// 2. sirket farklı sıralamayla veyaaa obje istiyor falan 
//      ADAPTOR PATERN 


