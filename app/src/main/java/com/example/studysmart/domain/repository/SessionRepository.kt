package com.example.studysmart.domain.repository

import com.example.studysmart.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun insertSession(session: Session)
    suspend fun deleteSession(session: Session)
    fun getAllSession():Flow<List<Session>>
    fun getRecentFiveSessions():Flow<List<Session>>
    fun getRecentTenSessionForSubject(subjectId:Int):Flow<List<Session>>
    fun getTotalSessionDuration():Flow<Long>
    fun getTotalSessionDurationBySubjectId(subjectId: Int):Flow<Long>
}