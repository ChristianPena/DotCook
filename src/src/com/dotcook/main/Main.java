package com.dotcook.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.Method;

import com.dotcook.application.Application;
import com.dotcook.application.ToolbarApplication;
import com.dotcook.resources.Properties;
import com.dotcook.user.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Main extends BorderPane{
	
	private HBox topPane = null;
	private Node centerPane = null;
	private ToolBar bottomPane = null;
	
//  Top Pane objects
	private VBox leftTopPane;
	private ToolBar toolbar;
	private ToolBar appTitleBar;
	private ImageView viewLogo;
	private Image logo;
	private String appTitle;
	private Label labelAppTitle;
	
//  Other objects
	private Double sizeHeight;
	private Double sizeWidth;
	
//  Main ToolBar buttons
	private Button btnBack;
	private Button btnFinish;
	private Button btnCancel;
	private Button btnPrint;
	private Button btnSearch;
	private Button btnHelp;
	private Button btnSys;
	
	private User user;
	private Application app;
	private ObservableList<Application> apps;
	private ObservableList<Application> steps;
	private Properties prop;
	private FXMLLoader fxmlLoader = null;
	
	public Main(){		
		
	}
	
	public void init(String idUser){
		
		steps = FXCollections.observableArrayList();
		
		getScreenSize();
		setProp();
		setUser(idUser);
		setApps();
		setApp(1);
		addStep(getApp());
		
		setTopPane();
		setCenterPane(getApp());
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
		
		setViewLogo();
		setLeftTopPane();
		
		topPane.getChildren().addAll(getLeftTopPane(),getViewLogo());
		
		this.topPane = topPane;
	}
		
	public ToolBar getBottomPane() {
		return bottomPane;
	}

	public void setBottomPane() {
		
		ToolBar bottomPane = new ToolBar();
		
		bottomPane.setMinWidth(getSizeWidth());
		
		bottomPane.getItems().add(new Label("Status"));		
		
		this.bottomPane = bottomPane;
	}

	public Node getCenterPane() {
		return centerPane;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setCenterPane(Application app) {
		
		Node centerPane = null;
		
		Method method = null;
		
		Class[] paramUser   = new Class[1];
		Class[] paramApp    = new Class[1];
		Class[] paramApps   = new Class[1];
		Class[] paramProp   = new Class[1];
		
		paramUser[0] = User.class;
		paramApps[0] = ObservableList.class;
		paramApp[0]  = Application.class;
		paramProp[0] = Properties.class;
		
		try{
			
			this.setCenter(null);
			
			setApp(app);
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(app.getSource()));			
			
			Class classController = Class.forName(app.getController());			
			
			Object controller = classController.newInstance();
			
			method = classController.getDeclaredMethod("setUser", paramUser);			
			method.invoke(controller, getUser());
			
			method = classController.getDeclaredMethod("setApp", paramApp);
			method.invoke(controller, getApp());
			
			method = classController.getDeclaredMethod("setApps", paramApps);
			method.invoke(controller, getApps());
			
			method = classController.getDeclaredMethod("setProp", paramProp);
			method.invoke(controller, getProp());
			
			fxmlLoader.setController(controller);
			
			centerPane = (BorderPane) fxmlLoader.load();
									
			getLabelAppTitle().setText(app.getDescription());			
			
			setFxmlLoader(fxmlLoader);
			
			setActionToolbar();
			
			setEnabledToolbar();
			
			if(app.getIdApplication() != 1){
				addStep(getApp());
			}
						
			this.setCenter(centerPane);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		this.centerPane = centerPane;
	}

	public ToolBar getToolbar() {
		return toolbar;
	}

	public void setToolbar() {
		
		initButtonsToolbar();
		
		setActionToolbar();
		
		setEnabledToolbar();
		
		ToolBar toolbar = new ToolBar(getBtnBack(),getBtnFinish(),getBtnCancel(),getBtnPrint(),
									  getBtnSearch(),getBtnHelp(),getBtnSys());		
		
		this.toolbar = toolbar;
	}

	private void initButtonsToolbar() {
		
		Button btnBack = new Button(getProp().getPropertyValue("toolbar.back"));		
		setBtnBack(btnBack);
		
		Button btnFinish = new Button(getProp().getPropertyValue("toolbar.finish"));
		setBtnFinish(btnFinish);
		
		Button btnCancel = new Button(getProp().getPropertyValue("toolbar.cancel"));
		setBtnCancel(btnCancel);
		
		Button btnPrint = new Button(getProp().getPropertyValue("toolbar.print"));
		setBtnPrint(btnPrint);
		
		Button btnSearch = new Button(getProp().getPropertyValue("toolbar.search"));
		setBtnSearch(btnSearch);
		
		Button btnHelp = new Button(getProp().getPropertyValue("toolbar.help"));
		setBtnHelp(btnHelp);
		
		Button btnSys = new Button(getProp().getPropertyValue("toolbar.sys"));
		setBtnSys(btnSys);
				
	}
	
	public void setActionToolbar(){
		
		EventHandler<ActionEvent> eHandler = new EventHandler<ActionEvent>(){
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void handle(ActionEvent e){
				
				try{
					
					Class classController = Class.forName(getApp().getController());
					Method method = null;
					
					Object controller = getFxmlLoader().getController();
					
					Class[] paramEvent   = new Class[1];
					paramEvent[0] = ActionEvent.class;
					
					Button btnPressed = (Button) e.getSource();
					String execMethod = null;
					
					if((btnPressed.getText()).equals(getBtnBack().getText()))
						execMethod = "actionBack";
					
					if((btnPressed.getText()).equals(getBtnFinish().getText()))
						execMethod = "actionFinish";
					
					if((btnPressed.getText()).equals(getBtnCancel().getText()))
						execMethod = "actionCancel";
					
					if((btnPressed.getText()).equals(getBtnPrint().getText()))
						execMethod = "actionPrint";
					
					if((btnPressed.getText()).equals(getBtnSearch().getText()))
						execMethod = "actionSearch";
					
					if((btnPressed.getText()).equals(getBtnHelp().getText()))
						execMethod = "actionHelp";
					
					if((btnPressed.getText()).equals(getBtnSys().getText()))
						execMethod = "actionSys";					
					
					method = classController.getDeclaredMethod(execMethod, paramEvent);			
					method.invoke(controller, e);					
				
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		};
		
		getBtnBack().setOnAction(eHandler);
		getBtnFinish().setOnAction(eHandler);
		getBtnCancel().setOnAction(eHandler);
		getBtnPrint().setOnAction(eHandler);
		getBtnSearch().setOnAction(eHandler);
		getBtnHelp().setOnAction(eHandler);
		getBtnSys().setOnAction(eHandler);
		
	}
	
	public void setEnabledToolbar(){
		
		for(ToolbarApplication tool : getApp().getTool()){
			
			switch(tool.getIdButton()){
			
			case "BACK":
				if(tool.isEnabled()==true)
					getBtnBack().setDisable(false);
				else
					getBtnBack().setDisable(true);
				break;
			case "FINISH":
				if(tool.isEnabled()==true)
					getBtnFinish().setDisable(false);
				else
					getBtnFinish().setDisable(true);
				break;
			case "CANCEL":
				if(tool.isEnabled()==true)
					getBtnCancel().setDisable(false);
				else
					getBtnCancel().setDisable(true);
				break;
			case "PRINT":
				if(tool.isEnabled()==true)
					getBtnPrint().setDisable(false);
				else
					getBtnPrint().setDisable(true);
				break;
			case "SEARCH":
				if(tool.isEnabled()==true)
					getBtnSearch().setDisable(false);
				else
					getBtnSearch().setDisable(true);
				break;
			case "HELP":
				if(tool.isEnabled()==true)
					getBtnHelp().setDisable(false);
				else
					getBtnHelp().setDisable(true);
				break;
			case "SYSTEM":
				if(tool.isEnabled()==true)
					getBtnSys().setDisable(false);
				else
					getBtnSys().setDisable(true);
				break;
			
			}			
		}
	}

	public ImageView getViewLogo() {
		return viewLogo;
	}

	public void setViewLogo() {
		
		ImageView viewLogo = new ImageView();
		
		setLogo();
		viewLogo.setImage(getLogo());
		viewLogo.setFitWidth(159.38);
		viewLogo.setFitHeight(65);
		
		this.viewLogo = viewLogo;
		
	}

	public Image getLogo() {
		return logo;
	}

	public void setLogo() {
		Image logo = new Image(Main.class.getResourceAsStream("/com/dotcook/resources/logo/logo.png"));
		this.logo = logo;
	}
	
	public void getScreenSize(){
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setSizeHeight(screenSize.getHeight());
		setSizeWidth(screenSize.getWidth());
		
	}

	public Double getSizeHeight() {
		return sizeHeight;
	}

	public void setSizeHeight(Double sizeHeight) {
		this.sizeHeight = sizeHeight;
	}

	public Double getSizeWidth() {
		return sizeWidth;
	}

	public void setSizeWidth(Double sizeWidth) {
		this.sizeWidth = sizeWidth;
	}

	public VBox getLeftTopPane() {
		return leftTopPane;
	}

	public void setLeftTopPane() {
		VBox leftTopPane = new VBox();
		
		leftTopPane.setMinWidth(getSizeWidth() - getViewLogo().getFitWidth());
		
		setToolbar();
		setAppTitleBar();
		
		leftTopPane.getChildren().addAll(getToolbar(),getAppTitleBar());
		
		this.leftTopPane = leftTopPane;
	}

	public ToolBar getAppTitleBar() {
		return appTitleBar;
	}

	public void setAppTitleBar() {
		
		ToolBar appTitleBar = new ToolBar();
		
		setLabelAppTitle();
		
		setAppTitle("App.Title");
		
		setAppTitle(getAppTitle());
		
		getLabelAppTitle().setFont(new Font("Arial",20));
		
		appTitleBar.getItems().add(getLabelAppTitle());
		
		this.appTitleBar = appTitleBar;
	}

	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUser(String username){
		
		User user = new User();
		user.getUser(username);
		
		this.user = user;
		
	}
	
	public Application getApp(){
		return app;
	}
	
	public void setApp(Application app){
		this.app = app;
	}
	
	public void setApp(int idApp){
		
		for(Application app : getApps()){
			if(app.getIdApplication() == idApp){
				setApp(app);
			}
		}
		
	}

	public ObservableList<Application> getApps() {
		return apps;
	}

	public void setApps(ObservableList<Application> apps) {
		this.apps = apps;
	}
	
	public void setApps() {
		
		ObservableList<Application> apps = null;
		Application app = new Application();
		
		app.setApplications(getUser().getIdUser());
		apps = app.getApplications();
		
		this.apps = apps;
	}

	public Label getLabelAppTitle() {
		return labelAppTitle;
	}
	
	public void setLabelAppTitle() {
		Label labelAppTitle = new Label();
		this.labelAppTitle = labelAppTitle;
	}

	public void setLabelAppTitle(Label labelAppTitle) {
		this.labelAppTitle = labelAppTitle;
	}
	
	public void setLabelAppTitle(String appTitle) {
		getLabelAppTitle().setText(appTitle);
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp() {
		Properties prop = new Properties();
		prop.setProp();
		this.prop = prop;
	}

	public Button getBtnBack() {
		return btnBack;
	}

	public void setBtnBack(Button btnBack) {
		this.btnBack = btnBack;
	}

	public Button getBtnFinish() {
		return btnFinish;
	}

	public void setBtnFinish(Button btnFinish) {
		this.btnFinish = btnFinish;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public Button getBtnPrint() {
		return btnPrint;
	}

	public void setBtnPrint(Button btnPrint) {
		this.btnPrint = btnPrint;
	}

	public Button getBtnSearch() {
		return btnSearch;
	}

	public void setBtnSearch(Button btnSearch) {
		this.btnSearch = btnSearch;
	}

	public Button getBtnHelp() {
		return btnHelp;
	}

	public void setBtnHelp(Button btnHelp) {
		this.btnHelp = btnHelp;
	}

	public Button getBtnSys() {
		return btnSys;
	}

	public void setBtnSys(Button btnSys) {
		this.btnSys = btnSys;
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public void setFxmlLoader(FXMLLoader fxmlLoader) {
		this.fxmlLoader = fxmlLoader;
	}

	public ObservableList<Application> getSteps() {
		return steps;
	}

	public void setSteps(ObservableList<Application> steps) {
		this.steps = steps;
	}
	
	public void addStep(Application step){		
		getSteps().add(step);		
	}
	
	public void removeStep(){
		
		int size = getSteps().size() - 1;
		getSteps().remove(size);
		System.out.println("Size: " + getSteps().size());
	}
	
	public Application getLastStep(){
		Application lastStep = new Application();
		int idx = getSteps().size() - 1;
		lastStep = getSteps().get(idx);
		
		return lastStep;
	}
	

}
