package fr.quentinroussat.pokedex.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import fr.quentinroussat.pokedex.R;
import fr.quentinroussat.pokedex.model.Manager;
import fr.quentinroussat.pokedex.ui.adapter.ItemClickListener;
import fr.quentinroussat.pokedex.ui.adapter.PokemonGridAdapter;
import fr.quentinroussat.pokedex.api.ApiService;
import fr.quentinroussat.pokedex.model.Pokemon;
import fr.quentinroussat.pokedex.model.PokemonAnswer;
import fr.quentinroussat.pokedex.ui.adapter.RecyclerTouchListener;
import fr.quentinroussat.pokedex.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private PokemonGridAdapter adapter;
    public static final String TAG = "POKEMON";
    private int offset = 0;
    private boolean canLoadData = true;
    private Manager manager;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        initRecyclerView();
        manager = Manager.getInstance();

        getPokemonData(offset);
    }

    private void getPokemonData(int offset) {
        ApiService service = manager.getRetrofit().create(ApiService.class);
        Call<PokemonAnswer> pokemonAnswerCall = service.getPokemonList(Constants.LOADING_VALUE, offset);

        pokemonAnswerCall.enqueue(new Callback<PokemonAnswer>() {
            @Override
            public void onResponse(Call<PokemonAnswer> call, Response<PokemonAnswer> response) {
                canLoadData = true;
                if (response.isSuccessful()) {
                    PokemonAnswer pokemonAnswer = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonAnswer.getResults();

                    if (pokemonList != null) {
                        adapter.addPokemonToAdapter(pokemonList);
                    }
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonAnswer> call, Throwable t) {
                canLoadData = true;
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Impossible de charger les données", Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });
    }

    /**
     * Initialise la REcyclerView avec les différents paramétrages
     */
    private void initRecyclerView() {
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
                if (dy > 0) {
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (canLoadData) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            canLoadData = false;
                            offset = offset + Constants.LOADING_VALUE;
                            getPokemonData(offset);
                        }
                    }
                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_POKEMON, adapter.getPokemonFromPosition(position));
                PokemonGridAdapter.ViewHolder holder = (PokemonGridAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, (View)holder.getPokemonImage(), Constants.TRANSITION_POKEMON_IMAGE);
                startActivity(intent, options.toBundle());
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }
}
