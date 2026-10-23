package email.service

import email.domain.Email
import email.domain.Folder
import email.infrastructure.InMemoryEmailRepository
import user.domain.User
import user.infrastructure.InMemoryUserRepository
import user.service.AuthenticationException
import user.service.UserAlreadyExistsException
import user.service.UserNotFoundException
import java.util.UUID

class InboxService (
    private val userRepository: InMemoryUserRepository,
    private val emailRepository: InMemoryEmailRepository
){
    fun register(username: String, password: String){
        if(userRepository.findByUsername(username) != null){
            throw UserAlreadyExistsException(username)
        }
        userRepository.save(User(
            username = username,
            password = password,
        ))
    }

    fun login(username: String, password: String):User{
        val user = userRepository.findByUsername(username) ?: throw UserNotFoundException(username)
        if(!user.authenticate(password)){
            throw AuthenticationException(username)
        }
        return user
    }

    fun sendEmail(user: User, toUsername:String, subject: String, body: String){
        val sentMail = Email(
            owner = user.id,
            from = user.username,
            to = toUsername,
            subject = subject,
            body = body,
            folder = Folder.SENT
        )

        emailRepository.save(sentMail)

        userRepository.findByUsername(toUsername)?.let { recipient ->
            val inboxMail = sentMail.copy(
                id = UUID.randomUUID(),
                owner = recipient.id,
                folder = Folder.INBOX
            )

            emailRepository.save(inboxMail)
        }
    }

    fun receiveEmail(user: User, from: String, subject: String, body: String){
        val mail = Email(
            owner = user.id,
            from = from,
            to = user.username,
            subject = subject,
            body = body
        )
        emailRepository.save(mail)
    }

    fun listByFolder(user: User, folder: Folder): List<Email>{
        return emailRepository.findByOwner(user.id).filter {
            it.folder == folder
        }
    }

    fun markAsRead(user: User, emailId: UUID){
        val mail = emailRepository.findById(emailId)?:throw EmailNotFoundException(emailId.toString())

        if(mail.owner != user.id){
            throw EmailNotFoundException(emailId.toString())
        }

        mail.isRead = true
        emailRepository.save(mail)
    }

    fun moveToFolder(user: User, emailId: UUID, folder: Folder){
        val mail = emailRepository.findById(emailId)?:throw EmailNotFoundException(emailId.toString())

        if(mail.owner != user.id){
            throw EmailNotFoundException(emailId.toString())
        }

        mail.folder = folder
        emailRepository.save(mail)
    }

    fun deleteMail(user: User, emailId: UUID){
        val mail = emailRepository.findById(emailId)?:throw EmailNotFoundException(emailId.toString())

        if(mail.owner != user.id){
            throw EmailNotFoundException(emailId.toString())
        }

        emailRepository.delete(emailId)
    }
}