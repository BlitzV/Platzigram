package com.blitz.platzigram.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blitz.platzigram.R;
import com.blitz.platzigram.model.Picture;
import com.blitz.platzigram.view.PictureDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PictureAdapterRecyclerView extends RecyclerView.Adapter<PictureAdapterRecyclerView.PictureViewHolder>{

    private ArrayList<Picture> pictures;
    private  int resource; //sera el layout card view por separado
    private Activity activity;

    public PictureAdapterRecyclerView(ArrayList<Picture> pictures, int resource, Activity activity) {
        this.pictures = pictures;
        this.resource = resource;
        this.activity = activity;
        // lo que pasa en activity es el contexto
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inicializar clase PictureViewHolder
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false); //inflando un view, pasando el recurso por completo
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder pictureViewHolder, int i) {
        //aqui se trabajara toda la lista del arreglo
        // tenemos acceso a cada view, añadir onclicks, setear datos
        Picture picture = pictures.get(i);
        pictureViewHolder.userNameCard.setText(picture.getUserName());
        pictureViewHolder.timeCard.setText(picture.getTime());
        pictureViewHolder.likeNumberCard.setText(picture.getLike_number());
        Picasso.with(activity).load(picture.getPicture()).into(pictureViewHolder.pictureCard);

        pictureViewHolder.pictureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (activity, PictureDetailActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v,
                            activity.getString(R.string.transitonname_picture)).toBundle());
                }else {
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //Devuelve un conteo de cuantos elemtos tengo...
        return pictures.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {
        // se encarga de todos los view que component la tarjeta

        private ImageView pictureCard;
        private TextView userNameCard;
        private TextView timeCard;
        private TextView likeNumberCard;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);

            pictureCard = (ImageView) itemView.findViewById(R.id.pictureCard);
            userNameCard = (TextView) itemView.findViewById(R.id.userNameCard);
            timeCard = (TextView) itemView.findViewById(R.id.timeCard);
            likeNumberCard = (TextView) itemView.findViewById(R.id.likeNumberCard);
        }
    }
}
