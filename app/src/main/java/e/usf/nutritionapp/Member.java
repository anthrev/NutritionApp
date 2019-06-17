package e.usf.nutritionapp;

public class Member {
    private String gender, goals;
    private float weight, goalWeight;
    private double weeklyGoals, age, height;
    double caloriesBurnedPerDay;
    double goalCalories;

    public Member(String gender, String goals, float weight, float goalWeight, double weeklyGoals, double age, double height, double caloriesBurnedPerDay, double goalCalories){
        this.gender = gender;
        this.goals = goals;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.weeklyGoals = weeklyGoals;
        this.age = age;
        this.height = height;
        this.caloriesBurnedPerDay = caloriesBurnedPerDay;
        this.goalCalories = goalCalories;

    }
    public Member(){

    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getGoals(){
        return goals;
    }

    public void setGoals(String goals){
        this.goals = goals;
    }

    public float getWeight(){
        return weight;
    }

    public void setWeight(float weight){
        this.weight = weight;
    }

    public float getGoalWeight(){
        return goalWeight;
    }

    public void setGoalWeight(float goalWeight){
        this.goalWeight = goalWeight;
    }

    public double getWeeklyGoals(){
        return weeklyGoals;
    }

    public void setWeeklyGoals(double weeklyGoals){
        this.weeklyGoals = weeklyGoals;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public double getHeight(){
        return height;
    }

    public void setAge(double age){
        this.age = age;
    }

    public double getAge() {
        return age;
    }

    public double getCaloriesBurnedPerDay(){
        if(getGender() == "Male"){
            return (66 + (6.23 * getWeight()) + (12.7 * getHeight()) - (6.8 * getAge()));
        } else {
            return(655 + (4.35*getWeight()) + (4.7 * getHeight()) - (4.7*getAge()));
        }
    }

    public void setCaloriesBurnedPerDay(double caloriesBurnedPerDay){
        this.caloriesBurnedPerDay = caloriesBurnedPerDay;
    }

    public double getGoalCalories(){
        if(getGoals() == "Lose Weight") {
           if(getWeeklyGoals() == .5){
               double caloriesToBurn = 3500 / 2;
               double caloriesToBurnPerDay = caloriesToBurn / 7;
               return (getCaloriesBurnedPerDay() - caloriesToBurnPerDay);
           } else {
               double caloriesToBurn = 3500;
               double caloriesToBurnPerDay = caloriesToBurn / 7;
               return (getCaloriesBurnedPerDay() - caloriesToBurnPerDay);
           }
        } else {
            if(getWeeklyGoals() == .5){
                double caloriesToConsume = 3500/ 2;
                return (caloriesToConsume / 7 + getCaloriesBurnedPerDay());
            } else {
                double caloriesToConsume = 3500;
                return (caloriesToConsume / 7 + getCaloriesBurnedPerDay());
            }
        }
    }

    public void setGoalCalories(double goalCalories){
        this.goalCalories = goalCalories;
    }
}
