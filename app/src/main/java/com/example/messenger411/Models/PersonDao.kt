package com.example.messenger411.Models



import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface  PersonDao {
    @Query("SELECT * FROM person")
    fun getAll(): List<Person>
    @Query("SELECT * FROM person WHERE uid IN (:personIds)")
    fun getAllByIds(personIds: IntArray): List<Person>
    @Query("SELECT * FROM person WHERE number = :first")
    fun findByNumber(first: Int): Flow<List<Person>>
    @Query("SELECT COUNT(*) FROM person WHERE number = :first")
    fun checkValue(first: Int): Int

    @Insert
    fun insert(vararg person: Person)
    @Insert
    fun insertAll(person: List<Person>)

    @Update
    fun update(vararg person: Person)

    @Delete
    fun delete(person: Person)
}