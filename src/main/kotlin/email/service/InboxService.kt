package email.service

import email.domain.Email
import email.domain.Folder
import email.infrastructure.InMemoryEmailRepository
import user.domain.User
import user.infrastructure.InMemoryUserRepository
import user.service.AuthenticationException
import user.service.UserAlreadyExistsException
import user.service.UserNotFoundException

class InboxService (
    private val userRepository: InMemoryUserRepository,
    private val emailRepository: InMemoryEmailRepository
){
    fun register(username: String, password: String){
        if(userRepository.findByUsername(username) != null){
            throw UserAlreadyExistsException(username)
        }
    }

    fun login(username: String, password: String):User{
        val user = userRepository.findByUsername(username) ?: throw UserNotFoundException(username)
        if(!user.authenticate(password)){
            throw AuthenticationException(username)
        }
        return user
    }

    fun sendEmail(user: User, toUsername:String, subject: String, body: String){
        val emailToSend = Email(
            owner = user.id,
            from = user.username,
            to = toUsername,
            subject = subject,
            body = body
        )

        emailRepository.save(emailToSend)

        userRepository.findByUsername(toUsername)?.let {
            val inboxMail = emailToSend.copy(
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
}