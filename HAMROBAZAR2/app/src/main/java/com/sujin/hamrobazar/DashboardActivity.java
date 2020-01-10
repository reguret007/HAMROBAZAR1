package com.sujin.hamrobazar;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;
import com.sujin.hamrobazar.adapter.ProductsAdapter;
import com.sujin.hamrobazar.adapter.imgSliderAdapter;
import com.sujin.hamrobazar.api.UsersAPI;
import com.sujin.hamrobazar.model.Product;
import com.sujin.hamrobazar.model.User;
import com.sujin.hamrobazar.url.Url;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotsPanel;
    private int dotscount;
    private ImageView[] dots;

    private CircleImageView imgLogin;

    private RecyclerView rvProduct;
    SwipeRefreshLayout refreshLayout;

    public DashboardActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        rvProduct = findViewById(R.id.rvProduct);
        refreshLayout = findViewById(R.id.refreshLayout);
        showProduct();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProduct();

            }

        });

        loadCurrentUser();


        viewPager = findViewById(R.id.imgSlider);
        imgLogin = findViewById(R.id.imgLogin);

        //For indicators
        sliderDotsPanel = findViewById(R.id.sliderDotsPanel);

        imgSliderAdapter adapter = new imgSliderAdapter(this);
        viewPager.setAdapter(adapter);

        //Indicator
        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactivedots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotsPanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.activedots));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactivedots));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.activedots));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private boolean pagerMoved = false;
    private static final long ANIM_VIEWPAGER_DELAY = 5000;

    private Handler h = new Handler();

    private Runnable animateViewPager = new Runnable() {
        @Override
        public void run() {

            if (!pagerMoved) {
                if (viewPager.getCurrentItem() == viewPager.getChildCount()) {
                    viewPager.setCurrentItem(0, true);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
                pagerMoved = false;
                h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        if (h != null) {
            h.removeCallbacks(animateViewPager);
        }
    }
    //changed

    @Override
    protected void onResume() {
        super.onResume();

        h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void showProduct() {
        refreshLayout.setRefreshing(false);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.base_url).addConverterFactory(GsonConverterFactory.create()).build();
        UsersAPI usersAPI = retrofit.create(UsersAPI.class);

        Call<List<Product>> proListCall = usersAPI.getAllProducts();

        proListCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    refreshLayout.setRefreshing(true);
                    Toast.makeText(DashboardActivity.this, "Error code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Product> ProductList = response.body();
                ProductsAdapter productsAdapter = new ProductsAdapter(DashboardActivity.this, ProductList);
                rvProduct.setAdapter(productsAdapter);
                rvProduct.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.HORIZONTAL, false));


            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Log.d("Msg", "onFailure" + t.getLocalizedMessage());
                Toast.makeText(DashboardActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadCurrentUser () {
        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<User> userCall = usersAPI.getUserDetails(Url.token);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {
                if (!response.isSuccessful()) {
//                    Toast.makeText(DashboardActivity.this, "Code "+response.code(), Toast.LENGTH_SHORT).show();

                    //to show login and registration when user not logged in

                    imgLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                        }
                    });

                } else {
                    String imgPath = Url.imagePath + response.body().getImage();

                    Picasso.get().load(imgPath).into(imgLogin);


                    imgLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(DashboardActivity.this, "Logged in User: " + response.body().getFullname(), Toast.LENGTH_SHORT).show();
                        }

                    });

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                Picasso.get().load(R.drawable.profile).into(imageProfile);
            }

        });

    }
}