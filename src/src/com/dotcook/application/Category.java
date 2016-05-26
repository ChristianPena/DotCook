package com.dotcook.application;

import com.dotcook.connection.Connection;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Category extends Connection{
	
	private final SimpleIntegerProperty idCategory;
	private final SimpleStringProperty nameCategory;
	private final SimpleStringProperty descriptionCategory;
	private final SimpleIntegerProperty positionCategory;
	
	
	public Category(){
		idCategory          = new SimpleIntegerProperty();
		nameCategory        = new SimpleStringProperty();
		descriptionCategory = new SimpleStringProperty();
		positionCategory    = new SimpleIntegerProperty();
	}


	public int getIdCategory() {
		return idCategory.get();
	}


	public void setIdCategory(int idCategory) {
		this.idCategory.set(idCategory);
	}


	public String getNameCategory() {
		return nameCategory.get();
	}


	public void setNameCategory(String nameCategory) {
		this.nameCategory.set(nameCategory);
	}


	public String getDescriptionCategory() {
		return descriptionCategory.get();
	}


	public void setDescriptionCategory(String descriptionCategory) {
		this.descriptionCategory.set(descriptionCategory);
	}


	public int getPositionCategory() {
		return positionCategory.get();
	}


	public void setPositionCategory(int positionCategory) {
		this.positionCategory.set(positionCategory);
	}

}
