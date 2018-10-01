package com.dp.petshome.persistence.model;

import java.io.Serializable;

public class User implements Serializable {
	private Integer id;

	private String name;

	private Long asset;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Long getAsset() {
		return asset;
	}

	public void setAsset(Long asset) {
		this.asset = asset;
	}
}