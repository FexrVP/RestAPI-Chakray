package com.mx.PruebaTecnicaChakray.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * CREATE TABLE ADDRESSES(
    id_addresses NUMBER PRIMARY KEY,
    name NVARCHAR2(50),
    street NVARCHAR2(100),
    country_code NVARCHAR2(10)
)
 */

@Entity
@Table(name = "ADDRESSES")
public class Addresses {
	@Id
	@Column(name = "id_addresses")
	private Long idAddres;
	
	@Column
	private String name;
	
	@Column
	private String street;
	
	@Column(name = "country_code")
	private String countryCode;

	@ManyToOne
	@JoinColumn(name = "id_usuarios")
	@JsonBackReference
	private Usuarios usuario;

	public Addresses() {
	}

	public Addresses(Long idAddres, String name, String street, String countryCode, Usuarios usuario) {
		this.idAddres = idAddres;
		this.name = name;
		this.street = street;
		this.countryCode = countryCode;
		this.usuario = usuario;
	}

	public Long getIdAddres() {
		return idAddres;
	}

	public void setIdAddres(Long idAddres) {
		this.idAddres = idAddres;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Addresses [idAddres=" + idAddres + ", name=" + name + ", street=" + street + ", countryCode="
				+ countryCode + ", usuario=" + usuario + "]";
	}


	
}
