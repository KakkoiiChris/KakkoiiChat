package kakkoiichris.chat.server

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ServerClient(private val server: Server, private val socket: Socket) : Thread() {
    private val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
    private val writer = PrintWriter(socket.getOutputStream(), true)

    override fun run() {
        try {
            do {
                val text = reader.readLine() ?: ""
                val reverseText = text.reversed()

                server.write("@${socket.inetAddress}: $text -> $reverseText")

                writer.println("Server: $reverseText")
            }
            while (text.isNotEmpty())

            socket.close()
        }
        catch (ex: IOException) {
            println("Server exception: ${ex.message}")

            ex.printStackTrace()
        }
    }

    fun send(message: String) {
        writer.println(message)
    }
}