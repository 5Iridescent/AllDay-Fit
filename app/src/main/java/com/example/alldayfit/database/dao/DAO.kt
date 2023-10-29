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
    // 사용자 정보를 Database 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    // 특정 userId에 해당하는 사용자 정보를 조회
    @Query("SELECT * FROM users WHERE name = :name")
    suspend fun getUserByName(name: String): UserEntity?
}


// PhysicalInformationDao
@Dao
interface PhysicalInformationDao {
    // 신체 정보를 Database 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhysicalInformation(physicalInformation: PhysicalInformationEntity)

    // 특정 userId에 해당하는 신체 정보를 조회
    @Query("SELECT * FROM physical_information WHERE name = :name")
    suspend fun getPhysicalInformationByUserId(name: String): PhysicalInformationEntity?
}


// ExerciseLogDao
@Dao
interface ExerciseLogDao {
    // 운동 로그 Database 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseLog(exerciseLog: ExerciseLogEntity)

    // 특정 userId에 해당하는 운동 로그를 조회
    @Query("SELECT * FROM exercise_logs WHERE name = :name")
    suspend fun getExerciseLogByUserId(name: String): ExerciseLogEntity?
}


// DietLogDao
@Dao
interface DietLogDao {
    // 식사 로그를 Database 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDietLog(dietLog: DietLogEntity)

    // 특정 userId에 해당하는 식사 로그를 조회
    @Query("SELECT * FROM diet_logs WHERE name = :name")
    suspend fun getDietLogByUserId(name: String): DietLogEntity?
}



