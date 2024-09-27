package com.resumejpa.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "members")
public class Member {

	/** ID */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 名前 */
	@NotEmpty(message="{err.msg.required}")
	private String name;

	/** 誕生日 */
	@NotNull(message="{err.msg.required}")
	@DateTimeFormat(pattern = "yyyy-MM-dd") //inputタグに日付を反映させるために形式を整える
	private LocalDate birth;

	/** メアド */
	@NotEmpty(message="{err.msg.required}")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
