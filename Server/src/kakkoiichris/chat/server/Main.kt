package kakkoiichris.chat.server

import kakkoiichris.chat.common.PORT
import java.io.IOException
import java.net.ServerSocket

fun main() {
    try {
        val socket = ServerSocket(PORT)

        val server = Server(socket)

        server.open()
    }
    catch (ex: IOException) {
        println("Server exception: ${ex.message}")

        ex.printStackTrace()
    }
}