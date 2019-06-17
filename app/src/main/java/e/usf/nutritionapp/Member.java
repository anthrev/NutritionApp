package e.usf.nutritionapp;

public class Member {
    private String gender, goals;
    private float weight, goalWeight;
    private double weeklyGoals, age, height;
    double caloriesBurnedPerDay;

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
}
