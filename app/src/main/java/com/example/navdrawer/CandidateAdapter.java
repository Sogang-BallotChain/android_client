package com.example.navdrawer;

import android.view.LayoutInflater; //
import android.view.View;   //
import android.view.ViewGroup;  //
import android.widget.TextView; //

import androidx.annotation.NonNull; //
import androidx.recyclerview.widget.RecyclerView;   //

import java.util.ArrayList; //

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.ViewHolder> {
    ArrayList<Candidate> items = new ArrayList<Candidate>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        // [yh] 인플레이션을 통해 뷰 객체 만들기.
        View itemView = inflater.inflate(R.layout.candidate_item, viewGroup, false);

        // [yh] 뷰홀더 객체를 생성하면서 뷰 객체를 전달하고 그 뷰홀더 객체를 반환하기.
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Candidate item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Candidate item) {
        items.add(item);
    }

    public void setItems(ArrayList<Candidate> items) {
        this.items = items;
    }

    public Candidate getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Candidate item) { items.set(position, item); }

    // [yh] 각각의 아이템을 위한 뷰는 뷰홀더에 담아두자.
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Candidate item) {
            textView.setText(item.candiNm);
            textView2.setText(item.candiCnt + " 표");
        }

    }
}
