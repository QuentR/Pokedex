package fr.quentinroussat.pokedex.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.quentinroussat.pokedex.R;
import fr.quentinroussat.pokedex.model.Pokemon;

/**
 * Created by Quentin on 20/02/2017.
 */

public class PokemonGridAdapter extends RecyclerView.Adapter<PokemonGridAdapter.ViewHolder>  {

    private ArrayList<Pokemon> dataset;

    public PokemonGridAdapter()
    {
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);
        holder.pokemonName.setText(pokemon.getName());
        holder.pokemonName.setAllCaps(true);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void addPokemonToAdapter(ArrayList<Pokemon> pokemonList) {
        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView pokemonImage;
        private TextView pokemonName;

        public ViewHolder(View itemView)
        {
            super(itemView);

            pokemonImage = (ImageView) itemView.findViewById(R.id.pokemon_image);
            pokemonName = (TextView) itemView.findViewById(R.id.pokemon_name);
        }
    }
}
