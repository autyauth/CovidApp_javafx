package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterController{

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private DatePicker dateTextField;
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
    private Label registerLabel;

    ObservableList<String> vaccineList = FXCollections.observableArrayList("Sinovac","AstraZeneca","Johnson & Johnson","Moderna","Sinopharm","Pfizer","ยังไม่ได้รับวัคซีน");
    //สร้าง list สำหรับ dropdown ของเลือกวัคซีน
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
        //ตั้งค่า dropdown
    }
    public void confirmRegisterAction(){
        if (firstNameTextField.getText().isBlank() || lastNameTextField.getText().isBlank() || phoneNumberTextField.getText().isBlank()
            || emailTextField.getText().isBlank() || userNameTextField.getText().isBlank() || passwordTextField.getText().isBlank()
            || dateTextField.getValue() == null || addressTextField.getText().isBlank())
        {// check เงื่อนไขว่าในช่องต่างๆว่างไหม ถ้าว่างก็ให้ไปกรอกข้อมูลให้ครบ
            registerLabel.setText("กรุณากรอกข้อมูลให้ครบ");
        }else {

            if (phoneNumberCheck(phoneNumberTextField.getText())){ // check รูปแบบเบอร์
                if (emailCheck(emailTextField.getText())){ // check รูปแบบเมล
                    registerUser();
                }else {
                    registerLabel.setText("รูปแบบอีเมลไม่ถูกต้อง");
                }

            }else {
                registerLabel.setText("รูปแบบเบอร์ไม่ถูกต้อง");
            }

        }
    }
    public void registerUser(){

        DatabaseConnection connection = new DatabaseConnection();
        Connection connection1 = connection.getDataBaseLink();
        // เชื่อมกับ database
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        String date = dateTextField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String address = addressTextField.getText();
        String vaccine1 = (String) (vaccine1ChoiceBox.getValue());
        String vaccine2 = (String) (vaccine2ChoiceBox.getValue());
        String vaccine3 = (String) (vaccine3ChoiceBox.getValue());
        String vaccine4 = (String) (vaccine4ChoiceBox.getValue());
        //ใส่ข้อมูลต่างๆไปยังตัวแปร เพื่อใช้ต่อจากนี้
        String insert = "INSERT INTO user_account (firstname,lastname,phone_number,email,username,password,birthdate,address,vaccine1,vaccine2,vaccine3,vaccine4) ";
        String values = "VALUES ('"+firstName+"','"+lastName+"','"+phoneNumber+"','"+email+"','"+userName+"','"+password+
                "','"+date+"','"+address+"','"+vaccine1+"','"+vaccine2+"','"+vaccine3+"','"+vaccine4+"')";
        String insetValues = insert+values;
        // สร้าง query ไว้สำหรับ execute
        try {
            Statement statement = connection1.createStatement();
            //สร้างstatement เพื่อจะได้ใช้ query อัพเดทข้อมูล
            ResultSet resultSet =statement.executeQuery("SELECT * FROM user_account WHERE username = '"+userName+"'");
            //execute ข้อมูลไปยังdatabase แล้วใช้ ResultSet รับก้อนข้อมูลมา
            if (resultSet.next() == true){ // หากมีคอลัมที่ 0 หรือก็คือหาบรรทัดที่มี username เจอก็แปลว่ามี username นั้นไปแล้ว ถ้าไม่มีบรรทัดนั้นแปลว่าไม่มี username นั้น
                System.out.println("username Duplicate");
                registerLabel.setText("username ซ้ำ");

            } else {
                statement.executeUpdate(insetValues);
                System.out.println("1");
                registerLabel.setText("ลงทะเบียนสำเร็จ");
            }

        }catch (SQLIntegrityConstraintViolationException e){
            registerLabel.setText("เบอร์โทรศัพท์ซ้ำ"); //ตัวซ้ำมีแค่ 2 ตัวก็คือ เลขโทรรศัพท์กับไอดี จึงดักแค่นี้ได้เลย
        }
        catch (DataTruncation e){
            StringBuffer ee = new StringBuffer(String.valueOf(e)).delete(0,50);
            registerLabel.setText(String.valueOf(ee));
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();


        }
    }
    public static boolean phoneNumberCheck(String phoneNumber){
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        boolean m = matcher.find();
        return m;

    }
    public static boolean emailCheck(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        boolean e = matcher.find();
        return e;
    }
}
