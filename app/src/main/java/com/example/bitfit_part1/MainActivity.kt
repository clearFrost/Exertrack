package com.example.bitfit_part1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exerciseInput: TextView = findViewById(R.id.exerciseET)
        val repetitionsInput: TextView = findViewById(R.id.repsET)
        val addWorkoutBtn: Button = findViewById<Button>(R.id.addWorkoutBtn)
/*        val exerciseTrackerRv: RecyclerView = findViewById(R.id.exerciseTrackerRv)*/
        val exerciseList: MutableList<DisplayExercise> = listOf<DisplayExercise>().toMutableList()

        val exerciseAdapter = ExerciseAdapter(exerciseList)
/*        exerciseTrackerRv.adapter = exerciseAdapter
        exerciseTrackerRv.layoutManager=LinearLayoutManager(this)*/
        val exerciseFragment: Fragment = ExerciseFragment()
        val statsFragment: Fragment = StatsFragment()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

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
        bottomNavigationView.setOnItemSelectedListener {
            item ->
            lateinit var fragment: Fragment
            when (item.itemId){
                R.id.exerciseItem -> fragment = exerciseFragment
                R.id.statsItem -> fragment = statsFragment
            }
            replaceFragment(fragment)
            true
        }
        bottomNavigationView.selectedItemId = R.id.exerciseItem
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.article_frame_layout, fragment)
        fragmentTransaction.commit()
    }
}