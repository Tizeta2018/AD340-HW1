package com.example.myapplicationhw3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.zombie_movies
import com.squareup.picasso.Picasso


class ZombieMovies : AppCompatActivity()  {
        private lateinit var adapter: Adapter
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_zombie_movies)

            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            adapter = Adapter()
            val layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.layoutManager = layoutManager
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = adapter

            val actionbar = supportActionBar
            actionbar!!.title = "Zombie Movies"
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressed()
            return true
        }

    internal class Adapter :
        RecyclerView.Adapter<Adapter.MyViewHolder>() {
        internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var title: TextView = view.findViewById(R.id.title)
            var year: TextView = view.findViewById(R.id.year)
            var director: TextView = view.findViewById(R.id.director)
            val movielogo: ImageView = view.findViewById(R.id.movielogo)


        }
        @NonNull
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list,
                    parent,
                    false)



            return MyViewHolder(itemView)
        }
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val zmovie = zombie_movies[position]
            holder.title.text = zmovie[0]
            holder.year.text = zmovie[1]
            holder.director.text = zmovie[2]
            Picasso.get().load(zmovie[3]).into(holder.movielogo)

        }

        override fun getItemCount(): Int {
            return zombie_movies.size
        }

    }


    }