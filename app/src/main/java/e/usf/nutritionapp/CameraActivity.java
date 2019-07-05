package e.usf.nutritionapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.camerakit.CameraKitView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    final String TAG = "Camera";



    private CameraKitView cameraKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraKitView = (CameraKitView) findViewById(R.id.camera);

        cameraKitView.setGestureListener(new CameraKitView.GestureListener() {
            @Override
            public void onTap(CameraKitView cameraKitView, float v, float v1) {
                Log.d(TAG, "onTap");
                getBarcode(cameraKitView);

            }

            @Override
            public void onLongTap(CameraKitView cameraKitView, float v, float v1) {
                Log.d(TAG, "onLongTap");
            }

            @Override
            public void onDoubleTap(CameraKitView cameraKitView, float v, float v1) {
                Log.d(TAG, "onDoubleTap");
            }

            @Override
            public void onPinch(CameraKitView cameraKitView, float v, float v1, float v2) {
                Log.d(TAG, "onPinch");
            }
        });
    }

    private void getBarcode(CameraKitView cameraKitView){

        final BarcodeAPI BCApi = new BarcodeAPI();

        FirebaseVisionBarcodeDetectorOptions options =
                new FirebaseVisionBarcodeDetectorOptions.Builder()
                        .setBarcodeFormats(
                                FirebaseVisionBarcode.FORMAT_UPC_E,
                                FirebaseVisionBarcode.FORMAT_UPC_A)
                        .build();

        final FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options);

        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(
                        BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.length));
                detector.detectInImage(image)
                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                                // Task completed successfully
                                for(FirebaseVisionBarcode barcode: barcodes){
                                    BCApi.execute(barcode.getDisplayValue());
                                    Log.d(TAG, "Finished execution");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                Log.d(TAG, "Failed");
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private class BarcodeAPI extends AsyncTask<String, String, String> {

        final String TAG = "BarcodAPI";
        public String finalResult;

        public class RootObject
        {
            public ProductInfo[] products;
        }

        public String doInBackground(String... barcode)
        {
            try
            {
                Log.d(TAG, "Looking up...");
                Log.d(TAG, "Barcode = " + barcode[0]);
                URL url = new URL("https://api.barcodelookup.com/v2/products?barcode="
                        + barcode[0] +"&formatted=y&key=btplha4k3db85us01bf8740f9h5c77");
                Log.d(TAG, url.toString());
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                Log.d(TAG, "Opened Stream");
                String str = "";
                String data = "";
                while (null != (str= br.readLine())) {
                    data+=str;
                }

                Log.d(TAG, "Read data");

                Gson g = new Gson();

                RootObject value = g.fromJson(data, RootObject.class);

                Log.d(TAG, "Returning...");
                String name = value.products[0].product_name;


                return name;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return "Error: something went wrong";
        }

        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            returnValue(result);
        }


    }

    private void returnValue(String result)
    {
        Log.d(TAG, result);
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, result);
        i.setType("text/plain");
        setResult(CameraActivity.RESULT_OK, i);
        finish();
    }


}
