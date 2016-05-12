package Network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.findskilled.findskilled.MyApplication;

/**
 * Created by lenovo on 16-03-2016.
 */
//volleysingleton class is used so as to access this class for volley implementation
public class VolleySingleton {
    //reference to class object

    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;
    private ImageLoader mimageLoader;
    //making private so that no other class can use our volley class
    private VolleySingleton()
    {
    mRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
        mimageLoader=new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            //to hold the caching mechanism;
            //cach that hold strong referance to the limited number of values
            //to get the size in runtime maxMemory java function
            private LruCache<String,Bitmap> cache=new LruCache<>((int) (Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
    }
//    static method to return the instance of our class volley singleton
//    this method will return the object of type Volleysingleton
    public static VolleySingleton getInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }
//    method will RETURN RequestQueue so as to other class can use it
public RequestQueue getRequestQueue(){
    return mRequestQueue;
}
    //method will return the ImageLoader so as to other class can use it after RequestQueue
    public ImageLoader getImageLoader(){
        return mimageLoader;
    }

}
