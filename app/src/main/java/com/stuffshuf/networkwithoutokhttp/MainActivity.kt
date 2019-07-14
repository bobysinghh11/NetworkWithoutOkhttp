package com.stuffshuf.networkwithoutokhttp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnData.setOnClickListener {

            val networkConnection=NetworkConnection()

            //execute has paramete like vararg param:String? and in doInBackground fun parameter take this
            //this parameter Url[0] like this -->URL("https://api.github.com/search/users?q=Pulkit%20Aggarwal")
            //in execute we can also pass multiple url like execute("url1, url2)
            networkConnection.execute("https://api.github.com/search/users?q=Pulkit%20Aggarwal")


        }



    }


//we add inner keyword because our class has out of scop and we need scop of main class that is why we add inner
    // now inner extend the scop of Networkconnection class
   inner class NetworkConnection():AsyncTask<String, Int, String>() {

        override fun doInBackground(vararg Url: String?): String {

            val googleUrl=URL(Url[0])// Ulr[0] --> execute(vararg param:String?) -->execute(Url[0])
            val connection=googleUrl.openConnection() as HttpURLConnection
            val isr=InputStreamReader(connection.inputStream)
            val bufferReader=BufferedReader(isr)
            val sb=StringBuilder()
            var buffer:String?="" //here buffer is empty not null
            while (buffer!=null)  // we got actual data through bufferReader and at the end of data have null value so it will stop working
            {
                //StringBuiler addd buffer in sb and after adding all the actual data in sb it return to the onPostExecute
                sb.append(buffer)
                buffer=bufferReader.readLine()
            }
              return sb.toString()

        }



        override fun onPostExecute(result: String?) {


            // here we got json format data which is not readable and we need to convert json into readable format
            // for this we use json parsing where we hava to declare many of things
           val userList= arrayListOf<Users>()
            val jsonData=JSONObject(result)
            val itemList=jsonData.getJSONArray("items")
            for(i in 0..8){
                val users=Users((itemList[i] as JSONObject).getString("login"),
                    (itemList[i] as JSONObject).getInt("id")



                            )
                userList.add(users)
               // getdata.text=userList.toString()


            }

            showdata.layoutManager=GridLayoutManager(
                this@MainActivity,
                2,
                GridLayoutManager.HORIZONTAL,
                false
            )
            showdata.adapter=UsersAdapter(userList)

            //to access api we need to add permission of the INTERNET in manifests file

        }
    }
}

