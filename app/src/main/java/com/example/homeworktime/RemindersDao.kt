package com.example.homeworktime

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * This interface contains all of methods of the Reminder Table.
 */
@Dao
interface RemindersDao {

    /**
     * Gets all of [Reminder] from database.
     *
     * @return A list of [Reminder]
     */
    @Query("SELECT * FROM Reminders WHERE status = 0")
    fun getAll(): LiveData<List<Reminder>>

    /**
     * Gets a [Reminder] by ID from database.
     *
     * @param [id] ID of the journal to get.
     * @return A [Reminder] found.
     */
    @Query("SELECT * FROM Reminders WHERE idReminder = :id")
    fun get(id:Int): LiveData<Reminder>

    /**
     * Gets all of [Reminder] from database with complete status.
     *
     * @return A list of [Reminder]
     */
    @Query("SELECT * FROM Reminders WHERE status = 1")
    fun getComplete(): LiveData<List<Reminder>>

    /**
     * Inserts a [Reminder] in database.
     *
     * @param [reminder] Reminder to insert in database.
     */
    @Insert
    fun insertAll(vararg reminder: Reminder): List<Long>

    /**
     * Updates a [Reminder] in database.
     *
     * @param [reminder] Reminder to update.
     */
    @Update
    fun update(reminder: Reminder)

    /**
     * Deletes a [Reminder] in database.
     *
     * @param [reminder] Reminder to delete.
     */
    @Delete
    fun delete(reminder: Reminder)

    /**
     * Looks for a [Reminder] in database by title or course.
     *
     * @param [searchQuery] Title or course name to search.
     */
    @Query("SELECT * FROM Reminders WHERE title LIKE :searchQuery OR course LIKE :searchQuery")
    fun searchDatabase(searchQuery:String) : LiveData<List<Reminder>>
}