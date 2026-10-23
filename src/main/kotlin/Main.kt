import email.domain.Folder
import email.infrastructure.InMemoryEmailRepository
import email.service.InboxService
import user.infrastructure.InMemoryUserRepository
import java.util.UUID


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

    val currentUser = try {
        val user = service.login(usernameLogin, passwordLogin)
        println("Datos usuario: ${user.username}")
        user
    } catch (ex: Exception) {
        println("Error $ex")
        return
    }

    try {
        while (true) {
            println(
                """
            |---Menú-------
            | 1) Enviar correo
            | 2) Listar carpetas
            | 3) Marcar leído
            | 4) Mover correo
            | 5) Eliminar correo
            | 6) Recibir un correo simulado
            | 7) Salir
        """.trimIndent()
            )

            when (readLine()?.trim()) {
                "1" -> {
                    print("Para (usuario): ")
                    val to = readLine()!!.trim()
                    print("Asunto: ")
                    val sub = readLine()!!.trim()
                    print("Cuerpo: ")
                    val body = readLine()!!.trim()

                    service.sendEmail(currentUser, to, sub, body)
                    println("Correo enviado.")
                }

                "2" -> {
                    print("Carpeta (INBOX/SENT/ARCHIVE/SPAM): ")
                    val f = Folder.valueOf(readLine()!!.trim().uppercase())
                    val list = service.listByFolder(currentUser, f)
                    if (list.isEmpty()) println("--Vacío--")
                    else {
                        list.forEachIndexed { index, mail ->
                            println("$index ) email: $mail")
                        }
                    }
                }

                "3" -> {
                    print("ID a marcar como leído: ")
                    val id = UUID.fromString(readLine()!!.trim())
                    service.markAsRead(currentUser, id)
                }

                "4" -> {
                    print("ID a mover:")
                    val id = UUID.fromString(readLine()!!.trim())
                    print("Nueva carpeta")
                    val nf = Folder.valueOf(readLine()!!.trim().uppercase())
                    service.moveToFolder(currentUser, id, nf)
                    println("Correo movido")
                }

                "5" -> {
                    print("ID a mover:")
                    val id = UUID.fromString(readLine()!!.trim())
                    service.deleteMail(currentUser, id)
                }

                "6" -> {
                    print("De (usuario): ")
                    val from = readLine()!!.trim()
                    print("Asunto: ")
                    val sub = readLine()!!.trim()
                    print("Cuerpo: ")
                    val body = readLine()!!.trim()
                    service.receiveEmail(currentUser, from, sub, body)
                    println("Correo recibido a inbox")
                }

                "7" -> {
                    println("Adiós")
                    break
                }
            }
        }
    }
    catch (ex: Exception) {
        println("Error: ${ex.message}")
    }
}
