package email.service

class EmailNotFoundException (
    emailId: String
) : RuntimeException("Email: $emailId not found")