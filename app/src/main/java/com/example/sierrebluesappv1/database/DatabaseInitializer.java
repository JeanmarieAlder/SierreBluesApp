package com.example.sierrebluesappv1.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.database.entity.StageEntity;

/**
 * Class used to initialize datababe. Called when Demo data button is
 * used or when no data are displayied in an activity.
 */
public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void populateWithTestData(AppDatabase db) {
        db.stageDao().deleteAll();

        addStage(db, "Main scene", "Plaine Bellevue", "www.hevs.ch",
                6000, false
        );
        addStage(db, "Small scene", "Plaine Bellevue", "www.hevs.ch",
                1500, false
        );
        addStage(db, "THL", "Route Ancienne Sierre 3", "www.hevs.ch",
                400,
                true
        );
        addStage(db, "Britannia Pub", "Rue du Bourg 17", "www.hevs.ch",
                150, true
        );
        addStage(db, "HES-SO", "Route de Bellevue 2", "www.hevs.ch",
                1100, true
        );

        try {
            // Let's ensure that the clients are already stored
            // in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addAct(db,
                "Subscribe to Pewdiepie", "Sweden", "imagePath", "Original Content",
                "12:45", "Friday", 399.0f,  "Main scene"
        );
        addAct(db,
                "Kiss", "USA", "imagePath", "Hard Rock",
                "22:00", "Friday", 100.0f, "Main scene"
        );
        addAct(db,
                "Avenged Sevenfold", "USA", "imagePath", "Metal",
                "21:30", "Saturday", 70.0f, "Main scene"
        );
        addAct(db,
                "BSD", "Valais", "imagePath",
                "Alternative", "21:150", "Sunday", 50.0f,
                "Britannia Pub"
        );
        addAct(db,
                "Queen of The Stone Age", "GB", "imagePath", "Rock",
                "21:15", "Sunday", 60.0f, "Britannia Pub"
        );
        addAct(db,
                "Clutch", "USA", "imagePath", "Stoner",
                "16:20", "Friday", 48.0f, "THL"
        );

        addAct(db,
                "Prophets of Rage", "USA", "imagePath", "Hard Rock",
                "22:15", "Saturday", 90.0f, "Main scene"
        );
        addAct(db,
                "ZZ Top", "USA", "Crazy rock blues band. lorem ipsum.",
                "Blues", "23:30", "Saturday", 120.0f, "Main scene"
        );
        addAct(db,
                "Dead Kennedys", "USA", "imagePath", "Punk",
                "24:15", "Friday", 50.0f, "THL"
        );
        addAct(db,
                "Naast", "France", "imagePath", "Rock",
                "19:45", "Saturday", 45.0f,  "Small scene"
        );
        addAct(db,
                "Build To Spill", "USA", "imagePath", "Rock",
                "20:00", "Saturday", 50.0f, "Main scene"
        );
        addAct(db,
                "Züri West", "Switzerland", "imagePath", "Commercial",
                "21:30", "Saturday", 50.0f, "Small scene"
        );
        addAct(db,
                "Kaiser Chiefs", "Great Britain", "imagePath", "Rock",
                "23:30", "Sunday", 52.0f,  "THL"
        );
        addAct(db,
                "Black Sabbath", "USA", "imagePath", "Metal",
                "23:30", "Friday", 100.0f, "Main scene"
        );
        addAct(db,
                "Scorpions", "USA", "imagePath", "Metal",
                "19:30", "Friday", 70.0f, "Small scene"
        );
        addAct(db,
                "Red Hot Chili Peppers", "California", "imagePath",
                "Alternative", "21:00", "Sunday", 50.0f, "THL"
        );

        addAct(db,
                "Guns N Roses", "USA", "imagePath", "Hard Rock",
                "22:15", "Sunday", 65.0f, "Britannia Pub"
        );
        addAct(db,
                "Sex Pistols", "GB", "imagePath", "Punk",
                "22:15", "Sunday", 65.0f, "HES-SO"
        );
        addAct(db,
                "Bob marley", "Jamaica", "imagePath", "Reggae",
                "21:15", "Sunday", 65.0f, "HES-SO"
        );
        addAct(db,
                "ZZ Top", "USA", "imagePath", "Blues",
                "16:30", "Friday", 68.0f, "THL"
        );
        addAct(db,
                "The Smashing Pumpkins", "USA", "imagePath",
                "Alternative",
                "18:45", "Friday", 50.0f, "THL"
        );
    }

    private static void addStage(AppDatabase db, String name, String location,
                                 String website, int capacity, boolean seats) {
        StageEntity stage = new StageEntity(name, location, website, capacity, seats);
        db.stageDao().insert(stage);
    }

    private static void addAct(AppDatabase db, String name, String country,
                               String image, String genre, String startTime, String date,
                               float price, String stageName) {
        ActEntity act = new ActEntity(name, country, image, genre, startTime, date,
                price, stageName);
        db.actDao().insert(act);

    }

    /**
     * Async task to populate database with demo data in background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
