package com.example.studysmart.data.repository

import com.example.studysmart.data.local.SessionDao
import com.example.studysmart.domain.model.Session
import com.example.studysmart.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDao: SessionDao
):SessionRepository {
    override suspend fun insertSession(session: Session) {
        sessionDao.insertSession(session)
    }

    override suspend fun deleteSession(session: Session) {
        sessionDao.deleteSession(session)
    }

    override fun getAllSession(): Flow<List<Session>> {
        return sessionDao.getAllSessions()
    }

    override fun getRecentFiveSessions(): Flow<List<Session>> {
        return sessionDao.getAllSessions().take(count = 5)
    }

    override fun getRecentTenSessionForSubject(subjectId: Int): Flow<List<Session>> {
        return sessionDao.getRecentSessionForSubject(subjectId).take(count = 10)
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDao.getTotalSessionDuration()
    }

    override fun getTotalSessionDurationBySubjectId(subjectId: Int): Flow<Long> {
        return sessionDao.getTotalSessionDurationBySubjectId(subjectId)
    }
}