package fr.quentinroussat.pokedex.ui.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Locale;

import fr.quentinroussat.pokedex.R;
import fr.quentinroussat.pokedex.api.ApiService;
import fr.quentinroussat.pokedex.model.Language;
import fr.quentinroussat.pokedex.model.Manager;
import fr.quentinroussat.pokedex.model.Pokemon;
import fr.quentinroussat.pokedex.model.PokemonAnswer;
import fr.quentinroussat.pokedex.model.PokemonSpecies;
import fr.quentinroussat.pokedex.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsActivity extends AppCompatActivity {

    private ImageView pokemonImage;
    private TextView pokemonName;
    private Pokemon pokemon;
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        manager = Manager.getInstance();
        manager.getRetrofit();

        pokemonImage = (ImageView) findViewById(R.id.pokemon_image);
        pokemonName = (TextView) findViewById(R.id.pokemon_name);

        if (getIntent().getExtras() != null) {
            pokemon = (Pokemon) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_POKEMON);
            String imageURL = String.format(Locale.FRANCE, Constants.API_IMAGE_BASE_URL, pokemon.getNumber());
            Glide.with(this).load(imageURL).centerCrop().crossFade().into(pokemonImage);

            ApiService service = manager.getRetrofit().create(ApiService.class);
            Call<PokemonSpecies> pokemonSpeciesCall = service.getPokemonSpecies(pokemon.getNumber());

            pokemonSpeciesCall.enqueue(new Callback<PokemonSpecies>() {
                @Override
                public void onResponse(Call<PokemonSpecies> call, Response<PokemonSpecies> response) {
                    PokemonSpecies species = response.body();

                    if (species != null) {
                        int indexFr = species.getSpecificNameIndexLanguage("fr");
                        pokemonName.setText(species.getNames().get(indexFr).getName());
                    }
                }

                @Override
                public void onFailure(Call<PokemonSpecies> call, Throwable t) {
                    Log.d("POKEMON", t.getMessage());
                }
            });
        }
    }
}


