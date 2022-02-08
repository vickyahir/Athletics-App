package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Activity.FollowingActivity;
import com.example.athletics.Activity.VideoViewActivity;
import com.example.athletics.Utils.Functions;

import java.util.List;


public class AllListCategoryAdapter extends RecyclerView.Adapter<AllListCategoryAdapter.Myviewholder> {
    Context context;
    List<String> muscles;


    public AllListCategoryAdapter(Activity activity, List<String> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allcategorylist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final String bean = muscles.get(position);

        holder.Tv_Username.setText(bean);
        holder.simpleVideoView.setVideoURI(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.dummy_two));
        holder.simpleVideoView.start();


//        String uriPath = "android.resource://" + context.getPackageName() + "/" + R.raw.dummy_two;
//        Uri uri = Uri.parse(uriPath);
//        holder.simpleVideoView.setVideoURI(uri);
//        holder.simpleVideoView.start();

        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_fill));
                holder.TvLikeCount.setTextColor(context.getResources().getColor(R.color.colorPrimary));

//                if (clicked) {
//                    holder.imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart));
//                    holder.TvLikeCount.setTextColor(context.getResources().getColor(R.color.black));
//                    clicked = false;
//                } else {
//
//                    clicked = true;
//                }


            }
        });

        holder.ImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.ImgMenu);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.home_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
//                        Toast.makeText(context, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                        if (menuItem.getItemId() == R.id.MenuViewProfile) {
                            context.startActivity(new Intent(context, FollowingActivity.class));
                            Functions.animNext(context);

                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });


        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = "android.resource://" + context.getPackageName() + "/" + R.raw.dummy_two; //should be local path of downloaded video

                ContentValues content = new ContentValues(4);
                content.put(MediaStore.Video.VideoColumns.DATE_ADDED,
                        System.currentTimeMillis() / 1000);
                content.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
                content.put(MediaStore.Video.Media.DATA, path);

                ContentResolver resolver = context.getContentResolver();
                Uri uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content);

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("video/*");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Athletics App");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "This is video description");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(Intent.createChooser(sharingIntent, "Share Video"));


            }
        });

        holder.Tv_Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, FollowingActivity.class));
                Functions.animNext(context);

            }
        });

        holder.Tv_UserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, FollowingActivity.class));
                Functions.animNext(context);

            }
        });

        holder.iv_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, FollowingActivity.class));
                Functions.animNext(context);

            }
        });

        holder.imgFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VideoViewActivity.class).putExtra("fullScreenInd", "y"));
                Functions.animNext(context);
            }
        });


//        Glide.with(context).load(bean.getBanner()).into(holder.iv_ProductCategory);


    }


    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private ImageView iv_User, imgLike, imgShare, ImgMenu, imgFullscreen;
        private TextView Tv_Username, TvLikeCount, TvShareCount, Tv_UserType;
        private LinearLayout LLUserProfile;
        private VideoView simpleVideoView;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            iv_User = itemView.findViewById(R.id.iv_User);
            Tv_Username = itemView.findViewById(R.id.Tv_Username);
            Tv_UserType = itemView.findViewById(R.id.Tv_UserType);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgShare = itemView.findViewById(R.id.imgShare);
            ImgMenu = itemView.findViewById(R.id.ImgMenu);
            imgFullscreen = itemView.findViewById(R.id.imgFullscreen);
            TvLikeCount = itemView.findViewById(R.id.TvLikeCount);
            TvShareCount = itemView.findViewById(R.id.TvShareCount);
            LLUserProfile = itemView.findViewById(R.id.LLUserProfile);
            simpleVideoView = itemView.findViewById(R.id.simpleVideoView);

        }
    }
}
