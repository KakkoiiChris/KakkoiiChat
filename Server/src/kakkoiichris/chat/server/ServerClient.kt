package kakkoiichris.chat.server

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ServerThread(private val server: Server, private val socket: Socket) : Thread() {
    override fun run() {
        try {
            val input = socket.getInputStream()
            val reader = BufferedReader(InputStreamReader(input))

            val output = socket.getOutputStream()
            val writer = PrintWriter(output, true)

            var text: String

            do {
                text = reader.readLine() ?: ""
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
}