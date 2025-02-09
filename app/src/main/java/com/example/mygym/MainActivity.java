package com.example.mygym;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView userName, todaysWorkout;
    private EditText searchExercises;
    private ImageButton breakfastButton, lunchButton, dinnerButton;
    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // אתחול רכיבי ממשק
        userName = findViewById(R.id.user_name);
        todaysWorkout = findViewById(R.id.todays_workout);
        searchExercises = findViewById(R.id.search_exercises);
        breakfastButton = findViewById(R.id.food_breakfast_button);
        lunchButton = findViewById(R.id.food_lunch_button);
        dinnerButton = findViewById(R.id.food_dinner_button);
        mapView = findViewById(R.id.mapView);

        // אתחול המפה
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }

        // מאזין לחיפוש תרגילים
        searchExercises.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // כאן ניתן לממש חיפוש מתקדם
                Toast.makeText(MainActivity.this, "מחפש: " + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        private void openFoodList(String mealType) {
            Intent intent = new Intent(this, FoodListActivity.class);
            intent.putExtra("mealType", mealType);
            startActivity(intent);
        }

        breakfastButton.setOnClickListener(v -> openFoodList("ארוחת בוקר"));
        lunchButton.setOnClickListener(v -> openFoodList("ארוחת צהריים"));
        dinnerButton.setOnClickListener(v -> openFoodList("ארוחת ערב"));

    }

    // פונקציה להצגת הודעה
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // פונקציה לטעינת המפה
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // כאן ניתן להוסיף קוד להצגת מפה מותאמת אישית
    }

    // פונקציות מחזור חיים עבור המפה
    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }
}
