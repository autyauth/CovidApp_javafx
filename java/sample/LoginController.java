package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private String firstname;
    private String lastname;
    private String phone_number;
    private String email;
    private String username;
    private String password;
    private String birthdate;
    private String address;
    private String vaccine1;
    private String vaccine2;
    private String vaccine3;
    private String vaccine4;

    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordTextField;

    public void loginButtonOnAction(ActionEvent event) {
        if (usernameTextField.getText().isBlank() == false && usernameTextField.getText().isBlank() == false) {
            try {
                DatabaseConnection connection = new DatabaseConnection();
                Connection connection1 = connection.getDataBaseLink();
                String select = "SELECT count(1) FROM user_account";
                String where = " WHERE username = '"+usernameTextField.getText() + "' AND password = '" + enterPasswordTextField.getText() + "'";
                String verifyLogin = select + where;
                System.out.println(verifyLogin);
                Statement statement = connection1.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                // ต้อง next เพราะแถวของ table ไม่รู้อยู่แถวไหน ต้องให้มาอยู่แล้ว ที่ 0
                //ResultSet ข้อมูลจาก query
                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        App home = new App();
                        loginMessageLabel.setText("login");

                        System.out.println("SELECT * FROM user_account WHERE username = "+"'"+usernameTextField.getText()+"'");
                        try {
                            Statement statementUser = connection1.createStatement();
                            ResultSet queryColumn = statementUser.executeQuery("SELECT * FROM user_account WHERE username = "+"'"+usernameTextField.getText()+"'");
                            queryColumn.next();
                            this.firstname = queryColumn.getNString("firstname");
                            this.lastname = queryColumn.getNString("lastname");
                            this.phone_number = queryColumn.getNString("phone_number");
                            this.email = queryColumn.getNString("email");
                            this.username = queryColumn.getNString("username");
                            this.password = queryColumn.getNString("password");
                            this.birthdate = queryColumn.getNString("birthdate");
                            this.address = queryColumn.getNString("address");
                            this.vaccine1 = queryColumn.getNString("vaccine1");
                            this.vaccine2 = queryColumn.getNString("vaccine2");
                            this.vaccine3 = queryColumn.getNString("vaccine3");
                            this.vaccine4 = queryColumn.getNString("vaccine4");

                            FXMLLoader loader=new FXMLLoader(getClass().getResource("Homepage.fxml"));
                            Parent root = loader.load();
                            User user = new User(firstname, lastname, phone_number, email, username, password, birthdate, address, vaccine1, vaccine2, vaccine3,vaccine4);
                            HomeController homeController=loader.getController();
                            homeController.displayHomePage(user);
                          //  System.out.println(queryColumn.getNString("username"));
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        loginMessageLabel.setText("login fail");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        } else {
            loginMessageLabel.setText("Enter username and password");
        }
    }
    public void registerButtonAction() {
        registerForm();
    }
    public void registerForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage registerStage = new Stage();
            registerStage.resizableProperty().setValue(Boolean.FALSE); // ห้ามปรับขนาดจอ
            registerStage.setScene(new Scene(root, 700, 450));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
