package fr.quentinroussat.pokedex.api;

import fr.quentinroussat.pokedex.model.PokemonAnswer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static fr.quentinroussat.pokedex.util.Constants.API_GET_POKEMON;
import static fr.quentinroussat.pokedex.util.Constants.LIMIT;
import static fr.quentinroussat.pokedex.util.Constants.OFFSET;

/**
 * Created by Quentin on 16/02/2017.
 */

public interface PokemonService {

    @GET(API_GET_POKEMON)
    Call<PokemonAnswer> getPokemonList(@Query(LIMIT) int limit, @Query(OFFSET) int offset);
}
