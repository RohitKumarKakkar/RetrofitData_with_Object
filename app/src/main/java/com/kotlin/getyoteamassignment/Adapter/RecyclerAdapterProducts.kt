package com.kotlin.pixelsoftwaresdemo.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.getyoteamassignment.Model.ChapterElement
import com.kotlin.getyoteamassignment.R
import android.media.AudioManager

import android.media.MediaPlayer
import java.io.IOException


class RecyclerAdapterProducts(
    private val courseList: List<ChapterElement>,
    private val listener: (ChapterElement) -> Unit
) : RecyclerView.Adapter<RecyclerAdapterProducts.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.singleton_courses, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val courseListing = courseList.get(position)

        holder.title.text = courseListing.title
        holder.subtitle.text = courseListing.subTitle
        holder.courseid.text =
            "Course ID : ${courseListing.courseID.toString()}"
        holder.chapterid.text =
            "Chapter ID : ${courseListing.chapterID.toString()}"

        if (courseListing.completed.toString().equals("false")) {
            holder.status.text = "Not Completed"
            holder.status.setTextColor(Color.parseColor("#FFDB6C"))
        } else {
            holder.status.text = "Completed"
            holder.status.setTextColor(Color.parseColor("#3DBC3C"))
        }

        holder.duration.text =
            "Duration - " + courseListing.minute + ":" +
                    courseListing.second

        val type: String = courseListing.fileType.toString()
        holder.coursetype.text = "Course Type : $type"

        holder.itemView.setOnClickListener { listener(courseListing) }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return courseList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val subtitle: TextView = itemView.findViewById(R.id.tvsubtitle)
        val title: TextView = itemView.findViewById(R.id.tvtitle)
        val courseid: TextView = itemView.findViewById(R.id.tvCourseId)
        val chapterid: TextView = itemView.findViewById(R.id.tvchapterid)
        val coursetype: TextView = itemView.findViewById(R.id.tvCourseType)
        val status: TextView = itemView.findViewById(R.id.tvStatus)
        val duration: TextView = itemView.findViewById(R.id.tvDuration)

    }
}