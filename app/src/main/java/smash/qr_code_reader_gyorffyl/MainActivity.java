package smash.qr_code_reader_gyorffyl;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {

    Response[] responseObj;
    String url="http://10.0.2.2/projects/qr_code_reader/"; //localhost elérése
    String query = "query.php"; // localhoston lévő fájl, mely a JSON kimenetért felelős
    String pictures = "pictures/"; // képek ebben a mappában találhatóak meg
    Gson gson; //JSON fordító
    AsyncHttpClient client; //Aszinkron http kliens kapcsolatot valósít meg
    String contents=""; // QR kód tartalma
    TextView tv_name, tv_price, tv_pic;
    ImageView imageView;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_name = (TextView)(findViewById(R.id.tv_name));
        tv_price = (TextView)(findViewById(R.id.tv_price));
        tv_pic = (TextView)(findViewById(R.id.tv_pic));
        client = new AsyncHttpClient(true, 80, 443);
        imageView = (ImageView)(findViewById(R.id.imageView));
    }

    public void scanQR(View v) {

     try{

        Intent intent = new Intent(new IntentIntegrator(this).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES).createScanIntent());
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {

    }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                valasz();
                toast.show();
            }
        }
    }

    public void valasz() {
        client.get(MainActivity.this, url + query, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseStr = new String(responseBody);
                gson = new Gson();
                responseObj = gson.fromJson(responseStr, Response[].class);
                for (int i = 0; i <= responseObj.length - 1; i++) {
                    if (responseObj[i].getId().equals(contents)) {
                        tv_name.setText(responseObj[i].getItem_name());
                        tv_price.setText(responseObj[i].getItem_price() +"€");
                        tv_pic.setText(url + pictures + responseObj[i].getItem_picture()); // ez a field csak ellenőrzés miatt jelenik meg
                        Picasso.with(getApplicationContext()).load(url + pictures + responseObj[i].getItem_picture()).into(imageView); //a kép megjelenítéséért felelős
                    }

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
