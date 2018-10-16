package plusequalsto.com.radio;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<Model> dataset;
    private Context mContext;
    Typeface dayFonts, showFonts, timeFonts;

    public RecyclerViewAdapter(ArrayList<Model> mlist, Context context) {
        this.dataset = mlist;
        this.mContext = context;
        dayFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Bold.otf");
        showFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Bold.otf");
        timeFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Light.otf");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardShows;
        TextView day, show, time;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardShows = (CardView) itemView.findViewById(R.id.cardShows);
            this.day = (TextView) itemView.findViewById(R.id.day);
            this.show = (TextView) itemView.findViewById(R.id.show);
            this.time = (TextView) itemView.findViewById(R.id.time);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recyclerviewadapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Model object = dataset.get(position);

        ((ViewHolder) holder).day.setTypeface(dayFonts);
        ((ViewHolder) holder).day.setText(Html.fromHtml(object.day));
        ((ViewHolder) holder).show.setTypeface(showFonts);
        ((ViewHolder) holder).show.setText(Html.fromHtml(object.show));
        ((ViewHolder) holder).time.setTypeface(timeFonts);
        ((ViewHolder) holder).time.setText(Html.fromHtml(object.time));

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
