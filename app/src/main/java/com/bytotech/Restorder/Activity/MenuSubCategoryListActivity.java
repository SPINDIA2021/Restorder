package com.bytotech.Restorder.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytotech.Restorder.Adapter.MenuAdapter;
import com.bytotech.Restorder.Adapter.MenuDetailAdapter;
import com.bytotech.Restorder.Adapter.SubMenuAdapter;
import com.bytotech.Restorder.CommonClass.CommonClass;
import com.bytotech.Restorder.CommonClass.Constant;
import com.bytotech.Restorder.CommonClass.PreferenceUtils;
import com.bytotech.Restorder.R;
import com.bytotech.Restorder.WS.Response.ResponseMenuDetail;
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
public class MenuSubCategoryListActivity extends AppCompatActivity {
	private CommonClass cc;
	private PreferenceUtils preferenceUtils;
	private RelativeLayout progressbar;
	private RecyclerView rvMenu;
	private TextView tvNoData;
	private SubMenuAdapter subMenuAdapter;
	private String id;
	private ImageView ivBack, ivCart;
	ArrayList<SubCategoryResponse> subCategoryResponses;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_detail_list);
		
		cc = new CommonClass(this);
		preferenceUtils = new PreferenceUtils(this);
		id = getIntent().getStringExtra("id");
		subCategoryResponses=(ArrayList<SubCategoryResponse>) getIntent().getSerializableExtra("subcategory");
		if (preferenceUtils.getCartCount().equals("") || preferenceUtils.getCartCount().equals("0")) {
			findViewById(R.id.tvCartCount).setVisibility(View.GONE);
		} else {
			findViewById(R.id.tvCartCount).setVisibility(View.VISIBLE);
		}
		
		AdView adView = findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		if (preferenceUtils.adCount() == 5) {
			preferenceUtils.setadCount(1);
			cc.showFullAd();
		} else {
			int count = preferenceUtils.adCount() + 1;
			preferenceUtils.setadCount(count);
		}
		
		((TextView) findViewById(R.id.tvCartCount)).setText(preferenceUtils.getCartCount());
		tvNoData = findViewById(R.id.tvNoData);
		ivBack = findViewById(R.id.ivBack);
		ivCart = findViewById(R.id.ivCart);
		progressbar = findViewById(R.id.progressbar);
		rvMenu = findViewById(R.id.rvMenu);
		rvMenu.setNestedScrollingEnabled(false);
		rvMenu.setHasFixedSize(false);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		rvMenu.setLayoutManager(layoutManager);


		subMenuAdapter = new SubMenuAdapter(MenuSubCategoryListActivity.this, subCategoryResponses);
		rvMenu.setAdapter(subMenuAdapter);
		cc.AnimationLeft(rvMenu);
		if (subCategoryResponses.size() == 0) {
			tvNoData.setVisibility(View.VISIBLE);
			rvMenu.setVisibility(View.GONE);
		} else {
			tvNoData.setVisibility(View.GONE);
			rvMenu.setVisibility(View.VISIBLE);
		}

		subMenuAdapter.setOnDetailClick(new SubMenuAdapter.OnDetailsClick() {
			@Override
			public void onDetailClick(int position) {

				Intent intent = new Intent(MenuSubCategoryListActivity.this, MenuDetailListActivity.class);
				intent.putExtra("id", subCategoryResponses.get(position).getCid());
				ActivityOptions options =
						ActivityOptions.makeCustomAnimation(MenuSubCategoryListActivity.this, R.anim.slide_in, R.anim.slide_out);
				startActivity(intent, options.toBundle());

			}
		});

		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		ivCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cc.startCartActivity();
			}
		});
	}
	

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		
	}
	
	@Override
	protected void onResume() {
		preferenceUtils = new PreferenceUtils(this);
		if (preferenceUtils.getCartCount().equals("") || preferenceUtils.getCartCount().equals("0")) {
			findViewById(R.id.tvCartCount).setVisibility(View.GONE);
		} else {
			findViewById(R.id.tvCartCount).setVisibility(View.VISIBLE);
		}
		((TextView) findViewById(R.id.tvCartCount)).setText(preferenceUtils.getCartCount());
		super.onResume();
	}
}
