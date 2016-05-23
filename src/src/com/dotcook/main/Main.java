package com.dotcook.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;

import com.dotcook.application.Application;
import com.dotcook.application.ToolbarApplication;
import com.dotcook.popup.system.SystemInfoController;
import com.dotcook.resources.Properties;
import com.dotcook.session.Session;
import com.dotcook.user.User;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	private Button btnSave;
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
	private Session session;
	
//  Bottom Pane Objects
	private Label statusMessage;
	private Label hostname;
	private Label servername;
	private Label username;
	
	public Main(){		
		
	}
	
	public void init(String idUser){
		
		steps = FXCollections.observableArrayList();
		
		Session session = new Session();
		session.createSession(idUser);
		setSession(session);
		
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
		
		initBottomLabels();
		
		bottomPane.getItems().addAll(getStatusMessage(), new Separator(), getServername(), new Separator(), 
									 getHostname(), new Separator(), getUsername());		
		
		this.bottomPane = bottomPane;
	}

	private void initBottomLabels() {
		
		Double sizeLabels = 0.0;
		Label statusMessage = new Label();
		Label servername = new Label(getSession().getServername());
		Label hostname = new Label(getSession().getHostname());		
		Label username = new Label(getSession().getUsername());		
		
		servername.setMinWidth(100);
		hostname.setMinWidth(100);
		username.setMinWidth(100);		
		
		sizeLabels = hostname.getMinWidth() 
				   + servername.getMinWidth() 
				   + username.getMinWidth() 
				   + 60;
		
		statusMessage.setMinWidth(getSizeWidth() - sizeLabels);
		
		setStatusMessage(statusMessage);
		setHostname(hostname);
		setServername(servername);
		setUsername(username);
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
			
			method = classController.getSuperclass().getDeclaredMethod("setUser", paramUser);			
			method.invoke(controller, getUser());
			
			method = classController.getSuperclass().getDeclaredMethod("setApp", paramApp);
			method.invoke(controller, getApp());
			
			method = classController.getSuperclass().getDeclaredMethod("setApps", paramApps);
			method.invoke(controller, getApps());
			
			method = classController.getSuperclass().getDeclaredMethod("setProp", paramProp);
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
		
		ToolBar toolbar = new ToolBar(getBtnSave(),getBtnBack(),getBtnFinish(),getBtnCancel(),
									  getBtnPrint(),getBtnSearch(),getBtnHelp(),getBtnSys());		
		
		this.toolbar = toolbar;
	}

	private void initButtonsToolbar() {
		
		Button btnSave = new Button(getProp().getPropertyValue("toolbar.save"));
		btnSave.setGraphic(getImageButton("save"));
		setBtnSave(btnSave);
		
		Button btnBack = new Button(getProp().getPropertyValue("toolbar.back"));
		btnBack.setGraphic(getImageButton("back"));
		setBtnBack(btnBack);
		
		Button btnFinish = new Button(getProp().getPropertyValue("toolbar.finish"));
		btnFinish.setGraphic(getImageButton("finish"));
		setBtnFinish(btnFinish);
		
		Button btnCancel = new Button(getProp().getPropertyValue("toolbar.cancel"));
		btnCancel.setGraphic(getImageButton("cancel"));
		setBtnCancel(btnCancel);
		
		Button btnPrint = new Button(getProp().getPropertyValue("toolbar.print"));
		btnPrint.setGraphic(getImageButton("print"));
		setBtnPrint(btnPrint);
		
		Button btnSearch = new Button(getProp().getPropertyValue("toolbar.search"));
		btnSearch.setGraphic(getImageButton("search"));
		setBtnSearch(btnSearch);
		
		Button btnHelp = new Button(getProp().getPropertyValue("toolbar.help"));
		btnHelp.setGraphic(getImageButton("help"));
		setBtnHelp(btnHelp);
		
		Button btnSys = new Button(getProp().getPropertyValue("toolbar.sys"));
		btnSys.setGraphic(getImageButton("system"));
		setBtnSys(btnSys);
				
	}
	
	private ImageView getImageButton(String button) {
		
		String imgSource = "/com/dotcook/resources/icons/" + button + "_32x32.png";
		
		Image img = new Image(getClass().getResourceAsStream(imgSource));
		
		ImageView imgView = new ImageView(img);
		
		return imgView;
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
					String execMethod = "";
					
					if((btnPressed.getText()).equals(getBtnSave().getText()))
						execMethod = "actionSave";					
					
					if((btnPressed.getText()).equals(getBtnCancel().getText()))
						execMethod = "actionCancel";
					
					if((btnPressed.getText()).equals(getBtnPrint().getText()))
						execMethod = "actionPrint";
					
					if((btnPressed.getText()).equals(getBtnSearch().getText()))
						execMethod = "actionSearch";
															
					if(!execMethod.equals("")){					
						method = classController.getDeclaredMethod(execMethod, paramEvent);			
						method.invoke(controller, e);
					} else {
						
						if((btnPressed.getText()).equals(getBtnBack().getText()))
							actionBack();
						
						if((btnPressed.getText()).equals(getBtnFinish().getText()))
							actionFinish();
						
						if((btnPressed.getText()).equals(getBtnHelp().getText()))
							showHelp();
						
						if((btnPressed.getText()).equals(getBtnSys().getText()))
							showSystemInfo();
						
					}
				
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
			
			case "SAVE":
				if(tool.isEnabled()==true)
					getBtnSave().setDisable(false);
				else
					getBtnSave().setDisable(true);
				break;
			
			case "BACK":
				if(tool.isEnabled()==true)
					getBtnBack().setDisable(false);
				else
					getBtnBack().setDisable(true);
				break;
//			case "FINISH":
//				if(tool.isEnabled()==true)
//					getBtnFinish().setDisable(false);
//				else
//					getBtnFinish().setDisable(true);
//				break;
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
//			case "HELP":
//				if(tool.isEnabled()==true)
//					getBtnHelp().setDisable(false);
//				else
//					getBtnHelp().setDisable(true);
//				break;
//			case "SYSTEM":
//				if(tool.isEnabled()==true)
//					getBtnSys().setDisable(false);
//				else
//					getBtnSys().setDisable(true);
//				break;
			
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
		viewLogo.setFitWidth(196.15);
		viewLogo.setFitHeight(80);
		
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
	
	public Button getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(Button btnSave) {
		this.btnSave = btnSave;
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

	public Label getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(Label statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public void setStatusMessage(String txt, char typ) {
		
		String imgSource = "/com/dotcook/resources/icons/";
		
		switch(typ){
		
		case 'S': 
			imgSource = imgSource + "success.png";
			break;
		
		case 'W':
			imgSource = imgSource + "warning.png";
			break;
			
		case 'E':
			imgSource = imgSource + "error.png";
			break;
		
		case 'I':
			imgSource = imgSource + "information.png";
			break;
			
		default:			
			imgSource = imgSource + "null.png";
			break;
		
		}
		
		if(!(getStatusMessage().getText()).equals("")){
			
			FadeTransition fadeOut = new FadeTransition(Duration.millis(175));
			fadeOut.setNode(getStatusMessage());
			fadeOut.setFromValue(1.0);
		    fadeOut.setToValue(0.0);
		    fadeOut.setCycleCount(1);
		    fadeOut.setAutoReverse(false);
		    fadeOut.playFromStart();
			
		}
		
		Image img = new Image(getClass().getResourceAsStream(imgSource));
		getStatusMessage().setGraphic(new ImageView(img));
		getStatusMessage().setText(txt);
		
		FadeTransition fadeIn = new FadeTransition(Duration.millis(175));
		fadeIn.setNode(getStatusMessage());
		fadeIn.setFromValue(0.0);
	    fadeIn.setToValue(1.0);
	    fadeIn.setCycleCount(1);
	    fadeIn.setAutoReverse(false);
	    fadeIn.playFromStart();
		
		System.out.println("(" + typ + ") " + txt);		
		
	}

	public Label getHostname() {
		return hostname;
	}

	public void setHostname(Label hostname) {
		this.hostname = hostname;
	}

	public Label getServername() {
		return servername;
	}

	public void setServername(Label servername) {
		this.servername = servername;
	}

	public Label getUsername() {
		return username;
	}

	public void setUsername(Label username) {
		this.username = username;
	}
	
	public void exit(){
		
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle(getProp().getPropertyValue("main.exit.title"));
		confirm.setHeaderText(getProp().getPropertyValue("main.exit.title"));
		confirm.setContentText(getProp().getPropertyValue("main.title.content"));
		
		Optional<ButtonType> result = confirm.showAndWait();
		if(result.get() == ButtonType.OK)
			setStatusMessage("Saliendo del Sistema...", 'I');
			getSession().closeSession();
			System.exit(0);
		
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public void actionFinish(){
		
		if(getApp().getIdApplication() == 1){
			exit();
		} else {
			removeStep();
			setCenterPane(getLastStep());			
		}
		
	}
	
	public void actionBack(){
		setStatusMessage("Back button Pressed", 'S');
		removeStep();
		setCenterPane(getLastStep());
	}
	
	public void showHelp(){
		setStatusMessage("Help button Pressed", 'S');		
	}
	
	public void showSystemInfo(){
		setStatusMessage("Recuperando información del sistema", 'I');
		try {			
			String fxmlSource = "/com/dotcook/popup/system/SystemInfo.fxml";
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlSource));
			SystemInfoController controller = new SystemInfoController();
			controller.setProp(getProp());
			controller.setSession(getSession());
			fxmlLoader.setController(controller);
			Parent popupSystemInfo = (Parent) fxmlLoader.load();
			Scene scene = new Scene(popupSystemInfo);
			Stage stage = new Stage();
			stage.setTitle(getProp().getPropertyValue("popup.system.title"));
			stage.setScene(scene);
			stage.setResizable(false);
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/dotcook/resources/icons/system_16x16.png"))); 
			stage.show();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}
}
