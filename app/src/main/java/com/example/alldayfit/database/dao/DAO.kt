package com.example.alldayfit.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alldayfit.database.entity.DietLogEntity
import com.example.alldayfit.database.entity.ExerciseLogEntity
import com.example.alldayfit.database.entity.PhysicalInformationEntity
import com.example.alldayfit.database.entity.UserEntity


// UserDAO
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE name = :name")
    suspend fun getUserByName(name: String): UserEntity?
}

@Dao
interface PhysicalInformationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhysicalInformation(physicalInformation: PhysicalInformationEntity)

    @Query("SELECT * FROM physical_information WHERE name = :name")
    suspend fun getPhysicalInformationByUserId(name: String): PhysicalInformationEntity?
}


@Dao
interface ExerciseLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseLog(exerciseLog: ExerciseLogEntity)

    @Query("SELECT * FROM exercise_logs WHERE name = :name")
    suspend fun getExerciseLogByUserId(name: String): ExerciseLogEntity?
}


@Dao
interface DietLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDietLog(dietLog: DietLogEntity)

    @Query("SELECT * FROM diet_logs WHERE name = :name")
    suspend fun getDietLogByUserId(name: String): DietLogEntity?
}



