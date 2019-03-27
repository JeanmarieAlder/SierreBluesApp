package com.example.sierrebluesappv1.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.database.entity.StageEntity;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();

    }

    private static void populateWithTestData(AppDatabase db) {
        db.stageDao().deleteAll();

        addStage(db, "Main scene", "Plaine Bellevue", "www.hevs.ch", 6000,
                false
                );
        addStage(db, "Small scene", "Plaine Bellevue", "www.hevs.ch", 1500,
                false
        );
        addStage(db, "THL", "Route Ancienne Sierre 3", "www.hevs.ch", 400,
                true
        );

        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        addAct(db,
                "Kiss", "USA", "Crazy band. lorem ipsum.", "Hard Rock",
                "22:00", "friday", 100.0f, "www.hevs.ch", 4
        );
        addAct(db,
                "ZZ Top", "USA", "Crazy rock blues band. lorem ipsum.", "Blues",
                "23:30", "saturday", 120.0f, "www.hevs.ch", 5
        );
        addAct(db,
                "Build To Spill", "USA", "Crazy band who loves to improvise. lorem ipsum.", "Rock",
                "20:00", "saturday", 50.0f, "www.hevs.ch", 6
        );

        addAct(db,
                "Kiss", "USA", "Crazy band. lorem ipsum.", "Hard Rock",
                "22:00", "friday", 100.0f, "www.hevs.ch", 4
        );
        addAct(db,
                "ZZ Top", "USA", "Crazy rock blues band. lorem ipsum.", "Blues",
                "23:30", "saturday", 120.0f, "www.hevs.ch", 5
        );
        addAct(db,
                "Build To Spill", "USA", "Crazy band who loves to improvise. lorem ipsum.", "Rock",
                "20:00", "saturday", 50.0f, "www.hevs.ch", 6
        );

        addAct(db,
                "Kiss", "USA", "Crazy band. lorem ipsum.", "Hard Rock",
                "22:00", "friday", 100.0f, "www.hevs.ch", 4
        );
        addAct(db,
                "ZZ Top", "USA", "Crazy rock blues band. lorem ipsum.", "Blues",
                "23:30", "saturday", 120.0f, "www.hevs.ch", 5
        );
        addAct(db,
                "Build To Spill", "USA", "Crazy band who loves to improvise. lorem ipsum.", "Rock",
                "20:00", "saturday", 50.0f, "www.hevs.ch", 6
        );
    }

    private static void addStage(AppDatabase db, String name, String location,
                                 String website, int capacity, boolean seats) {
        StageEntity stage = new StageEntity(name,location,website,capacity,seats);
        db.stageDao().insert(stage);
    }

    private static void addAct(AppDatabase db, String name, String country,
                               String descr, String genre, String startTime, String date,
                               float price, String website, int idstage) {
        ActEntity act = new ActEntity(name,country,descr,genre,startTime,date,
                price,website,(long)idstage);
        db.actDao().insert(act);

    }

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
