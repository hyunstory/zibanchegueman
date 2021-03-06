package com.davidlee.zibanchegueman_android



import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.davidlee.zibanchegueman_android.func.ChangeStatusBarColorFunc
import kotlinx.android.synthetic.main.activity_block_info.*
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import java.net.URL


class BlockInfoActivity : AppCompatActivity() {

    val url = "http://35.235.52.246:8080/web/android/index.html"
    val url2 = "http://35.235.52.246:8080/api/web/ious"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ChangeStatusBarColorFunc.updateStatusBarColor(this, R.color.colorWhite)
        setContentView(R.layout.activity_block_info)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

        //jsoutparsing()

        refresh.setOnClickListener {

            real_block_view.loadUrl(url2)
        }

        retrieveWebView()

        getBlockInfo()
    }

    fun getBlockInfo(){

        val apiResponse = URL("http://35.235.52.246:8080/api/web/ious").readText()

        println("**************************************")
        //println(apiResponse)
        println("**************************************")
        val jArray = JSONArray(apiResponse)



        for (i in 0 until jArray.length()){
            val obj = jArray.getJSONObject(i)
            val stateArray = obj.getJSONObject("state")
            val dataArray = stateArray.getJSONObject("data")


            val from = dataArray.getString("from")
            val to = dataArray.getString("to")
            val amount = dataArray.getString("amount")
            val date = dataArray.getString("date")
            val type = dataArray.getString("type")

            Log.d("&&&", "^^^^^^^^^^^^^^^^^^^^^^^")
            Log.d("&&&", "from($i): $from")
            Log.d("&&&", "to($i): $to")
            Log.d("&&&", "amount($i): $amount")
            Log.d("&&&", "date($i): $date")
            Log.d("&&&", "type($i): $type")
            Log.d("&&&", "^^^^^^^^^^^^^^^^^^^^^^^")





        }
        Log.d("&&&", jArray.length().toString())

        println("**************************************")

    }



    fun jsoutparsing() {

        println("----------------------------")
        //1. Fetching the HTML from a given URL
        Jsoup.connect(url).get().run {

            //2. Parses and scrapes the HTML response
            select("div.panel-body ul").forEachIndexed { index, element ->
                var titleAnchor = element.select("li").html()



                var title = titleAnchor.toString()
                //val url = titleAnchor.attr("href")
                //3. Dumping Search Index, Title and URL on the stdout.
                Toast.makeText(this@BlockInfoActivity, "${index} ${title}", Toast.LENGTH_SHORT).show()
                //println("$index. $title")
                println(element.text())
            }
        }

        /*
        val url = URL("http://35.235.52.246:8080/web/android/index.html")
        val html = url.readText()

        val element = HTMLParser.getElementsByTag(html, "from")
        element.eachText().forEach { println(it) }

        val to = HTMLParser.getElementsById(html, "to")
        to.allElements.forEach {
            println(it.html())
        }

*/
/*
        Jsoup.connect(url).get().run {
            select("li").forEachIndexed{ index, element ->
                val element1 = element.select("from")
                val from = element1.text()


                Toast.makeText(this@BlockInfoActivity, from.toString(), Toast.LENGTH_SHORT).show()



            }
            */


    }



    fun retrieveWebView(){

        val settings = real_block_view.settings

        settings.javaScriptEnabled = true
        //block_view.addJavascriptInterface(WebBrideg(), "java")



        //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
          //  settings.safeBrowsingEnabled = true
        //}


        //settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true

        settings.setUseWideViewPort(true)
        // 줌 컨트롤 사용 여부
        settings.setDisplayZoomControls(false)

        // TextEncoding 이름 정의
        settings.setDefaultTextEncodingName("UTF-8")

        // Setting Local Storage
        settings.setDatabaseEnabled(true)
        settings.setDomStorageEnabled(true)

        // 캐쉬 사용 방법을 정의
        //settings.setCacheMode(WebSettings.LOAD_NO_CACHE)

        settings.domStorageEnabled = true


        real_block_view.loadUrl(url2)



    }




////
}
