package com.mx.PruebaTecnicaChakray.Entity;

import java.sql.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/*
 * CREATE TABLE USUARIOS_ENCRIPTADOS(
    id_usuarios NVARCHAR2(50),
    email NVARCHAR2(100),
    name NVARCHAR2(100),
    app NVARCHAR2(100),
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
	private UUID idUsuarios;
	
	@Column
	private String email;
	
	@Column
	private String name;
	
	@Column
	private String app;
	
	@Column
	private String phone;
	
	@Column
	private String password;
	
	@Column(name = "tax_id")
	private String taxId;
	
	@Column(name = "created_at")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Africa/Nairobi")
	private Date createAt;
	
	@ManyToMany
	@JoinColumn(name = "id_addresses")
	@JsonManagedReference
	private Addresses addres;

	public Usuarios() {
	}

	public Usuarios(UUID idUsuarios, String email, String name, String app, String phone, String password, String taxId,
			Date createAt, Addresses addres) {
		this.idUsuarios = idUsuarios;
		this.email = email;
		this.name = name;
		this.app = app;
		this.phone = phone;
		this.password = password;
		this.taxId = taxId;
		this.createAt = createAt;
		this.addres = addres;
	}

	public UUID getIdUsuarios() {
		return idUsuarios;
	}

	public void setIdUsuarios(UUID idUsuarios) {
		this.idUsuarios = idUsuarios;
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

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Addresses getAddres() {
		return addres;
	}

	public void setAddres(Addresses addres) {
		this.addres = addres;
	}

	@Override
	public String toString() {
		return "Usuarios [idUsuarios=" + idUsuarios + ", email=" + email + ", name=" + name + ", app=" + app
				+ ", phone=" + phone + ", password=" + password + ", taxId=" + taxId + ", createAt=" + createAt
				+ ", addres=" + addres + "]";
	}
	
	
	
	
	
}
