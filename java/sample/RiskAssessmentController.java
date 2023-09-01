package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class RiskAssessmentController implements Initializable {
    @FXML
    private Label usernameLabel;
    @FXML
    private Button AssessInfo;
    @FXML
    private CheckBox Symptom1,Symptom2,Symptom3,Symptom4,Symptom5,Symptom6,Symptom7,Symptom8;
    @FXML
    private RadioButton riskmultiplier1,riskmultiplier2,noriskmultiplier1,noriskmultiplier2;
    @FXML
    private Label result;
    @FXML
    private ChoiceBox<String> GenderChoicebox,AgeChoicebox ;

    private String[] age ={"0-17","18-44","45-64","65-74","75+"};
    private String[] gender ={"ชาย","หญิง"};

    private String firstname;
    private String lastname;
    private String username;
    private String name;
    private String birthday;
    private String address;
    private String phone_number;
    private String email;
    private String vaccine1;
    private String vaccine2;
    private String vaccine3;
    private String vaccine4;
    private String password;
    private User user;

    private double risk=0;
    private double riskMultiplied =1;
    private double score;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       AgeChoicebox.getItems().addAll(age);
        GenderChoicebox.getItems().addAll(gender);

    }
    public void Assess(ActionEvent event) {
        checkSymptoms();
        riskMultiplied();
        showResult();
        risk=0;
        riskMultiplied =1;
    }

    public void displayRiskAssessmentPage(User user){
        this.user = user;
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.name = user.getFirstName() + " " + user.getLastName();
        this.birthday = user.getDate();
        this.address = user.getAddress();
        this.phone_number = user.getPhoneNumber();
        this.email = user.getEmail();
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.vaccine1 = user.getVaccine1();
        this.vaccine2 = user.getVaccine2();
        this.vaccine3 = user.getVaccine3();
        this.vaccine4 = user.getVaccine4();
        this.usernameLabel.setText(username);
    }
    public void toHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        homeController.displayHomePage(user);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void toProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Parent root = loader.load();
        ProfileController profileController = loader.getController();
        profileController.displayProfile(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void toCaseReport(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CaseReport.fxml"));
        Parent root = (Parent) loader.load();
        CaseReportController caseReportController = loader.getController();
        caseReportController.displayCaseReport(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void checkSymptoms(){
        if(Symptom1.isSelected()) {risk +=10;} else {risk +=0;}
        if(Symptom2.isSelected()) {risk +=10;} else {risk +=0;}
        if(Symptom3.isSelected()) {risk +=10;} else {risk +=0;}
        if(Symptom4.isSelected()) {risk +=10;} else {risk +=0;}
        if(Symptom5.isSelected()) {risk +=10;} else {risk +=0;}
        if(Symptom6.isSelected()) {risk +=10;} else {risk +=0;}
        if(Symptom7.isSelected()) {risk +=10;} else {risk +=0;}
        if(Symptom8.isSelected()) {risk +=10;} else {risk +=0;
        }
    }
    public void riskMultiplied(){
        if(riskmultiplier1.isSelected()&&riskmultiplier2.isSelected()) {
            riskMultiplied =1.5*1.5;risk+=50;}
        else if(riskmultiplier2.isSelected() || riskmultiplier1.isSelected()) {
            riskMultiplied =1*1.5;risk+=30;}
        else riskMultiplied = 1;

    }
    public void showResult(){
        score = risk* riskMultiplied;
        if (score>=80){
            result.setText("HIGH");
            result.setTextFill(Color.RED);}

        else if(score >40){
            result.setText("MEDIUM");
            result.setTextFill(Color.YELLOW);}

        else{
            result.setText("LOW");
            result.setTextFill(Color.GREEN);}
    }
}


