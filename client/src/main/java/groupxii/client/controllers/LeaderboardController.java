package groupxii.client.controllers;

import groupxii.client.Main;
import groupxii.client.connector.LeaderboardConnector;
import groupxii.client.connector.Target;
import groupxii.client.leaderboard.JsonConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LeaderboardController implements Initializable {

    private String overallListStr = "";
    private String friendListStr = "";
    //Should be the userId that is assigned to the user when he/she registers
    private static int userId = 1;

    @FXML
    private ListView<HBoxCell> overallLeaderboard = new ListView();

    @FXML
    private ListView<HBoxCell> friendsLeaderboard = new ListView();

    @FXML
    private static Text addedFriend = new Text();

    public static class HBoxCell extends HBox {
        Label label = new Label();
        Button button = new Button();

        HBoxCell(String labelText, String buttonText, int friendId) {
            super();

            label.setText(labelText);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                                   @Override
                                   public void handle(ActionEvent e) {
                                       addFriend(friendId);
                                   }
                               });
            button.setText(buttonText);

            this.getChildren().addAll(label, button);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
        try {
            List<DBObject> overallListJsonStr = (DBObject) JSON.parse(new Scanner(new URL(host + "Leaderboard").openStream(),
                    "UTF-8").nextLine());
            String friendListJsonStr = new Scanner(new URL(host + "getFriends?Id=" + userId).openStream(),
                    "UTF-8").nextLine();
            JsonConverter jsonConverter = new JsonConverter();
            overallListStr = jsonConverter.leaderboardToString(overallListJsonStr)
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        overallListStr = JsonConverter.leaderboardToString(LeaderboardConnector.retrieveLeaderboard());
        List<String> overallLeaderboardList = Arrays.asList(overallListStr.split(","));
        List<HBoxCell> overallList = new ArrayList<>();
        for (int i = 0; i < overallLeaderboardList.size(); i++) {
            overallList.add(new HBoxCell(overallLeaderboardList.get(i), "ADD FRIEND", Integer.parseInt(overallLeaderboardList.get(i+1))));
            i++;
        }
        ObservableList<HBoxCell> overallLeaderboardObservableList;
        overallLeaderboardObservableList = FXCollections.observableArrayList(overallList);
        overallLeaderboard.setItems(overallLeaderboardObservableList);

        friendListStr = JsonConverter.leaderboardToString(LeaderboardConnector.retrieveFriends(userId));
        List<String> friendsLeaderboardList = Arrays.asList(friendListStr.split(","));
        List<HBoxCell> friendList = new ArrayList<>();
        for (int i = 0; i < friendsLeaderboardList.size(); i++) {
            friendList.add(new HBoxCell(friendsLeaderboardList.get(i), "UNFOLLOW", Integer.parseInt(overallLeaderboardList.get(i+1))));
            i++;
        }
        ObservableList<HBoxCell> friendsLeaderboardObservableList;
        friendsLeaderboardObservableList = FXCollections.observableArrayList(friendList);
        friendsLeaderboard.setItems(friendsLeaderboardObservableList);
        //System.out.println("Overall List:   " + overallLeaderboardList);
        //System.out.println("Friend List:   " + friendsLeaderboardList);

    }

    public static void addFriend(int friendId){
        LeaderboardConnector.addFriend(userId, friendId);
        addedFriend.setText("Ädded new friend!");
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    @FXML
    public void btnBack(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("Menu.fxml", event);
    }
}
