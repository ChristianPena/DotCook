package com.dotcook.popup.system;

import java.net.URL;
import java.util.ResourceBundle;

import com.dotcook.resources.Properties;
import com.dotcook.session.Session;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SystemInfoController implements Initializable{
	
	@FXML ImageView imgView;
	@FXML Label popupTitle;
	@FXML TextField username;
	@FXML TextField systTime;
	@FXML TextField startSession;
	@FXML TextField servername;	
	@FXML TextField serverSO;
	@FXML TextField hostname;
	@FXML TextField hostSO;
	@FXML TextField hostIP;
	@FXML TextField hostMac;
	@FXML TextField DBSyst;
	@FXML TextField DBStringConnection;
	@FXML TextField DBSchema;
	@FXML TextField DBDriver;
	@FXML TextField DBDriverVersion;
	
	private SystemInfo syst;
	private Properties prop;
	private Session session;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setPopupTitle();
		setImgView();
		setSyst(getSession());
		fillSystData();
	}
	
	private void fillSystData() {
		
		username.setText(getSyst().getUsername());
		systTime.setText(getSyst().getSystTime());
		startSession.setText(getSyst().getStartSession());
		servername.setText(getSyst().getServername());	
		serverSO.setText(getSyst().getServerSO());
		hostname.setText(getSyst().getHostname());
		hostSO.setText(getSyst().getHostSO());
		hostIP.setText(getSyst().getHostIP());
		hostMac.setText(getSyst().getHostMac());
		DBSyst.setText(getSyst().getDBSyst());
		DBStringConnection.setText(getSyst().getDBStringConnection());
		DBSchema.setText(getSyst().getDBSchema());
		DBDriver.setText(getSyst().getDBDriver());
		DBDriverVersion.setText(getSyst().getDBDriverVersion());
		
	}
	
	public ImageView getImgView(){
		return imgView;
	}
	
	public void setImgView(){
		String imgSource = "/com/dotcook/resources/icons/system_64x64.png";
		Image img = new Image(getClass().getResourceAsStream(imgSource));
		getImgView().setImage(img);
	}
	
	public void setImgView(ImageView imgView){
		this.imgView = imgView;
	}
	
    public Properties getProp(){
    	return prop;
    }
    
    public void setProp(Properties prop){
    	this.prop = prop;
    }
    
    public void setPopupTitle(){
    	getPopupTitle().setText(getProp().getPropertyValue("popup.system.title"));
    }

	public void setPopupTitle(Label popupTitle) {
		this.popupTitle = popupTitle;		
	}
	
	public Label getPopupTitle(){
		return popupTitle;
	}

	public SystemInfo getSyst() {
		return syst;
	}

	public void setSyst(SystemInfo syst) {
		this.syst = syst;
	}
	
	public void setSyst(Session session) {
		
		try {			
			SystemInfo syst = new SystemInfo();
			syst.setSession(session);
			syst.getSystemInfo();
			this.syst = syst;			
		} catch(Exception e) {			
			e.printStackTrace();			
		}
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
