package fr.quentinroussat.pokedex.model;

import fr.quentinroussat.pokedex.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Quentin on 23/02/2017.
 */

public class Manager {

    private static Manager INSTANCE;
    private Retrofit retrofit;

    private Manager()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Manager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Manager();
        }

        return INSTANCE;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
