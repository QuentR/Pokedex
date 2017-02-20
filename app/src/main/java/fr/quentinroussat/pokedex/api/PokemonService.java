package fr.quentinroussat.pokedex.api;

import fr.quentinroussat.pokedex.model.PokemonAnswer;
import fr.quentinroussat.pokedex.util.Constants;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Quentin on 16/02/2017.
 */

public interface PokemonService {

    @GET(Constants.API_GET_POKEMON)
    Call<PokemonAnswer> getPokemonList();
}
