package com.example.fanxiafei.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fanxiafei.myapplication.R;
import com.example.fanxiafei.myapplication.view.ChannelModel;
import com.example.fanxiafei.myapplication.view.ItemTouchHelperCallback;
import com.example.fanxiafei.myapplication.view.OnItemClickListener;
import com.example.fanxiafei.myapplication.view.OnItemMoveListener;

import java.util.ArrayList;
import java.util.List;


public class DragActivity extends Activity implements OnItemClickListener {

    private ArrayList<ChannelModel> myChannelData = new ArrayList<>();
    private List<ChannelModel> moreChannelData = new ArrayList<>();
    ChannelAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(gridLayoutManager);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelperCallback());
        helper.attachToRecyclerView(rv);

        myChannelData.add(new ChannelModel("精品", "精品"));
        myChannelData.add(new ChannelModel("热点", "热点"));
        myChannelData.add(new ChannelModel("我的", "我的"));
        moreChannelData.add(new ChannelModel("情感", "情感"));
        moreChannelData.add(new ChannelModel("历史", "历史"));
        moreChannelData.add(new ChannelModel("好好", "好好"));

        adapter = new ChannelAdapter(this, helper, myChannelData, moreChannelData);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY_CHANNEL_ITEM || viewType == ChannelAdapter.TYPE_MORE_CHANNEL_ITEM ? 1 : 3;
            }
        });

    }

    @Override
    public void onItemClick(String s) {

    }

    public class ChannelAdapter extends RecyclerView.Adapter<VH> implements OnItemMoveListener {
        public static final int TYPE_MY_CHANNEL_TITLE = 0;
        public static final int TYPE_MY_CHANNEL_ITEM = 1;
        public static final int TYPE_MORE_CHANNEL_TITLE = 2;
        public static final int TYPE_MORE_CHANNEL_ITEM = 3;

        private static final int MY_HEADER_COUNT = 1;
        private static final int ALL_HEADER_COUNT = MY_HEADER_COUNT + 1;

        private static final int INSERT_CHANNEL_POSITION = 5;

        private static final long ANIM_TIME = 360L;

        private int fixPos;
        private boolean isEditMode;
        private List<ChannelModel> myChannelData, moreChannelData;

        private LayoutInflater inflater;
        private ItemTouchHelper itemTouchHelper;
        private OnItemClickListener onItemClickListener;

        public ChannelAdapter(Context context, ItemTouchHelper helper, List<ChannelModel> myChannels, List<ChannelModel> moreChannels) {
            this.inflater = LayoutInflater.from(context);
            this.itemTouchHelper = helper;
            this.myChannelData = myChannels;
            this.moreChannelData = moreChannels;
            fixPos = MY_HEADER_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_MY_CHANNEL_TITLE;
            } else if (position == myChannelData.size() + MY_HEADER_COUNT) {
                return TYPE_MORE_CHANNEL_TITLE;
            } else if (position > 0 && position < myChannelData.size() + MY_HEADER_COUNT) {
                return TYPE_MY_CHANNEL_ITEM;
            } else {
                return TYPE_MORE_CHANNEL_ITEM;
            }
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.layout_drag, parent, false);
            return new VH(itemView, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            switch (holder.getViewType()) {
                case TYPE_MY_CHANNEL_TITLE:
                    holder.iv.setImageResource(R.color.colorAccent);
                    holder.tv.setText(String.valueOf("my_title"));
                    break;
                case TYPE_MORE_CHANNEL_TITLE:
                    holder.iv.setImageResource(R.color.colorAccent);
                    holder.tv.setText(String.valueOf("more_title"));
                    break;
                case TYPE_MY_CHANNEL_ITEM:
                    holder.iv.setImageResource(R.color.colorPrimaryDark);
                    holder.tv.setText(String.valueOf(myChannelData.get(position - MY_HEADER_COUNT).getName()));
                    holder.itemView.setOnLongClickListener(v -> {
                        itemTouchHelper.startDrag(holder);
                        return true;
                    });
                    break;
                case TYPE_MORE_CHANNEL_ITEM:
                    holder.iv.setImageResource(R.color.colorPrimaryDark);
                    holder.tv.setText(String.valueOf(moreChannelData.get(position - myChannelData.size() - ALL_HEADER_COUNT).getName()));
                    holder.itemView.setOnLongClickListener(v -> {
                        itemTouchHelper.startDrag(holder);
                        return true;
                    });
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return myChannelData.size() + moreChannelData.size() + ALL_HEADER_COUNT;
        }

        @Override
        public boolean onItemMove(int from, int to) {
//            ChannelModel item = null;
//            if(getItemViewType(from) == TYPE_MY_CHANNEL_ITEM) {
//                item = myChannelData.get(from - MY_HEADER_COUNT);
//            }
//            if(getItemViewType(from) == TYPE_MORE_CHANNEL_ITEM) {
//                item = moreChannelData.get(from - ALL_HEADER_COUNT - myChannelData.size());
//            }
//
//            if(getItemViewType(from) == TYPE_MY_CHANNEL_ITEM && getItemViewType(to) == TYPE_MY_CHANNEL_ITEM) {
//                myChannelData.remove(from - MY_HEADER_COUNT);
//                myChannelData.add(to - MY_HEADER_COUNT, item);
//            }else if(getItemViewType(from) == TYPE_MORE_CHANNEL_ITEM && getItemViewType(to) == TYPE_MORE_CHANNEL_ITEM){
//                moreChannelData.remove(from - ALL_HEADER_COUNT - myChannelData.size());
//                moreChannelData.add(to - ALL_HEADER_COUNT- myChannelData.size(), item);
//            }
            notifyItemMoved(from, to);
            return true;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv;
        public int viewType;

        public VH(View itemView, int viewType) {
            super(itemView);
            this.iv = itemView.findViewById(R.id.iv);
            this.tv = itemView.findViewById(R.id.tv);
            this.viewType = viewType;
        }

        public int getViewType() {
            return viewType;
        }
    }

}
