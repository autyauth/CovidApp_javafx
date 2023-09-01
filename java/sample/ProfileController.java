package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;


public class ProfileController {



    @FXML
    private Label usernameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label phonenumLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label firstvacLabel ,secondvacLabel, thirdvacLabel, fourthvacLabel;

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
    public void displayProfile(User user){
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
        this.nameLabel.setText(name);
        this.birthdayLabel.setText(birthday);
        this.addressLabel.setText(this.address);
        this.phonenumLabel.setText(phone_number);
        this.emailLabel.setText(this.email);
        this.firstvacLabel.setText(this.vaccine1);
        this.secondvacLabel.setText(this.vaccine2);
        this.thirdvacLabel.setText(this.vaccine3);
        this.fourthvacLabel.setText(this.vaccine4);
    }

    //ข้างล้่าง void to... ไว้สำหรับไปหน้าต่างๆตามหน้านั้นๆ
    public void toHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        homeController.displayHomePage(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void toCaseReport(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CaseReport.fxml"));
        Parent root = loader.load();
        CaseReportController caseReportController = loader.getController();
        caseReportController.displayCaseReport(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void toRiskAssessment(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("RiskAssessment.fxml"));
        Parent root = loader.load();
        RiskAssessmentController riskAssessmentController = loader.getController();
        riskAssessmentController.displayRiskAssessmentPage(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void toProfileEdit(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profileEdit.fxml"));
        Parent root = loader.load();
        ProfileEditController profileEditController = loader.getController();
        profileEditController.displayProfileEdit(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void toLogin(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void buttonDeleteAccount(ActionEvent event) throws IOException {
        //หากกดปุ่ม delete ก็ให้แจ้งเตือนเพื่อยืนยัน
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deleting");
        alert.setContentText("Do you sure to delete your account then 'Ok' is pressed, " +
                "you will return to login page");
        Optional <ButtonType> result = alert.showAndWait();
        // หากกด ok ก็จะทำการลบ account นั้น และกลับไปหน้าล้อคอิน
        if (result.isPresent() && result.get() == ButtonType.OK){
            System.out.println("DELETE FROM user_account WHERE username = '"+username+"'");
            toLogin(event);
            deleteAccount();

        }

    }
    public void deleteAccount(){ //delete account
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.getDataBaseLink();
        try {
            PreparedStatement psDeleteAccount = connection1.prepareStatement("DELETE FROM user_account WHERE username = '"+
                    this.username+"'");
            psDeleteAccount.execute();
            System.out.println("ลบไอดีสำเร็จ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
