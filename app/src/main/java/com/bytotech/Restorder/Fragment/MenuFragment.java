package com.bytotech.Restorder.Fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bytotech.Restorder.Activity.MenuDetailListActivity;
import com.bytotech.Restorder.Activity.MenuSubCategoryListActivity;
import com.bytotech.Restorder.Adapter.MenuAdapter;
import com.bytotech.Restorder.CommonClass.CommonClass;
import com.bytotech.Restorder.CommonClass.Constant;
import com.bytotech.Restorder.CommonClass.PreferenceUtils;
import com.bytotech.Restorder.R;
import com.bytotech.Restorder.WS.Response.BaseAppBannerResponse;
import com.bytotech.Restorder.WS.Response.ResponseMenu;
import com.bytotech.Restorder.WS.Response.SubCategoryResponse;
import com.bytotech.Restorder.WS.RestApi;
import com.bytotech.Restorder.WS.eRestroAPI;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Pradip Bhuva
 * Bytotech Solutions
 * +91 8866036909
 */
@SuppressLint("ValidFragment")
public class MenuFragment extends Fragment {
	
	private View view;
	private CommonClass cc;
	private PreferenceUtils preferenceUtils;
	private RelativeLayout progressbar;
	private RecyclerView rvMenu;
	private TextView tvNoData;
	private List<ResponseMenu.MenucategoryList> menucategoryLists;
	private List<SubCategoryResponse> subCategoryResponseList;
	private MenuAdapter menuAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_menu, container, false);
		
		cc = new CommonClass(getActivity());
		preferenceUtils = new PreferenceUtils(getActivity());
		AdView adView = view.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		if (preferenceUtils.adCount() == 5) {
			preferenceUtils.setadCount(1);
			cc.showFullAd();
		} else {
			int count = preferenceUtils.adCount() + 1;
			preferenceUtils.setadCount(count);
		}
		tvNoData = view.findViewById(R.id.tvNoData);
		progressbar = view.findViewById(R.id.progressbar);
		rvMenu = view.findViewById(R.id.rvMenu);
		rvMenu.setNestedScrollingEnabled(false);
		rvMenu.setHasFixedSize(false);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		rvMenu.setLayoutManager(layoutManager);
		if (cc.isOnline()) {
			getMenuList();
			getBanner();
		} else {
			cc.showToast(getString(R.string.msg_no_internet));
		}

		return view;
	}

	private void showBanner(String bannerUrl) {

		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.dailog_image);

		ImageView imgClose=dialog.findViewById(R.id.imgClose);
		ImageView imgBanner = (ImageView) dialog.findViewById(R.id.imgBanner);
		Glide.with(getActivity())
				.load(bannerUrl)
				.into(imgBanner);


		imgClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}


	public void getBanner() {
		progressbar.setVisibility(View.VISIBLE);
		eRestroAPI dawaAPI = RestApi.createAPI();
		Call<BaseAppBannerResponse> call = dawaAPI.getBanner();
		call.enqueue(new Callback<BaseAppBannerResponse>() {
			@Override
			public void onResponse(Call<BaseAppBannerResponse> call, Response<BaseAppBannerResponse> response) {
				if (response.isSuccessful()) {
					progressbar.setVisibility(View.GONE);
					showBanner(response.body().getAppBannerResponse().get(0).getAppBanner());


				} else {
					progressbar.setVisibility(View.GONE);
					cc.showToast(getString(R.string.msg_something_went_wrong));
				}
			}

			@Override
			public void onFailure(Call<BaseAppBannerResponse> call, Throwable t) {
				progressbar.setVisibility(View.GONE);
				cc.showToast(getString(R.string.msg_something_went_wrong));
				t.printStackTrace();
			}
		});
	}

	public void getMenuList() {
		progressbar.setVisibility(View.VISIBLE);
		eRestroAPI dawaAPI = RestApi.createAPI();
		Call<ResponseMenu> call = dawaAPI.getMenuList();
		call.enqueue(new Callback<ResponseMenu>() {
			@Override
			public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
				if (response.isSuccessful()) {
					progressbar.setVisibility(View.GONE);
					if (response.body().code == Constant.Response_OK) {
						menucategoryLists = new ArrayList<>();
						menucategoryLists = response.body().menucategory_list;

						menuAdapter = new MenuAdapter(getActivity(), menucategoryLists);
						rvMenu.setAdapter(menuAdapter);
						
						cc.AnimationLeft(rvMenu);
						
						if (menucategoryLists.size() == 0) {
							tvNoData.setVisibility(View.VISIBLE);
							rvMenu.setVisibility(View.GONE);
						} else {
							tvNoData.setVisibility(View.GONE);
							rvMenu.setVisibility(View.VISIBLE);
						}
						
						menuAdapter.setOnDetailClick(new MenuAdapter.OnDetailsClick() {
							@Override
							public void onDetailClick(int position) {
								subCategoryResponseList=menucategoryLists.get(position).subcategory;
								Intent intent = new Intent(getActivity(), MenuSubCategoryListActivity.class);
								intent.putExtra("id", menucategoryLists.get(position).cid);
								intent.putExtra("subcategory", (Serializable) subCategoryResponseList);
								ActivityOptions options =
									   ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in, R.anim.slide_out);
								startActivity(intent, options.toBundle());
								
							}
						});
					} else {
						cc.showToast(response.body().message);
					}
				} else {
					progressbar.setVisibility(View.GONE);
					cc.showToast(getString(R.string.msg_something_went_wrong));
				}
			}
			
			@Override
			public void onFailure(Call<ResponseMenu> call, Throwable t) {
				progressbar.setVisibility(View.GONE);
				cc.showToast(getString(R.string.msg_something_went_wrong));
				t.printStackTrace();
			}
		});
	}
}
