package com.dotcook.main;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends BorderPane{
	
	private HBox topPane = null;
	private Node centerPane = null;
	private VBox bottomPane = null;
	
//  Top Pane objects
	private ToolBar toolbar;
	private ImageView viewLogo;
	private Image logo;
	
	public Main(){		
		
		setTopPane();
		setCenterPane();
		setBottomPane();
		
		setTop(getTopPane());
		setCenter(getCenterPane());
		setBottom(getBottomPane());
	}
	
	public HBox getTopPane() {
		return topPane;
	}

	public void setTopPane() {
		
		HBox topPane = new HBox(2);		
		
		setToolbar();
		setViewLogo();
		
		topPane.getChildren().addAll(getToolbar(),getViewLogo());
		
		this.topPane = topPane;
	}
		
	public VBox getBottomPane() {
		return bottomPane;
	}

	public void setBottomPane() {
		
		VBox bottomPane = new VBox();
		
		this.bottomPane = bottomPane;
	}

	public Node getCenterPane() {
		return centerPane;
	}

	public void setCenterPane() {
		Node centerPane = null;
		this.centerPane = centerPane;
	}

	public ToolBar getToolbar() {
		return toolbar;
	}

	public void setToolbar() {
		
		ToolBar toolbar = new ToolBar(
			new Button("New"),
			new Button("Open"),
			new Button("Save")
		);
		
		toolbar.setPrefWidth(getMaxWidth());
		
		this.toolbar = toolbar;
	}

	public ImageView getViewLogo() {
		return viewLogo;
	}

	public void setViewLogo() {
		
		ImageView viewLogo = new ImageView();
		
		setLogo();
		viewLogo.setImage(getLogo());
		
		this.viewLogo = viewLogo;
		
	}

	public Image getLogo() {
		return logo;
	}

	public void setLogo() {
		Image logo = new Image(Main.class.getResourceAsStream("/com/dotcook/resources/logo/logo.png"));
		this.logo = logo;
	}	

}
