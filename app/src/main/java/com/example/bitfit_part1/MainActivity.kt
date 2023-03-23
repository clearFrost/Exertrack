package com.example.bitfit_part1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exerciseInput: TextView = findViewById(R.id.exerciseET)
        val repetitionsInput: TextView = findViewById(R.id.repsET)
        val addWorkoutBtn: Button = findViewById<Button>(R.id.addWorkoutBtn)
        val exerciseTrackerRv: RecyclerView = findViewById(R.id.exerciseTrackerRv)
        val exerciseList: MutableList<DisplayExercise> = listOf<DisplayExercise>().toMutableList()

        val exerciseAdapter = ExerciseAdapter(exerciseList)
        exerciseTrackerRv.adapter = exerciseAdapter
        exerciseTrackerRv.layoutManager=LinearLayoutManager(this)

        lifecycleScope.launch {
            (application as ArticleApplication).db.exerciseDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayExercise(
                        entity.id,
                        entity.exercise,
                        entity.reps)
                }.also { mappedList ->
                    exerciseList.clear()
                    exerciseList.addAll(mappedList)
                    exerciseAdapter.notifyDataSetChanged()
                }
            }
        }


        addWorkoutBtn.setOnClickListener {
            lifecycleScope.launch(IO){
            (application as ArticleApplication).db.exerciseDao().insert(
                ExerciseEntity(
                    exercise = exerciseInput.text.toString(),
                    reps = repetitionsInput.text.toString()
                )
            )}
        }
    }
}