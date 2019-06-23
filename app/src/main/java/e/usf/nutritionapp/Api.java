package e.usf.nutritionapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    String BASE_URL = "https://api.nal.usda.gov/ndb/search/?format=json";

    @GET("&q={search}&sort=n&max=5&offset=0&api_key=OLhPe9BAFfszhNrO5kTx8HPmOczYzIhoVj6VlsT3")
    Call<List<FoodItem>> getFoodItems(@Path("search") String search);
}
