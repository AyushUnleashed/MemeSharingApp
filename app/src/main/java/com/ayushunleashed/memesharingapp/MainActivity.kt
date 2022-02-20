package com.ayushunleashed.memesharingapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var imageURL:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme()
    {
        progressBar.visibility = View.VISIBLE
        //creating request queue
        //val queue = Volley.newRequestQueue(this)
        //getting jsonObject from given url

        val apiUrl = "https://meme-api.herokuapp.com/gimme/marvelmemes"


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, apiUrl, null,
            { response ->
               imageURL = response.getString("url")
                Log.d("HogyaLoadImage","chodho yaar")
                Glide.with(this).load(imageURL).listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                    
                }).into(memeImageView)
            },
            { error ->
                Log.d("NhiHuaImageLoad",error.localizedMessage)
            }
        ) // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMemeFun(view: View) {

        //explicit intent  to travel between processes
        //creating intent to send action
        val intent = Intent(Intent.ACTION_SEND)
        //type of file to send
        intent.type = "text/plain"
      // putting text to be shared
        intent.putExtra(Intent.EXTRA_TEXT,"Hey guys checkout this meme ${imageURL}")

        //chooser for sharing with what to say
        val chooser = Intent.createChooser(intent,"Share via:")
        //starting chooser
        startActivity(chooser)
    }   

    fun nextMemeFun(view: View) {
    loadMeme();
    }
}

