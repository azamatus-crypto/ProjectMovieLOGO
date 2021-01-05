package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.base.projectmovielogo.R;

import java.util.ArrayList;
import java.util.List;

import Data.Trailers;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolde> {
public List<Trailers>trailersList;
public ONTRAILERTOUCH ontrailertouch;

        public interface ONTRAILERTOUCH{
            void ontrailercklick(String url);
        }
    public TrailersAdapter() {
        trailersList=new ArrayList<>();
    }

    public void setOntrailertouch(ONTRAILERTOUCH ontrailertouch) {
        this.ontrailertouch = ontrailertouch;
    }

    public List<Trailers> getTrailersList() {
        return trailersList;
    }

    public void setTrailersList(List<Trailers> trailersList) {
        this.trailersList = trailersList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_trailers,parent,false);
        return new TrailerViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolde holder, int position) {
       Trailers trailers=trailersList.get(position);
       holder.textViewnameofvideo.setText(trailers.getName());

    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    class TrailerViewHolde extends RecyclerView.ViewHolder{
    private TextView textViewnameofvideo;
        public TrailerViewHolde(@NonNull View itemView) {
            super(itemView);
            textViewnameofvideo=itemView.findViewById(R.id.texviewNameoftrailer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ontrailertouch!=null){
                        ontrailertouch.ontrailercklick(trailersList.get(getAdapterPosition()).getKey());
                    }
                }
            });
        }
    }
}
