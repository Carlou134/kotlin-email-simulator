package email.infrastructure

import email.domain.Email
import email.domain.EmailRepository
import java.util.*

class InMemoryEmailRepository: EmailRepository{
    private val emails = mutableListOf<Email>()

    override fun save(email: Email) {
        emails.add(email)
    }

    override fun findByOwner(ownerId: UUID): List<Email> {
        return emails.filter{ email -> email.owner == ownerId }
    }

    override fun findById(id: UUID): Email? {
        return emails.firstOrNull{ it.id == id }
    }

    override fun delete(id: UUID) {
        emails.removeIf { email -> email.id == id }
    }
}