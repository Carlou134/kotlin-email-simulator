package user.infrastructure

import user.domain.User
import user.domain.UserRepository

class InMemoryUserRepository: UserRepository{
    private val users = mutableListOf<User>()

    override fun save(user: User): Boolean = users.add(user)

    override fun findByUsername(username: String): User? {
        return users.firstOrNull { username == it.username }
    }
}

