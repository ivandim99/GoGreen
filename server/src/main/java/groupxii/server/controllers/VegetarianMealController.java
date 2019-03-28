package groupxii.server.controllers;

import groupxii.database.MealEntry;
import groupxii.server.ServerApplication;
import groupxii.vegetarianmeal.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class VegetarianMealController {

    private SaveMeal saveMeal = new SaveMeal();
    private Calculations calculations = new Calculations();
    private GetMealData getMealData = new GetMealData();
    private List<Meal> mealList = new ArrayList<Meal>();
    private final AtomicLong counter = new AtomicLong();
    private List<String> eatenMealList = new ArrayList<String>();

    /**
    First run this to load in the MealDataList on the server,
    this has only to be done once the server starts.
    in the future we will load this also on the boot of the server.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void getMealData() throws IOException {
        getMealData.readMealListData();
        this.mealList = getMealData.getMealList();
    }

    /**
    This method will transform the data from the mealList into one string, which then can be used
    by the client, so the choiceboxes/listviews in the GUI are able to show all the meal names.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/mealNameList")
    public String getNameList() {
        MealNameList mealNameList = new MealNameList();
        mealNameList.setGetMealData(this.mealList);
        String mealNameListString = mealNameList.getMealNameList();
        return mealNameListString;
    }

    /**
    the client can send data to the server with the right values as parameter,
    then this method will store the data in the database.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/saveMealData")
    public MealEntry saveMealData(@RequestParam(value = "goodFoodName",
            defaultValue = "Unknown") String goodFoodName,
                                  @RequestParam(value = "goodServingSize",
                                          defaultValue = "Unknown") int goodServingSize,
                                  @RequestParam(value = "badFoodName",
                                          defaultValue = "Unknown") String badFoodName,
                                  @RequestParam(value = "badServingSize",
                                          defaultValue = "Unknown")
                                              int badServingSize) throws IOException {
        calculations.setMealList(this.mealList);
        int reducedCo2 = calculations.reducedCO2(badFoodName, badServingSize,
                calculations.calculateCO2(goodFoodName, goodServingSize));
        saveMeal.setMealList(this.mealList);
        saveMeal.saveMealData(counter.incrementAndGet(), goodFoodName, badFoodName,
                goodServingSize, badServingSize, reducedCo2);
        return saveMeal.getMealEntry();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/eatenMealList")
    public String getEatenMealList() throws IOException {
        EatenMealList eatenMealListClass = new EatenMealList();
        eatenMealListClass.readDatabase();
        String jsonReturn = "";
        eatenMealList = eatenMealListClass.getEatenMealList();
        for (int i = 0; i < eatenMealList.size(); i++ ){
            jsonReturn += eatenMealList.get(i) + " - ";
        }
        return jsonReturn;
    }

    /*
    @RequestMapping(method = RequestMethod.GET, value = "/latestMeal")
    public String getLatestMeal() {
    }
    */
}

