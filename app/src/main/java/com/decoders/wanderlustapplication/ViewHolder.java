package com.decoders.wanderlustapplication;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mview;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview=itemView;
    }
    public void setDetails(Context ctx, String title, String desc, String image)
    {
        TextView mdesc=mview.findViewById(R.id.post_des);
        TextView mtitle = mview.findViewById(R.id.post_title);
        ImageView mimage = mview.findViewById(R.id.post_image);
        mdesc.setText(desc,TextView.BufferType.SPANNABLE);
        mtitle.setText(title);
        Picasso.get().load(image).into(mimage);
    }


}