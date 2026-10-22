package user.domain

interface UserRepository{
    fun save(user: User): Boolean
    fun findByUsername(username: String): User?
}