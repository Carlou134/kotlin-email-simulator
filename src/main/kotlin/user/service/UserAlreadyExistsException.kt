package user.service

class UserAlreadyExistsException (
    user: String
) : RuntimeException("User already exists")