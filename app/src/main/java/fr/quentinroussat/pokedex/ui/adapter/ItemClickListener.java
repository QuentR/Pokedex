package fr.quentinroussat.pokedex.ui.adapter;

import android.view.View;

/**
 * Created by Quentin on 22/02/2017.
 */

public interface ItemClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
