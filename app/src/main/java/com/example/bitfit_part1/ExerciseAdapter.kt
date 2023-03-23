package com.example.bitfit_part1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class ExerciseAdapter(private val exercises: List<DisplayExercise>) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val exerciseName: TextView = itemView.findViewById(R.id.exerciseTv)
        val exerciseReps: TextView = itemView.findViewById(R.id.repsTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.exercise_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.exerciseName.text=exercise.exercise.toString()
        holder.exerciseReps.text=exercise.reps.toString()
    }
}