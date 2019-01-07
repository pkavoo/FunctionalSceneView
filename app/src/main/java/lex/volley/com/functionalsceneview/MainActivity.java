package lex.volley.com.functionalsceneview;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.SceneView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private SceneView sv;
    private DrawerLayout myDrawerLayout;
    private ListView myLeftDrawerList;
    String[] cities = {
            "New York",
            "Chicago",
            "Denver",
            "Detroit",
            "Las Vegas",
            "Paris"
    };
    private ActionBarDrawerToggle myToggle;
    String[] basemapNames;
    ArcGISScene myScene;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDrawerLayout = findViewById(R.id.drawer_layout);
        myLeftDrawerList = findViewById(R.id.left_drawer);

        myDrawerLayout.addDrawerListener(myToggle);
        myScene = new ArcGISScene(Basemap.createImagery());
        sv = findViewById(R.id.map1);
        ArcGISScene myScene = new ArcGISScene(Basemap.createImagery());
        sv.setScene(myScene);
        ArcGISTiledElevationSource myElevation = new ArcGISTiledElevationSource(getString(R.string.elevation3dUrl));
        myScene.getBaseSurface().getElevationSources().add(myElevation);

        Camera myCamera = new Camera(35.59520,138.78045,2719.97086,195.47934,79.357552,0.0);
        sv.setViewpointCamera(myCamera);

        basemapNames = getResources().getStringArray(R.array.basemaps);
        myLeftDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                basemapNames));
        myLeftDrawerList.setOnItemClickListener(this);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        configureToggle();

    }

    @Override
    protected void onPause(){
        super.onPause();
        sv.pause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        sv.resume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return (myToggle.onOptionsItemSelected(item)) || super.onOptionsItemSelected(item);
    }

    private void configureToggle() {
        myToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.open, R.string.close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        myToggle.setDrawerIndicatorEnabled(true);
        myToggle.syncState();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        switch (i) {
            case 0:
                myScene.setBasemap(Basemap.createStreets());
                break;
            case 1:
                myScene.setBasemap(Basemap.createImagery());
                break;
            case 2:
                myScene.setBasemap(Basemap.createTopographic());
                break;
            case 3:
                myScene.setBasemap(Basemap.createOpenStreetMap());
                break;
        }
        Toast.makeText(getApplicationContext(), "This basemap is selected as: " + basemapNames[i],
                Toast.LENGTH_LONG).show();
    }
}