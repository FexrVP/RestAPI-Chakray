package com.mx.PruebaTecnicaChakray.Entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
 * CREATE TABLE USUARIOS_ENCRIPTADOS(
    id_usuarios NVARCHAR2(50),
    email NVARCHAR2(100),
    name NVARCHAR2(100),
    phone NVARCHAR2 (13),
    password NVARCHAR2(500),
    tax_id NVARCHAR2(13)CHECK(LENGTH(tax_id) BETWEEN 12 AND 13),
    created_at DATE,
    id_addresses NUMBER NOT NULL,
    CONSTRAINT fk_usuarios_addresses FOREIGN KEY (id_addresses) REFERENCES ADDRESSES(id_addresses)
)
 */

@Entity
@Table(name = "USUARIOS_ENCRIPTADOS")
public class Usuarios {
	
	@Id
	@Column(name = "id_usuarios")
	private String id;
	
	@Column
	private String email;
	
	@Column
	private String name;
	
	@Column
	private String phone;
	
	@Column
	private String password;
	
	@Column(name = "tax_id")
	private String taxId;
	
	@Column(name = "created_at")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Africa/Nairobi")
	private Date createdAt;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	@JsonManagedReference
	private List<Addresses> addresses;

	public Usuarios() {
	}

	public Usuarios(String id, String email, String name, String phone, String password, String taxId, Date createdAt,
			List<Addresses> addresses) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.taxId = taxId;
		this.createdAt = createdAt;
		this.addresses = addresses;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<Addresses> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Addresses> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", email=" + email + ", name=" + name + ", phone=" + phone + ", password="
				+ password + ", taxId=" + taxId + ", createdAt=" + createdAt + ", addresses=" + addresses + "]";
	}
	
	
	
}
