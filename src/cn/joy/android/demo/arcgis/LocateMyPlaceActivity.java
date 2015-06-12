package cn.joy.android.demo.arcgis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnStatusChangedListener.STATUS;
import com.esri.core.tasks.ags.geocode.Locator;
import cn.joy.android.R;

public class LocateMyPlaceActivity extends Activity {
	private MapView mMapView;
	private GraphicsLayer gLayer;
	private LocationManager locMag;
	private Locator locator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arcgis_map);

		mMapView = (MapView) findViewById(R.id.map);
		gLayer = new GraphicsLayer();

		mMapView.addLayer(new ArcGISTiledMapServiceLayer(getResources().getString(R.string.arcgis_basemap_url)));
		mMapView.addLayer(gLayer);

		//Envelope initextext = new Envelope(12891659.0975195, 4817561.93785559, 12996377.82627, 4884902.95977474);
		//mMapView.setExtent(initextext);
		//mMapView.setEsriLogoVisible(true);
		
		mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {

            @Override
            public void onStatusChanged(Object source, STATUS status) {
                if (status.equals(STATUS.INITIALIZATION_FAILED)) {
                    System.out.println("初始化失败");
                }

                if (status.equals(STATUS.INITIALIZED)) {
                	System.out.println("初始化完成");
                }
                if (status.equals(STATUS.LAYER_LOADED)) {
                	System.out.println("图层加载完成");
                }

                if (status.equals(STATUS.LAYER_LOADING_FAILED)) {
                	System.out.println("图层加载失败");
                }
            }
        });
	}

	
}
