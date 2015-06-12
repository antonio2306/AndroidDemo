package cn.joy.android.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import cn.joy.android.R;
import cn.joy.android.demo.arcgis.LocateAddressActivity;
import cn.joy.android.demo.arcgis.LocateLatAndLonActivity;

public class TestArcgisActivity extends Activity {
	private LocationManager locMag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arcgis);


	}

	public void locateByAddress(View source){
    	Intent intent = new Intent(getApplicationContext(), LocateAddressActivity.class);
    	String address = ((EditText)this.findViewById(R.id.address)).getText().toString();
    	intent.putExtra("address", address);
		startActivity(intent);
    }
	
	public void locateByLatAndLon(View source){
    	Intent intent = new Intent(getApplicationContext(), LocateLatAndLonActivity.class);
    	Double lat = Double.parseDouble( ((EditText)this.findViewById(R.id.lat)).getText().toString() );
    	Double lon = Double.parseDouble( ((EditText)this.findViewById(R.id.lon)).getText().toString() );
    	intent.putExtra("lat", lat);
    	intent.putExtra("lon", lon);
		startActivity(intent);
    }
	
	public void locateMyPlace(View source){
		locMag = (LocationManager) TestArcgisActivity.this.getSystemService(Context.LOCATION_SERVICE);// 创建LocationManager的唯一方法

		String provider = LocationManager.NETWORK_PROVIDER;
		Location loc = locMag.getLastKnownLocation(provider);
		if (loc == null) {
			provider = LocationManager.NETWORK_PROVIDER;
			loc = locMag.getLastKnownLocation(provider);
		}

		LocationListener locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				// textView.setText("位置发生变化，新位置：　" + location.getLatitude() +
				// "  ,  " + location.getLongitude());
				//System.out.println("位置发生变化，新位置：　" + location.getLatitude() + "  ,  " + location.getLongitude());
				// 刷新图层
				//markLocation(location);
			}

			@Override
			public void onProviderDisabled(String arg0) {
			}

			@Override
			public void onProviderEnabled(String arg0) {
			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			}
		};

		locMag.requestLocationUpdates(provider, 100, 0, locationListener);
		loc = locMag.getLastKnownLocation(provider);
		if (loc != null) {
			double latitude = loc.getLatitude();
			double longitude = loc.getLongitude();
			System.out.println("位置：　" + loc.getLatitude() + "  ,  " + loc.getLongitude());
			EditText lat = (EditText)this.findViewById(R.id.lat);
			lat.setText(latitude+"");
			EditText lon = (EditText)this.findViewById(R.id.lon);
			lon.setText(longitude+"");
		}
    }
}
