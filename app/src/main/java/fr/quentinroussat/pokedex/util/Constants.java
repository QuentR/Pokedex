package fr.quentinroussat.pokedex.util;

/**
 * Created by Quentin on 20/02/2017.
 */

public class Constants {

    public static final String API_URL = "http://pokeapi.co/api/v2/";
    public static final String API_IMAGE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%d.png";
    public static final String API_IMAGE_HD_BASE_URL = "http://serebii.net/pokemongo/pokemon/%s.png";
    public static final String API_GET_POKEMON = "pokemon";
    public static final String API_GET_POKEMON_SPECIES = "pokemon-species/{id}";
    public static final String API_GET_ITEMS = "items";
    public static final String INTENT_EXTRA_POKEMON = "POKEMON";
    public static final String TRANSITION_POKEMON_IMAGE = "pokemonImage";

    /**
     * URL PARAMETERS
     */

    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final int LOADING_VALUE = 50;

}
