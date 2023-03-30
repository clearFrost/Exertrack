package com.example.bitfit_part1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlinx.coroutines.launch


class ExerciseFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_exercise, container, false)
        val exerciseRv: RecyclerView = view.findViewById<RecyclerView>(R.id.exerciseTrackerRv)
        val exerciseList: MutableList<DisplayExercise> = listOf<DisplayExercise>().toMutableList()
        val exerciseAdapter = ExerciseAdapter(exerciseList)
        exerciseRv.adapter = exerciseAdapter
        exerciseRv.layoutManager = LinearLayoutManager(context)


        lifecycleScope.launch {
            (activity?.application as ArticleApplication).db.exerciseDao().getAll().collect { databaseList ->
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
        return view
    }

    companion object {
        fun newInstance(): ExerciseFragment{
            return ExerciseFragment()
        }
    }
}