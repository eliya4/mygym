
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ExerciseApi {
    @GET("exercises")
    Call<List<ExerciseModel>> getExercises(@Header("X-RapidAPI-Key") String apiKey);
}
