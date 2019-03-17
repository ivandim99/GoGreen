package client.groupxii.controllers;

import client.groupxii.vegetarianMeal.EatenMealList;
import client.groupxii.vegetarianMeal.SafeMeal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class VegetarianMealController implements Initializable {

    @FXML
    private ChoiceBox<String> cb = new ChoiceBox();

    @FXML
    private ChoiceBox<String> cb1 = new ChoiceBox();

    @FXML
    private Slider slider = new Slider();

    @FXML
    private Slider slider1 = new Slider();

    @FXML
    private Text textfield = new Text();

    @FXML
    private ListView<String> listView = new ListView();

    private String goodFoodName = "";
    private String badFoodName = "";
    private String host = "http://localhost:8080/";
    private String listItems = "";
    private SafeMeal safeMeal = new SafeMeal();
    private List<String> listViewItems = new ArrayList<String>();

    public void setListViewItems(List<String> listViewItems) {
        this.listViewItems = listViewItems;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        EatenMealList eatenMealList = new EatenMealList();
        try {
            listItems = new Scanner(new URL(host + "mealNameList").openStream(), "UTF-8").nextLine();
            eatenMealList.readDatabase();
        } catch (IOException e){
            e.printStackTrace();
        }
        List<String> items = Arrays.asList(listItems.split(", "));
        ObservableList<String> list = FXCollections.observableArrayList(items);
        cb.getItems().addAll(list);
        cb1.getItems().addAll(list);
        setListViewItems(eatenMealList.getEatenMealList());
        ObservableList<String> listViewObservable = FXCollections.observableArrayList(listViewItems);
        listView.getItems().addAll(listViewObservable);
    }

    /**
     * Calculates the co2 reduced emmision when the user clicks the calculate button.
     * it also calculates the amount of points the user earned.
     * @param event mouse click
     * @throws Exception throws exception when something went wrong
     */

    @FXML
    public void safeMeal(MouseEvent event) throws Exception {
        badFoodName = cb1.getValue();
        goodFoodName = cb.getValue();
        int goodServingSize = (int) slider.getValue();
        int badServingSize = (int) slider1.getValue();
        String enteredMeal = safeMeal.safeMeal(goodFoodName, badFoodName, goodServingSize, badServingSize);
        textfield.setText(enteredMeal);
    }
}
