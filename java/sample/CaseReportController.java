package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaseReportController implements Initializable {

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

    @FXML
    private TableView<DateSearchModel> tableTableView;
    @FXML
    private TableColumn<DateSearchModel, String> tableDate;
    @FXML
    private TableColumn<DateSearchModel, Integer> tableInfect;
    @FXML
    private TableColumn<DateSearchModel, Integer> tableRecover;
    @FXML
    private TableColumn<DateSearchModel, Integer> tableDeath;
    @FXML
    private TextField tableTextField;

    ObservableList<DateSearchModel> dateSearchModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize (URL url, ResourceBundle resource){
        DatabaseDateConnection connectNow = new DatabaseDateConnection();
        Connection connectOB = connectNow.getDataBaseLink();
        String TableViewQuart = "SELECT Date, Infect, Recover, Death FROM coviddata.covid_report";

        try {

            Statement statement = connectOB.createStatement();
            ResultSet queryOutput = statement.executeQuery(TableViewQuart);

            while (queryOutput.next()){

                String queryDay = queryOutput.getString("Date");
                Integer queryInfect = queryOutput.getInt("Infect");
                Integer queryRecover = queryOutput.getInt("Recover");
                Integer queryDeath = queryOutput.getInt("Death");

                //populate the ObservableList
                dateSearchModelObservableList.add(new DateSearchModel(queryDay, queryInfect, queryRecover, queryDeath));
            }
            // propertyValueFactory corresponds to the new ProductSearchModel fields
            // The table column is the one you annotate above
            tableDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
            tableInfect.setCellValueFactory(new PropertyValueFactory<>("Infect"));
            tableRecover.setCellValueFactory(new PropertyValueFactory<>("Recover"));
            tableDeath.setCellValueFactory(new PropertyValueFactory<>("Death"));
            tableTableView.setItems(dateSearchModelObservableList);
            //initial filtered list
            FilteredList<DateSearchModel> filteredDate = new FilteredList<>(dateSearchModelObservableList, b ->true);

            tableTextField.textProperty().addListener((observable, oldValue, newValue)-> {
                filteredDate.setPredicate(dateSearchModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }

                    String tableTextField = newValue.toLowerCase();

                    if(dateSearchModel.getDate().toLowerCase().indexOf(tableTextField) > -1) {
                        return true;//found date
                    }
                    else if(dateSearchModel.getInfect().toString().indexOf(tableTextField) > -1){
                        return true;//found infect
                    }
                    else if(dateSearchModel.getRecover().toString().indexOf(tableTextField) > -1) {
                        return true;//found recover
                    }
                    else if(dateSearchModel.getDeath().toString().indexOf(tableTextField) > -1) {
                        return true;//found death
                    }
                    else
                        return false;//not match
                });
            });
            SortedList<DateSearchModel> sortedData = new SortedList<>(filteredDate);
            //Bind sorted result with table view
            sortedData.comparatorProperty().bind(tableTableView.comparatorProperty());
            //apply filter and sorted data to the table view
            tableTableView.setItems(sortedData);
        }catch (SQLException e){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE,null, e);
            e.printStackTrace();
        }

    }
    public void displayCaseReport(User user){
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

    public void toRiskAssessment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RiskAssessment.fxml"));
        Parent root =  loader.load();
        RiskAssessmentController riskAssesmentController = loader.getController();
        riskAssesmentController.displayRiskAssessmentPage(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

