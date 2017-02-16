package fr.quentinroussat.pokedex.api;

import fr.quentinroussat.pokedex.model.PokemonAnswer;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Quentin on 16/02/2017.
 */

public interface PokemonService {

    @GET("pokemon")
    Call<PokemonAnswer> getPokemonList();
}
