package com.example.bitfit_part1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_table")
data class ExerciseEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name="exercise") val exercise: String?,
    @ColumnInfo(name="reps") val reps: String?
        )