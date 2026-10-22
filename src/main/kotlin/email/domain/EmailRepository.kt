package email.domain

import java.util.*

interface EmailRepository{
    fun save(email: Email)
    fun findByOwner(ownerId: UUID): List<Email>
    fun findById(id: UUID): Email?
    fun delete(id: UUID)
}