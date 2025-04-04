package pro.tricko.android

import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var webview: WebView
    var timestamp: Long = 0
    lateinit var backNotification: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview = findViewById(R.id.webView)
        webview.settings.javaScriptEnabled = true
        webview.settings.domStorageEnabled = true

        backNotification = Toast.makeText(this, "Press again to exit.", Toast.LENGTH_SHORT)

        if (savedInstanceState != null) webview.restoreState(savedInstanceState)
        else webview.loadUrl("https://tricko.pro")

        onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (webview.canGoBack()) {
                            webview.goBack()
                        } else {
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - timestamp < 2000) {
                                backNotification.cancel()
                                finishAffinity()
                            } else {
                                timestamp = currentTime
                                backNotification.show()
                            }
                        }
                    }
                }
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webview.saveState(outState)
    }
}
