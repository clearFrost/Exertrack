package com.example.bitfit_part1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class StatsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stats, container, false)
        val exerciseList: MutableList<DisplayExercise> = listOf<DisplayExercise>().toMutableList()
        val average: TextView = view.findViewById(R.id.averageWorkoutTv)
        val highest: TextView = view.findViewById(R.id.highestWorkoutTv)
        /*val lowest: TextView = view.findViewById(R.id.lowestWorkoutTv)*/
        val total: TextView = view.findViewById(R.id.totalWorkoutTv)

        var highestNum = 0
        var averageNum = 0
        var totalNum = 0
        /*var lowestNum = 0*/



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
                }
                for (counter in exerciseList.map{ it.reps!!.toInt()}){
                    if(highestNum < counter){
                        highestNum = counter
                    }
                }
                highest.text = highestNum.toString()
                /*for (counter in exerciseList.map{it.reps!!.toInt()}){
                    if(lowestNum > counter){
                        lowestNum=counter
                    }
                }
                lowest.text = lowestNum.toString()*/
                for (counter in exerciseList.map { it.reps!!.toInt() }){
                    if(counter != null){
                        totalNum += counter
                    }
                }
                total.text = totalNum.toString()
                averageNum = (totalNum/exerciseList.size)
                average.text = averageNum.toString()
        }

    }
        return view

    }

    companion object {
    fun newInstance(): StatsFragment{
        return StatsFragment()
    }
    }
}