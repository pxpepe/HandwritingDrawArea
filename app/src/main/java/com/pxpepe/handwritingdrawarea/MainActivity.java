package com.pxpepe.handwritingdrawarea;

import android.app.VoiceInteractor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DrawingView dv;
    private Paint mPaint;

    JSONArray listaInk;
    JSONArray lx;
    JSONArray ly;
    JSONArray lt;
    long tempoInicio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaInk = new JSONArray();
        dv = new DrawingView(this);
        setContentView(R.layout.activity_main);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        frameLayout.addView(dv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
    }

    private JSONObject getRequestJson() {

        JSONObject jsonObject = new JSONObject();

        TextView textView = findViewById(R.id.textView);
        String preContext = textView.getText().toString();

        try {

            JSONObject wrintingGuide = new JSONObject();
            wrintingGuide.put("writing_area_width", dv.getWidth());
            wrintingGuide.put("writing_area_height", dv.getHeight());

            JSONObject requestObj = new JSONObject();
            requestObj.put("writing_guide", wrintingGuide);
            //requestObj.put("pre_context", preContext);
            requestObj.put("pre_context", "0123456789");
            requestObj.put("max_num_results", 10);
            requestObj.put("max_completions", 0);
            requestObj.put("language", "pt-PT");

            requestObj.put("ink", listaInk);

            JSONArray requests = new JSONArray();
            requests.put(requestObj);

            jsonObject.put("app_version", 0.4);
            jsonObject.put("api_level", "537.36");
            jsonObject.put("device", "5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
            jsonObject.put("input_type", 0);
            jsonObject.put("options", "enable_pre_space");
            jsonObject.put("requests", requests);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    private void requestInputHandwriting() {

        String params = "itc=pt-pt-t-i0-handwrit&app=demopage";

        String url = "https://inputtools.google.com/request?"+params;

        JsonArrayRequest jsonArrayRequest;

        JsonObArrRequest jsonObjReq = new JsonObArrRequest(
                Request.Method.POST, url, getRequestJson(),
                new Response.Listener<JSONArray>() {
                    public void onResponse(JSONArray response) {
                        Log.d("resp", response.toString());
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("My App","Error: " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjReq);

    }



    private void guardarPosLetra(float x, float y) {
        try {
            lx.put(x);
            ly.put(y);

            if (tempoInicio==0) {
                lt.put(0);
                tempoInicio = Calendar.getInstance().getTimeInMillis();
            } else {
                long tempoAtual = Calendar.getInstance().getTimeInMillis();
                lt.put(tempoAtual-tempoInicio);
                tempoInicio=tempoAtual;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void comecarNovaLetra(float x, float y) {
        // Criamos as variáveis para enviar no ink
        lx = new JSONArray();
        ly = new JSONArray();
        lt = new JSONArray();
        JSONArray letraAct = new JSONArray();
        listaInk.put(letraAct);
        letraAct.put(lx);
        letraAct.put(ly);
        letraAct.put(lt);
        guardarPosLetra(x,y);

    }



    public class DrawingView extends View {

        public int width;
        public int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
            canvas.drawPath(circlePath, circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
            comecarNovaLetra(x,y);
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
                guardarPosLetra(x,y);

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            requestInputHandwriting();
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }

}
