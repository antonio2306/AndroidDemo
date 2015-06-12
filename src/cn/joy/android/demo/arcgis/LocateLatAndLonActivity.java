package cn.joy.android.demo.arcgis;

import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.TextSymbol;
import com.esri.core.tasks.ags.geocode.Locator;
import com.esri.core.tasks.ags.geocode.LocatorReverseGeocodeResult;
import cn.joy.android.R;

public class LocateLatAndLonActivity extends Activity {
	private MapView mMapView;
	private GraphicsLayer gLayer;
	private LocationManager locMag;
	private Locator locator;
	static ProgressDialog dialog;
	private static Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arcgis_map);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.arcgis_map);

		handler = new Handler();
		mMapView = (MapView) findViewById(R.id.map);
		gLayer = new GraphicsLayer();

		mMapView.addLayer(new ArcGISTiledMapServiceLayer(getResources().getString(R.string.arcgis_basemap_url)));
		mMapView.addLayer(gLayer);

		Envelope initextext = new Envelope(10891659.0975195, 3817561.93785559, 13996377.82627, 5884902.95977474);
		mMapView.setExtent(initextext);
		//mMapView.setEsriLogoVisible(true);
		
		mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {

            @Override
            public void onStatusChanged(Object source, STATUS status) {
                if (status.equals(STATUS.INITIALIZATION_FAILED)) {
                    System.out.println("初始化失败");
                }

                if (status.equals(STATUS.INITIALIZED)) {
                	System.out.println("初始化完成");
                	Intent intent = LocateLatAndLonActivity.this.getIntent();
                	Double lat = intent.getExtras().getDouble("lat");
                	Double lon = intent.getExtras().getDouble("lon");
            		queryByLatAndLon(lon, lat);
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
	
	public void queryByLatAndLon(Double lon, Double lat){
		System.out.println("lon:"+lon+", lat:"+lat);
		Point wgspoint = new Point(lon, lat);
		
		SpatialReference sr = mMapView.getSpatialReference();
		Point mapPoint = (Point) GeometryEngine.project(wgspoint, SpatialReference.create(4326),
				sr);
		
		SimpleMarkerSymbol resultSymbol = new SimpleMarkerSymbol(Color.BLUE, 20,
				SimpleMarkerSymbol.STYLE.CIRCLE);
		// create graphic object for resulting location
		Graphic resultLocation = new Graphic(mapPoint, resultSymbol);
		// add graphic to location layer
		gLayer.addGraphic(resultLocation);
		
		LocatorReverseGeocodeResult result = null;
		String address = "";
		try {
			locator = new Locator(getResources().getString(R.string.arcgis_geocode_url));
            // 使其变成反地理编码并进行查询
			result = locator.reverseGeocode(mapPoint, 100.00, mMapView.getSpatialReference(), mMapView.getSpatialReference());
			//result = locator.reverseGeocode(wgspoint, 100.00, SpatialReference.create(4326), SpatialReference.create(4326));
			Map<String, String> m = result.getAddressFields();
			/*System.out.println(m );
			Set<String> ks = m.keySet();
			for(String k:ks){
				System.out.println(k+"--"+m.get(k));
			}*/
			address = m.get("Address");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		TextSymbol resultAddress = new TextSymbol(14, address, Color.BLACK);
		// create offset for text
		resultAddress.setOffsetX(10);
		resultAddress.setOffsetY(25);
		// create a graphic object for address text
		Graphic resultText = new Graphic(mapPoint, resultAddress);
		// add address text graphic to location graphics layer
		gLayer.addGraphic(resultText);
		// zoom to geocode result
		mMapView.zoomToScale(mapPoint, 1000000);
	}

	
}
