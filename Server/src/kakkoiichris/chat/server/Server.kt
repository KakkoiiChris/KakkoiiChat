package kakkoiichris.chat.server

import java.awt.BorderLayout
import java.awt.Dimension
import java.net.ServerSocket
import javax.swing.*

class Server(val socket: ServerSocket): Thread("kakkoii_chat_server") {
    private val frame = JFrame("KakkoiiChat Server")
    private val output = JTextPane()
    private val input = JTextField()

    init {
        val size = Dimension(640, 480)

        val inPanel = JPanel()

        with(inPanel) {
            layout = BorderLayout()

            add(input, BorderLayout.CENTER)
        }

        with(frame.contentPane) {
            minimumSize = size
            preferredSize = size
            layout = BorderLayout()

            val scroll = JScrollPane(output)
            add(scroll, BorderLayout.CENTER)

            add(inPanel, BorderLayout.SOUTH)
        }

        with(frame) {
            pack()
            setLocationRelativeTo(null)
        }
    }

    fun open() {
        frame.isVisible = true

        start()
    }

    fun write(message: String) {
        output.document.insertString(output.document.length, "$message\n", null)
    }

    override fun run() {
        while (true) {
            val socket = socket.accept()

            write("New client connected @ ${socket.inetAddress}")

            ServerThread(this, socket).start()
        }
    }
}