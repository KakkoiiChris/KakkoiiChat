package kakkoiichris.chat.client

import kakkoiichris.chat.common.HOST
import kakkoiichris.chat.common.PORT
import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException

fun main() {
    try {
        val socket = Socket(HOST, PORT)

        val client = Client(socket)

        client.open()
    }
    catch (ex: UnknownHostException) {
        println("Server not found: ${ex.message}")
    }
    catch (ex: IOException) {
        println("I/O error: ${ex.message}")
    }
}