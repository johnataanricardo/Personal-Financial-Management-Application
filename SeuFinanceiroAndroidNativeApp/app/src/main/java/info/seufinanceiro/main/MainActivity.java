package info.seufinanceiro.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import info.seufinanceiro.R;
import info.seufinanceiro.fragments.AccountFragment;
import info.seufinanceiro.fragments.CategoriesFragment;
import info.seufinanceiro.fragments.HomeFragment;
import info.seufinanceiro.login.Login;
import info.seufinanceiro.model.Token;
import info.seufinanceiro.model.User;
import info.seufinanceiro.service.HttpClientService;
import info.seufinanceiro.service.HttpClientServiceCreator;
import info.seufinanceiro.service.SharedPreferencesService;
import info.seufinanceiro.service.TabContentService;
import info.seufinanceiro.utils.ResponseDataSimple;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.ContentTab {

    private TabContentService service;
    private TabLayout tabLayout;
    private Integer month = new Date().getMonth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.month_tab);

        Bundle bundle = new Bundle();
        bundle.putInt("tab", month);
        Fragment fragment = new HomeFragment(tabLayout);
        fragment.setArguments(bundle);
        setFragment(fragment);

        setTabLayoutListener(tabLayout);
        service = new TabContentService(getApplicationContext(), findViewById(android.R.id.content));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        makeCallUserName();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        TabLayout tabLayout = findViewById(R.id.month_tab);

        if (id == R.id.home) {
            tabLayout.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putInt("tab", month);
            fragment = new HomeFragment(this.tabLayout);
            fragment.setArguments(bundle);
        } else if (id == R.id.account) {
            tabLayout.setVisibility(View.GONE);
            fragment = new AccountFragment();
        } else if (id == R.id.category) {
            tabLayout.setVisibility(View.GONE);
            fragment = new CategoriesFragment();
        }

        setFragment(fragment);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setTabLayoutListener(final TabLayout tabLayoutListener) {
        tabLayoutListener.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                service.setContentTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                service.setContentTab(tab.getPosition());
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    private void makeCallUserName() {
        SharedPreferencesService sharedPreferencesService = new SharedPreferencesService(getBaseContext());
        final Token token = new Token(sharedPreferencesService.getToken());
        HttpClientService service = HttpClientServiceCreator.createService(HttpClientService.class);
        Call<ResponseDataSimple<User>> callUser = service.getUser(token.getToken());
        callUser.enqueue(new Callback<ResponseDataSimple<User>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDataSimple<User>> call, @NonNull Response<ResponseDataSimple<User>> response) {
                if (response.isSuccessful()) {
                    TextView txtNameView = findViewById(R.id.user_name);
                    txtNameView.setText(response.body().getData().getName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDataSimple<User>> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void setContentTab(Integer tab) {
        tabLayout.getTabAt(month).select();
    }

}
