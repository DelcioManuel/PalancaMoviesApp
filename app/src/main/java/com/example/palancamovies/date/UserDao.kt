package com.example.palancamovies.date

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    suspend fun getUser(email: String, password: String): User?

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getLoggedInUser(): User?

    @Query("DELETE FROM user_table")
    suspend fun logout()  // Remove todos os dados da tabela (encerra a sessão)
}
