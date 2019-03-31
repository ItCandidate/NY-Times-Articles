package com.demo.articles.ui.articles_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.articles.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.demo.articles.data.remote.models.MostPopularModelResponse;

import java.util.List;

/**
 * Created on 30/3/19.
 */
public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder> {

    private List<MostPopularModelResponse.Result> data;
    private IOnItemClickListener listener;

    public ArticleListAdapter(List<MostPopularModelResponse.Result> data, IOnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MostPopularModelResponse.Result item = data.get(i);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvBy.setText(item.getByline());
        viewHolder.tvDate.setText(item.getPublishedDate());
        viewHolder.tvSection.setText(item.getSection());

        String url = null;
        for (MostPopularModelResponse.MediaMetadatum model : item.getMedia().get(0).getMediaMetadata()) {
            if (model.getFormat().equalsIgnoreCase("Standard Thumbnail")) {
                url = model.getUrl();
            }
        }
        Glide.with(viewHolder.itemView.getContext()).load(url).apply(RequestOptions.circleCropTransform()).into(viewHolder.imgLogo);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgLogo)
        ImageView imgLogo;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvBy)
        TextView tvBy;

        @BindView(R.id.tvSection)
        TextView tvSection;

        @BindView(R.id.tvDate)
        TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(data.get(getAdapterPosition()));
                }
            });
        }
    }

    interface IOnItemClickListener {
        void onItemClick(MostPopularModelResponse.Result item);
    }
}
