package email.domain

import java.util.*

data class Email(
    val id: UUID = UUID.randomUUID(),
    val owner: UUID,
    val from: String,
    val to: String,
    val subject: String,
    val body: String,
    var folder: Folder = Folder.INBOX,
    var isRead: Boolean = false
)