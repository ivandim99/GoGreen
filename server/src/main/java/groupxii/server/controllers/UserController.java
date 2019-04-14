package groupxii.server.controllers;

import groupxii.database.Database;
import groupxii.database.UserEntry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {


    /**
     * Receives data and creates a user entry in the user collection.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public void saveUserData(@RequestParam(value = "username",
        defaultValue = "") String username,
                              @RequestParam(value = "password",
                                      defaultValue = "")
                                      String password)  {
        //TODO error handling, return responses
        if (username.isEmpty() || password.isEmpty()) {
            return;
        }

	//TODO verify name is unique
	/*
        if (Database.instance.findUserByName(username)) {
            return;
        }
	*/

        int userId = Database.instance.getUserCount() + 1;
        UserEntry userEntry = new UserEntry(userId, username, password);
        Database.instance.save(userEntry);
    }

    /**
     * Receives user's id and returns the list
     * of his/her friends.
     */
    //Isn't this unnecessary slow?
    @RequestMapping(method = RequestMethod.GET, value = "/getFriends")
    public List<UserEntry> getFriends(@RequestParam(value = "Id",
        defaultValue = "-1") int userId) {
        UserEntry user =  Database.instance.findUserById(userId);
        List<Integer> friendsIdList = user.getFriendsId();

        List<UserEntry> friends = new ArrayList<>();
        for (int i = 0; i < friendsIdList.size(); i++) {
            UserEntry friend =  Database.instance.findUserById(friendsIdList.get(i));
            friends.add(friend);
        }
        return friends;
    }

    /**
     * returns all users sorted by points.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/Leaderboard")
    public List<UserEntry> leaderboard() {
        List<UserEntry> users = Database.instance.sortUsersByReducedCo2();
        return users;
    }

    /**
     * receives two id's and adds the second one as a friend to the first one.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addFriend")
    public void addFriend(Principal principal,
                          @RequestParam(value = "newFriend",
                                  defaultValue = "Unknown") int friendsId) {
        String user = principal.getName();
        Database.instance.addFriend(user, friendsId);
    }


    //TODO make internal for features

    /**
     * Increments reducedCO2 with some reducedCo2 dependant on the meal.
     */
    /*
      @RequestMapping(method = RequestMethod.GET, value = "increaseReducedCO2")
      public DBObject incReducedCO2(@RequestParam(value = "Id",defaultValue = "Unknown") int userId,
                                @RequestParam(value = "ReducedCO2",
                                        defaultValue = "Unknown") int reducedCo2) {
          Database.instance.incrementReducedCo2(userId,reducedCo2);
          DBObject user = Database.instance.findDocumentById(userId);
          return user;
      }
      */
    @RequestMapping(method = RequestMethod.GET, value = "/getReducedCo2OfUser")
    public int getReducedCo2OfUser(@RequestParam(value = "Id", defaultValue = "-1") int userId) {
        UserEntry user = Database.instance.findUserById(userId);
        int reducedCo2 = user.getReducedCo2();
        return reducedCo2;
    }
}


