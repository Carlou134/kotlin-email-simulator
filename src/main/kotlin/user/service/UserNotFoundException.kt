package user.service

class UserNotFoundException (
    user: String
) : RuntimeException("User: $user not found")