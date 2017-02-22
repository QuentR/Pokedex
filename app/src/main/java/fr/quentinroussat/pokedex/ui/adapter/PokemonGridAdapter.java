package fr.quentinroussat.pokedex.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Locale;

import fr.quentinroussat.pokedex.R;
import fr.quentinroussat.pokedex.model.Pokemon;
import fr.quentinroussat.pokedex.util.Constants;

/**
 * Created by Quentin on 20/02/2017.
 */

public class PokemonGridAdapter extends RecyclerView.Adapter<PokemonGridAdapter.ViewHolder>  {

    private ArrayList<Pokemon> dataset;
    private Context context;

    public PokemonGridAdapter(Context context)
    {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Pokemon pokemon = dataset.get(position);
        holder.pokemonName.setText(pokemon.getName());
        holder.pokemonName.setAllCaps(true);
        holder.pokemonBackground.setText(pokemon.getFullNumberFromPokemonNumber(pokemon.getNumber()));
        String imageURL = String.format(Locale.FRANCE, Constants.API_IMAGE_BASE_URL, pokemon.getNumber());
        Glide.with(context).load(imageURL).centerCrop().crossFade().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.pokemonBackground.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.pokemonImage);
    }

    public Pokemon getPokemonFromPosition(int position)
    {
        return dataset.get(position);
    }





    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addPokemonToAdapter(ArrayList<Pokemon> pokemonList) {
        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView pokemonImage;
        private TextView pokemonName;
        private TextView pokemonBackground;

        public ViewHolder(View itemView)
        {
            super(itemView);
            pokemonImage = (ImageView) itemView.findViewById(R.id.pokemon_image);
            pokemonName = (TextView) itemView.findViewById(R.id.pokemon_name);
            pokemonBackground = (TextView) itemView.findViewById(R.id.pokemon_background);
        }

        public ImageView getPokemonImage() {
            return pokemonImage;
        }

        public TextView getPokemonName() {
            return pokemonName;
        }
    }
}
