import email.infrastructure.InMemoryEmailRepository
import email.service.InboxService
import user.infrastructure.InMemoryUserRepository


fun main(){
    val nombreCompleto:String = "Carlos Vásquez"

    println("Bienvenido a tu bandeja de entrada de Email!")
    println("Desarrollado por $nombreCompleto")
    println("El nombre de la aplicación es kotlin email simulator")

    print("Sistema iniciado....")
    println("¡Listo!")

    var userRepository = InMemoryUserRepository()
    val emailRepository = InMemoryEmailRepository()

    val service = InboxService(
        userRepository = userRepository,
        emailRepository = emailRepository
    )

    println("========Inbox Simulator========")
    println("Registrate (usuario):")
    val usernameRegister = readLine()?.trim()!!
    println("Registrate (clave):")
    val passwordRegister = readLine()?.trim()!!

    service.register(usernameRegister, passwordRegister)

    println("Login (usuario):")
    val usernameLogin = readLine()?.trim()!!
    println("Login (clave):")
    val passwordLogin = readLine()?.trim()!!

    val currentLogin = try {
        val user = service.login(usernameLogin, passwordLogin)
        println("Datos usuario: ${user.username}")
    } catch (ex: Exception) {
        println("Error $ex")
        return
    }

}
