package com.kotlin.getyoteamassignment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.getyoteamassignment.Model.Chapter
import com.kotlin.getyoteamassignment.Model.ChapterElement
import com.kotlin.pixelsoftwaresdemo.Adapter.RecyclerAdapterProducts
import com.kotlin.pixelsoftwaresdemo.Retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.media.AudioManager

import android.media.MediaPlayer
import android.widget.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        //Dialog Create
        val dialog = Dialog(this)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        //MediaPlayer
        val mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer()

        val call: Call<Chapter> = ApiClient.getClient.getCourses("6434", "157073")
        call.enqueue(object : Callback<Chapter> {
            override fun onResponse(
                call: Call<Chapter>?,
                response: Response<Chapter>?
            ) {
                // val data: ArrayList<Chapter>
                val myList: List<ChapterElement>
                myList = response?.body()?.chapter!!
                val adapter = RecyclerAdapterProducts(myList) { myList ->

                    var url: String? = myList.fileURL.toString()
                    Toast.makeText(this@MainActivity, myList.toString(), Toast.LENGTH_SHORT).show()

                    if (myList.fileType.equals("audio")) {

                        dialog.setContentView(R.layout.audiodialog)
                        dialog.show()

                        val btnPlay = dialog.findViewById<Button>(R.id.btnPlay)

                        btnPlay.setOnClickListener {
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                            try {
                                mediaPlayer.setDataSource(url)
                                mediaPlayer.prepare()
                                mediaPlayer.start()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }

                        val btnPause = dialog.findViewById<Button>(R.id.btnPause)
                        btnPause.setOnClickListener {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer.release();
                                Toast.makeText(
                                    this@MainActivity,
                                    "Audio has been paused",
                                    Toast.LENGTH_SHORT
                                ).show();
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Audio has not played",
                                    Toast.LENGTH_SHORT
                                ).show();
                            }
                        }

                        val btnClose = dialog.findViewById<ImageView>(R.id.btnCloseSGV)
                        btnClose.setOnClickListener {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer.release();
                            }
                            dialog.dismiss()
                        }

                    } else if (myList.fileType.equals("video")) {
                        dialog.setContentView(R.layout.videodialog)
                        dialog.show()

                        val vdvW = dialog.findViewById<VideoView>(R.id.vdVw)
                        vdvW.setVideoPath(url)
                        vdvW.start()

                        val btnClose = dialog.findViewById<ImageView>(R.id.btnCloseSGV)
                        btnClose.setOnClickListener {
                            vdvW.stopPlayback()
                            dialog.dismiss()
                        }

                    } else if (myList.fileType.equals("text")) {
                        dialog.setContentView(R.layout.textdialog)
                        dialog.show()

                        val txtView = dialog.findViewById<TextView>(R.id.txtView)
                        txtView.text = myList.textChapter

                        val btnClose = dialog.findViewById<ImageView>(R.id.btnCloseSGV)
                        btnClose.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                };
                recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<Chapter>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "" + t, Toast.LENGTH_SHORT).show()
            }
        })

    }
}