package cz.hermik.psivylety;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Color;

public class MainActivity extends Activity {
    private static final String HOME = "https://tzouz.github.io/Psi-venceni/";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(36, 92, 69));
        getWindow().setNavigationBarColor(Color.rgb(36, 92, 69));
        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(245, 247, 244));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return handleUrl(request.getUrl().toString());
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return handleUrl(url);
            }
            private boolean handleUrl(String url) {
                if (url.startsWith("https://tzouz.github.io/Psi-venceni/") ||
                    url.startsWith("https://dmmdcdrhcjmstimvjlsf.supabase.co/") ||
                    url.startsWith("https://unpkg.com/") ||
                    url.startsWith("https://cdn.jsdelivr.net/") ||
                    url.startsWith("https://{s}.tile.openstreetmap.org/")) {
                    return false;
                }
                if (url.startsWith("https://") || url.startsWith("http://") ||
                    url.startsWith("geo:") || url.startsWith("google.navigation:")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception ignored) { }
                    return true;
                }
                return false;
            }
        });
        setContentView(webView);
        if (savedInstanceState != null) webView.restoreState(savedInstanceState);
        else webView.loadUrl(HOME);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (webView != null) webView.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
