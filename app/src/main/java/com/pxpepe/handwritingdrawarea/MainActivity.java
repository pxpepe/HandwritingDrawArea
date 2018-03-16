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
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DrawingView dv;
    private Paint mPaint;

    JSONArray listaInk;
    JSONArray lx;
    JSONArray ly;
    JSONArray lt;
    long tempoInicio = 0;

    private String caixaTextoAceite = "";

    private List<SimboloValorDto> listaSimbolos;

    private void inicializarListaSimbolos() {

        listaSimbolos = new ArrayList<>();

        // Adicionamos o valor 0
        //---------------------------
        SimboloValorDto valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("0");
        List<String> arraySimbolo = new ArrayList<>();
        arraySimbolo.add("zero");
        arraySimbolo.add("cero");
        arraySimbolo.add("zer");
//        arraySimbolo.add("z");
        arraySimbolo.add("0");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o valor 1
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("1");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("um");
        arraySimbolo.add("uno");
        arraySimbolo.add("un");
        arraySimbolo.add("m");
        arraySimbolo.add("u");
        arraySimbolo.add("1");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o valor 2
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("2");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("dois");
        arraySimbolo.add("doi");
//        arraySimbolo.add("d");
        arraySimbolo.add("dos");
        arraySimbolo.add("2");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o valor 3
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("3");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("três");
        arraySimbolo.add("tres");
        arraySimbolo.add("trez");
        arraySimbolo.add("tre");
        arraySimbolo.add("3");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o valor 4
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("4");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("quatro");
        arraySimbolo.add("quadro");
        arraySimbolo.add("quatr");
//        arraySimbolo.add("q");
        arraySimbolo.add("4");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o valor 5
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("5");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("cinco");
        arraySimbolo.add("cino");
        arraySimbolo.add("cinc");
        arraySimbolo.add("cimco");
        arraySimbolo.add("5");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o valor 6
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("6");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("seis");
        arraySimbolo.add("ses");
        arraySimbolo.add("sei");
//        arraySimbolo.add("s");
        arraySimbolo.add("6");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o valor 7
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("7");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("sete");
        arraySimbolo.add("set");
        arraySimbolo.add("se");
        arraySimbolo.add("ste");
        arraySimbolo.add("7");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o valor 8
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("8");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("oito");
        arraySimbolo.add("oto");
        arraySimbolo.add("oit");
//        arraySimbolo.add("o");
        arraySimbolo.add("8");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o valor 9
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("9");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("nove");
        arraySimbolo.add("nov");
        arraySimbolo.add("move");
//        arraySimbolo.add("u");
        arraySimbolo.add("9");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o simbolo +
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("+");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("mais");
        arraySimbolo.add("nais");
        arraySimbolo.add("mai");
        arraySimbolo.add("nas");
        arraySimbolo.add("+");
        arraySimbolo.add("t");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o simbolo -
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("-");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("menos");
        arraySimbolo.add("memos");
        arraySimbolo.add("nemos");
        arraySimbolo.add("nenos");
        arraySimbolo.add("-");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o simbolo *
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("*");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("vezes");
        arraySimbolo.add("veses");
        arraySimbolo.add("x");
        arraySimbolo.add("*");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o simbolo /
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("/");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("dividir");
        arraySimbolo.add("divisão");
        arraySimbolo.add("/");
        arraySimbolo.add("|");
        arraySimbolo.add("div");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

        // Adicionamos o simbolo =
        //---------------------------
        valSimbolo = new SimboloValorDto();
        valSimbolo.setSimbolo("=");
        arraySimbolo = new ArrayList<>();
        arraySimbolo.add("igual");
        arraySimbolo.add("=");
        valSimbolo.setValoresPossiveis(arraySimbolo);
        listaSimbolos.add(valSimbolo);
        //---------------------------

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inicializarListaSimbolos();
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


    private String calcularExprMatematica(String textoEntrada) {

        String saida = "";


        // A caixa tem texto e tem o igual para obter o resultado
        if (textoEntrada!=null
                && textoEntrada.trim()!=""
                && textoEntrada.indexOf("=")==textoEntrada.length()-1) {

            try {

                double resultado = evalNumericExp(textoEntrada.replace("=",""));
                saida = String.valueOf(resultado);

            } catch(Exception e) {
            }

        }

        return saida;

    }

    private void processarInfoCaixa() {

        TextView visor = findViewById(R.id.textView);

        String[] arrComps = visor.getText().toString().split(" ");
        String textoConvertido = "";

        if (arrComps!=null && arrComps.length>0) {
            for (String componente : arrComps) {

                String simboloAct = null;
                for (SimboloValorDto simbolo : listaSimbolos) {

                    for (String simEquivalente : simbolo.getValoresPossiveis()){

                        // Neste caso temos equivalencia, guardamos o valor
                        if (componente.toLowerCase().equals(simEquivalente)) {
                            simboloAct=simbolo.getSimbolo();
                            break;
                        }

                    }

                    if (simboloAct!=null) {
                        textoConvertido+=simboloAct;
                        break;
                    }

                }

            }

            TextView vistaConvertido = findViewById(R.id.rxtTransf);
            vistaConvertido.setText(textoConvertido+calcularExprMatematica(textoConvertido));

        }


    }

    public void clicarBtnLimpar(View vistaBtn) {
        // A primeira vez limpa apenas o conteúdo adicionado em último
        // A segunda vez que clicamos limpa tudo
        // Se a caixa já eta limpa, limpa também a caixa de texto
        dv.resetCanvasArea( dv.getWidth(),dv.getHeight());

        TextView visor = findViewById(R.id.textView);
        String textoAtual = visor.getText().toString();

        // Já tinhamos limpo, limpamos tudo
        if (textoAtual.trim().equals(caixaTextoAceite)) {
            caixaTextoAceite="";
        }

        visor.setText(caixaTextoAceite);
        processarInfoCaixa();

    }

    public void clicarBtnOk(View vistaBtn) {
        // Aceita a operação atual (passa para a caixa) de texto e limpa a área de desenho

        TextView visor = findViewById(R.id.textView);

        caixaTextoAceite = visor.getText().toString();

        dv.resetCanvasArea( dv.getWidth(),dv.getHeight());

        processarInfoCaixa();
    }

    private JSONObject getRequestJson() {

        JSONObject jsonObject = new JSONObject();

        try {

            JSONObject wrintingGuide = new JSONObject();
            wrintingGuide.put("writing_area_width", dv.getWidth());
            wrintingGuide.put("writing_area_height", dv.getHeight());

            JSONObject requestObj = new JSONObject();
            requestObj.put("writing_guide", wrintingGuide);
            //requestObj.put("pre_context", preContext);
            requestObj.put("pre_context", caixaTextoAceite);
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

    private String selecionarMelhorOpc(JSONArray arrayActual) {

        String saida = "";

        try {
            // Selecionamos sempre a primeira opção,
            // No futuro podemos tentar selecionar a opção que contenha mais coincidencias
            saida = (String) arrayActual.get(0);
        } catch (JSONException e) {
            //
        }

        return saida;

    }

    private void tratarOperCaixaTexto(String textoPendente) {

        TextView visor = findViewById(R.id.textView);

        String textoTotal = caixaTextoAceite+textoPendente;

        visor.setText(textoTotal);

        processarInfoCaixa();

    }

    private void requestInputHandwriting() {

        String params = "itc=pt-pt-t-i0-handwrit&app=demopage";

        String url = "https://inputtools.google.com/request?"+params;

        JsonArrayRequest jsonArrayRequest;

        JsonObArrRequest jsonObjReq = new JsonObArrRequest(
                Request.Method.POST, url, getRequestJson(),
                new Response.Listener<JSONArray>() {
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length()>=2) {

                                String estadoResp = (String) response.get(0);

                                if ("SUCCESS".equals(estadoResp)) {

                                    JSONArray arrayRespReq = (JSONArray) response.get(1);

                                    if (arrayRespReq.length()>=1) {

                                        JSONArray arrayResp = (JSONArray) arrayRespReq.get(0);

                                        if (arrayResp.length()>=2) {
                                                String codigo = (String) arrayResp.get(0);
                                                JSONArray arrPalavras = (JSONArray) arrayResp.get(1);
                                                tratarOperCaixaTexto(selecionarMelhorOpc(arrPalavras));
                                        }
                                    }


                                }

                            }

                        } catch (Exception e) {
                            Log.d("Error Message","Error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error Message","Error: " + error.getMessage());
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

        public void resetCanvasArea(int w, int h) {
            listaInk = new JSONArray();
            lx = new JSONArray();
            ly = new JSONArray();
            lt = new JSONArray();

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            invalidate();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            resetCanvasArea(w, h);
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

    public static double evalNumericExp(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

}
