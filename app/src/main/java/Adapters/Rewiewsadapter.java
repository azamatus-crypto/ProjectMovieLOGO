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

import Data.Rewiews;

public class Rewiewsadapter extends RecyclerView.Adapter<Rewiewsadapter.RewiewsViewHolder> {
    public List<Rewiews>rewiewsList;

    public Rewiewsadapter() {
       rewiewsList=new ArrayList<>();
    }

    public List<Rewiews> getRewiewsList() {
        return rewiewsList;
    }

    public void setRewiewsList(List<Rewiews> rewiewsList) {
        this.rewiewsList = rewiewsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RewiewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_rewiews,parent,false);
        return new RewiewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewiewsViewHolder holder, int position) {
       Rewiews rewiews=rewiewsList.get(position);
       holder.textViewauthor.setText(rewiews.getAuthor());
       holder.textViewcontent.setText(rewiews.getContent());
    }

    @Override
    public int getItemCount() {
        return rewiewsList.size();
    }

    class RewiewsViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewauthor;
        private TextView textViewcontent;

        public RewiewsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewauthor=itemView.findViewById(R.id.textviewAuthor);
            textViewcontent=itemView.findViewById(R.id.textviewContent);
        }
    }
}
