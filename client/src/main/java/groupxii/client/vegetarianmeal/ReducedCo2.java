package groupxii.client.vegetarianmeal;

import groupxii.client.connector.VegetarianMealConnector;

public class ReducedCo2 {

    public static String getReducedCo2(String goodFoodName, int goodServingSize, String badFoodName, int badServingSize){
        String result = VegetarianMealConnector
                .calculateCO2Reduction(goodFoodName,
                        goodServingSize,
                        badFoodName,
                        badServingSize);
        return result;
    }
}