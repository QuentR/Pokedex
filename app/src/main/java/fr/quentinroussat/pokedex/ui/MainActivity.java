package fr.quentinroussat.pokedex.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import fr.quentinroussat.pokedex.R;
import fr.quentinroussat.pokedex.adapter.PokemonGridAdapter;
import fr.quentinroussat.pokedex.api.PokemonService;
import fr.quentinroussat.pokedex.model.Pokemon;
import fr.quentinroussat.pokedex.model.PokemonAnswer;
import fr.quentinroussat.pokedex.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private PokemonGridAdapter adapter;
    public static final String TAG = "POKEMON";
    private int offset = 0;
    private boolean canLoadData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getPokemonData(offset);
    }

    private void getPokemonData(int offset) {
        PokemonService service = retrofit.create(PokemonService.class);
        Call<PokemonAnswer> pokemonAnswerCall = service.getPokemonList(Constants.LOADING_VALUE, offset);

        pokemonAnswerCall.enqueue(new Callback<PokemonAnswer>() {
            @Override
            public void onResponse(Call<PokemonAnswer> call, Response<PokemonAnswer> response) {
                canLoadData = true;
                if (response.isSuccessful()) {

                    PokemonAnswer pokemonAnswer = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonAnswer.getResults();

                    if (pokemonList != null)
                    {
                        adapter.addPokemonToAdapter(pokemonList);
                    }
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonAnswer> call, Throwable t) {
                canLoadData = true;
                Toast.makeText(MainActivity.this, "Impossible de récupérer les données", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new PokemonGridAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 ){
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (canLoadData){
                        if (visibleItemCount + pastVisibleItems >= totalItemCount){
                            canLoadData = false;
                            offset = offset + Constants.LOADING_VALUE;
                            getPokemonData(offset);
                        }
                    }
                }
            }
        });
    }
}
