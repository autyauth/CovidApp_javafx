package sample;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ProfileEditController {
    public Button Profile;
    public Button Home;
    public Button CaseReport;
    public Button RiskAssesment;
    @FXML
    private Button confirm;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private DatePicker birthdateDatePicker;
    @FXML
    private TextField addressTextField;
    @FXML
    private ChoiceBox vaccine1ChoiceBox;
    @FXML
    private ChoiceBox vaccine2ChoiceBox;
    @FXML
    private ChoiceBox vaccine3ChoiceBox;
    @FXML
    private ChoiceBox vaccine4ChoiceBox;
    @FXML
    private Label updateLabel;
    @FXML
    private Label usernameLabel;

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

    ObservableList<String> vaccineList = FXCollections.observableArrayList("Sinovac","AstraZeneca","Johnson & Johnson","Moderna","Sinopharm","Pfizer","ยังไม่ได้รับวัคซีน");
    @FXML
    private void initialize(){
        vaccine1ChoiceBox.setValue("ยังไม่ได้รับวัคซีน");
        vaccine2ChoiceBox.setValue("ยังไม่ได้รับวัคซีน");
        vaccine3ChoiceBox.setValue("ยังไม่ได้รับวัคซีน");
        vaccine4ChoiceBox.setValue("ยังไม่ได้รับวัคซีน");

        vaccine1ChoiceBox.setItems(vaccineList);
        vaccine2ChoiceBox.setItems(vaccineList);
        vaccine3ChoiceBox.setItems(vaccineList);
        vaccine4ChoiceBox.setItems(vaccineList);
    }

    public void displayProfileEdit(User user){ // รับข้อมูลจาก user แล้วมาใส่ในในตัวแปรของobj ของclass นั้นๆเอง
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
        System.out.println(birthday);

        usernameLabel.setText(username);
        emailTextField.setText(email);
        usernameTextField.setText(username);
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
        RiskAssessmentController riskAssesmentController = loader.getController();
        riskAssesmentController.displayRiskAssessmentPage(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void toLogin(ActionEvent event)throws IOException{
        PauseTransition pause = new PauseTransition(new Duration(700));
        pause.setOnFinished(event1 -> {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        });
        pause.play();
    }

    public void confirmUpdateAction(ActionEvent event) { // หากกดปุ่ม confirm แล้วเช็คตามเงื่อนไขต่างๆ
        if (firstnameTextField.getText().isBlank() || lastnameTextField.getText().isBlank() || phoneNumberTextField.getText().isBlank()
                || usernameTextField.getText().isBlank() || passwordTextField.getText().isBlank()
                || birthdateDatePicker.getValue() == null || addressTextField.getText().isBlank())
        {
            updateLabel.setText("กรุณากรอกข้อมูลให้ครบ");
        }else {

            if (phoneNumberCheck(phoneNumberTextField.getText())){
                updateUser(event);

            }else {
                updateLabel.setText("รูปแบบเบอร์ไม่ถูกต้อง");
            }
        }
    }
    public void updateUser(ActionEvent event){ //ใช้อัพข้อมูลต่างๆลงฐานข้อมูล
        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.getDataBaseLink();

        String update = "UPDATE user_account ";
        String set = "SET firstname = "+"'"+firstnameTextField.getText()+"'"+",lastname = '"+lastnameTextField.getText()+"'"+
                ",phone_number = '"+phoneNumberTextField.getText()+"'"+",password = '"+passwordTextField.getText()+"'"+
                ",birthdate = '"+birthdateDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"'"+
                ",address = '"+addressTextField.getText()+"'"+",vaccine1 = '"+(String) (vaccine1ChoiceBox.getValue())+"'"+
                ",vaccine2 = '"+(String)(vaccine2ChoiceBox.getValue())+"'"+",vaccine3 = '"+(String)(vaccine3ChoiceBox.getValue())+"'"+
                ",vaccine4 = '"+(String)(vaccine4ChoiceBox.getValue())+"'";
        String where = "WHERE username = '"+username+"'";
        String updateValues = update+set+where;

        try {
            Statement statement = connection1.createStatement();
            //สร้างstatement เพื่อจะได้ใช้ query อัพเดทข้อมูล
            ResultSet resultSet =statement.executeQuery("SELECT * FROM user_account WHERE username = '"+username+"'");
            if (resultSet.next() == true){
                updateLabel.setText("แก้ไขสำเร็จ กรุณาเพื่อไปหน้าล็อคอิน");
                statement.executeUpdate(updateValues);
                toLogin(event);

            }
        }catch (SQLIntegrityConstraintViolationException e){
            updateLabel.setText("เบอร์โทรศัพท์ซ้ำ"); //ตัวซ้ำมีแค่ 2 ตัวก็คือ เลขโทรรศัพท์กับไอดี จึงดักแค่นี้ได้เลย
        }
        catch (DataTruncation e){
            StringBuffer ee = new StringBuffer(String.valueOf(e)).delete(0,50);
            updateLabel.setText(String.valueOf(ee));
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public static boolean phoneNumberCheck(String phoneNumber){ //เช็คนัมเบอร์
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        boolean m = matcher.find();
        return m;
    }

}
