package cn.joy.android.demo.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MyHostnameVerifier  implements HostnameVerifier{
	@Override
    public boolean verify(String hostname, SSLSession session) {
          return true;
    }
}
