package fr.quentinroussat.pokedex.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import fr.quentinroussat.pokedex.R;
import fr.quentinroussat.pokedex.api.PokemonService;
import fr.quentinroussat.pokedex.model.Pokemon;
import fr.quentinroussat.pokedex.model.PokemonAnswer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    public static final String TAG = "POKEMON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        retrofit = new Retrofit.Builder().baseUrl("http://pokeapi.co/api/v2").addConverterFactory(GsonConverterFactory.create()).build();

        getPokemonData();
    }

    private void getPokemonData() {
        PokemonService pokemonService = retrofit.create(PokemonService.class);
        Call<PokemonAnswer> pokemonAnswerCall = pokemonService.getPokemonList();

        pokemonAnswerCall.enqueue(new Callback<PokemonAnswer>() {
            @Override
            public void onResponse(Call<PokemonAnswer> call, Response<PokemonAnswer> response) {
                if (response.isSuccessful()){

                    PokemonAnswer pokemonAnswer = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonAnswer.getResult();

                    for (int i = 0; i < pokemonList.size(); i++) {
                        Log.d(TAG, "Pokemon : " + pokemonList.get(i).getName());
                    }
                }else
                {
                    Log.d(TAG, "onResponse : " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<PokemonAnswer> call, Throwable t) {
                Log.d(TAG, "onFailure : " + t.getMessage());
            }
        });
    }
}
