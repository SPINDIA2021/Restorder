package com.bytotech.Restorder.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bytotech.Restorder.R;
import com.bytotech.Restorder.WS.Response.ResponseMenu;
import com.bytotech.Restorder.WS.Response.SubCategoryResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Pradip Bhuva
 * Bytotech Solutions
 * +91 8866036909
 */
public class SubMenuAdapter extends RecyclerView.Adapter {

	private List<SubCategoryResponse> memberLists;
	private Context mContext;
	private OnDetailsClick onDetailClick;

	public SubMenuAdapter(Context mContext, List<SubCategoryResponse> memberLists) {
		this.mContext = mContext;
		this.memberLists = memberLists;
		
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
			            .inflate(R.layout.item_menu_list, parent, false);
		return new SubMenuAdapter.MenuViewHolder(v);
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@SuppressLint("CheckResult")
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
		SubMenuAdapter.MenuViewHolder holder = (SubMenuAdapter.MenuViewHolder) viewHolder;
		holder.tvMenuTitle.setText(memberLists.get(position).getCategoryName());
		holder.tvMenuItems.setText(memberLists.get(position).getCname() );

		holder.rating.setVisibility(View.GONE);

		Glide.with(mContext)
			   .load("http://phool.spindiabazaar.com/imagess/"+memberLists.get(position).getCategoryImage())
			   .into(holder.ivMenu);
		
		holder.lay_main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onDetailClick.onDetailClick(position);
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return memberLists.size();
	}
	
	public void setOnDetailClick(OnDetailsClick onDetailClick) {
		this.onDetailClick = onDetailClick;
		
	}
	
	public interface OnDetailsClick {
		void onDetailClick(int position);
	}
	
	private class MenuViewHolder extends RecyclerView.ViewHolder {
		
		TextView tvMenuTitle, tvMenuItems;
		RatingBar rating;
		CircleImageView ivMenu, ivMenuDetail;
		RelativeLayout lay_main;
		
		MenuViewHolder(@NonNull View itemView) {
			super(itemView);
			tvMenuTitle = itemView.findViewById(R.id.tvMenuTitle);
			tvMenuItems = itemView.findViewById(R.id.tvMenuItems);
			rating = itemView.findViewById(R.id.rating);
			ivMenu = itemView.findViewById(R.id.ivMenu);
			ivMenuDetail = itemView.findViewById(R.id.ivMenuDetail);
			lay_main=itemView.findViewById(R.id.lay_main);
			
		}
	}
}