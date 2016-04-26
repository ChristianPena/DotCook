package com.dotcook.main;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends BorderPane{
	
	private VBox topPane = null;
	private Node centerPane = null;
	private HBox bottomPane = null;
	
	public Main(){
		
		
		setTopPane();
		setCenterPane();
		setBottomPane();
		
		setTop(getTopPane());
		setCenter(getCenterPane());
		setBottom(getBottomPane());
	}
	
	public VBox getTopPane() {
		return topPane;
	}

	public void setTopPane() {
		
		VBox topPane = new VBox();																	
		
		this.topPane = topPane;
	}
	
	public HBox getBottomPane() {
		return bottomPane;
	}

	public void setBottomPane() {
		
		HBox bottomPane = new HBox();
		
		this.bottomPane = bottomPane;
	}

	public Node getCenterPane() {
		return centerPane;
	}

	public void setCenterPane() {
		Node centerPane = null;
		this.centerPane = centerPane;
	}	

}
