package cn.joy.android.demo.arcgis;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnStatusChangedListener.STATUS;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.TextSymbol;
import com.esri.core.tasks.ags.geocode.Locator;
import com.esri.core.tasks.ags.geocode.LocatorFindParameters;
import com.esri.core.tasks.ags.geocode.LocatorGeocodeResult;
import cn.joy.android.R;

public class LocateAddressActivity extends Activity {
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

		handler = new Handler();
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
                	Intent intent = LocateAddressActivity.this.getIntent();
            		String address = intent.getExtras().getString("address");
            		queryByAddressName(address);
                }
                if (status.equals(STATUS.LAYER_LOADED)) {
                	System.out.println("图层加载完成");
                }

                if (status.equals(STATUS.LAYER_LOADING_FAILED)) {
                	System.out.println("图层加载失败");
                }
            }
        });
		
	
		/*Button btn = (Button)this.findViewById(R.id.locate);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = LocateAddressActivity.this.getIntent();
				String address = intent.getExtras().getString("address");
				queryByAddressName(address);
			}
		});*/
		
	}
	/*
	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("LocateAddressActivity onNewIntent");
		super.onNewIntent(intent);
	}
	
	@Override
	protected void onPause() {
		System.out.println("LocateAddressActivity onPause");
		super.onPause();
		mMapView.pause();
	}

	@Override
	protected void onResume() {
		System.out.println("LocateAddressActivity onResume");
		super.onResume();
		
		mMapView.unpause();
		
	}
	
	@Override
	protected void onDestroy() {
		System.out.println("LocateAddressActivity onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onStart() {
		System.out.println("LocateAddressActivity onStart");
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		System.out.println("LocateAddressActivity onStop");
		super.onStop();
	}
*/
	public void queryByAddressName(String addressName) {
		try {
			System.out.println("queryByAddressName, address=" + addressName);
			// create Locator parameters from single line address string
			LocatorFindParameters findParams = new LocatorFindParameters(addressName);
			// set the search country to USA
			findParams.setSourceCountry("CHN");
			// limit the results to 2
			findParams.setMaxLocations(5);
			// set address spatial reference to match map
			System.out.println("###"+mMapView.getSpatialReference());
			findParams.setOutSR(mMapView.getSpatialReference());
			// execute async task to geocode address
			new Geocoder().execute(findParams);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class Geocoder extends AsyncTask<LocatorFindParameters, Void, List<LocatorGeocodeResult>> {
		// The result of geocode task is passed as a parameter to map the
		// results
		protected void onPostExecute(List<LocatorGeocodeResult> result) {
			if (result == null || result.size() == 0) {
				// update UI with notice that no results were found
				Toast toast = Toast.makeText(LocateAddressActivity.this, "No result found.", Toast.LENGTH_LONG);
				toast.show();
			} else {
				//Toast toast = Toast.makeText(LocateAddressActivity.this, result.size()+" result found.", Toast.LENGTH_LONG);
				//toast.show();
				// show progress dialog while geocoding address
				dialog = ProgressDialog.show(mMapView.getContext(), "Arcgis Geocoder", "Searching for address ...");
				// get return geometry from geocode result
				Geometry resultLocGeom = result.get(0).getLocation();
				
				/*Point mapPoint = (Point) GeometryEngine.project(resultLocGeom,
						mMapView.getSpatialReference(), SpatialReference.create(4326));
				System.out.println(mapPoint.getX());
				System.out.println(mapPoint.getY());*/
				// create marker symbol to represent location
				SimpleMarkerSymbol resultSymbol = new SimpleMarkerSymbol(Color.BLUE, 20,
						SimpleMarkerSymbol.STYLE.CIRCLE);
				// create graphic object for resulting location
				Graphic resultLocation = new Graphic(resultLocGeom, resultSymbol);
				// add graphic to location layer
				gLayer.addGraphic(resultLocation);
				// create text symbol for return address
				/*Drawable image = GraphicsLayerDemoActivity.this.getBaseContext()
		                .getResources().getDrawable(R.drawable.pop);
		        PictureMarkerSymbol symbol = new PictureMarkerSymbol(image);*/
				TextSymbol resultAddress = new TextSymbol(14, result.get(0).getAddress(), Color.BLACK);
				// create offset for text
				resultAddress.setOffsetX(10);
				resultAddress.setOffsetY(25);
				// create a graphic object for address text
				Graphic resultText = new Graphic(resultLocGeom, resultAddress);
				// add address text graphic to location graphics layer
				gLayer.addGraphic(resultText);
				// zoom to geocode result
				mMapView.zoomToScale(result.get(0).getLocation(), 600000);
				// mMapView.zoomToResolution(result.get(0).getLocation(), 2);
				// create a runnable to be added to message queue
				handler.post(new Runnable() {
					public void run() {
						dialog.dismiss();
					}
				});
			}
		}

		// invoke background thread to perform geocode task
		@Override
		protected List<LocatorGeocodeResult> doInBackground(LocatorFindParameters... params) {
			// get spatial reference from map
			SpatialReference sr = mMapView.getSpatialReference();
			// create results object and set to null
			List<LocatorGeocodeResult> results = null;
			// set the geocode service
			locator = new Locator(getResources().getString(R.string.arcgis_geocode_url));
			try {
				// pass address to find method to return point representing
				// address
				results = locator.find(params[0]);
				
				for(LocatorGeocodeResult result:results){
					System.out.println(result.getAddress()+"--"+result.getLocation().getX()+"--"+result.getLocation().getY());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			// return the resulting point(s)
			return results;
		}
	}

}
