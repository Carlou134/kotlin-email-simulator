package user.service

class AuthenticationException(
    user: String
) : RuntimeException("Password or user incorrect")