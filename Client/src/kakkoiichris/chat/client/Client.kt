package kakkoiichris.chat.client

import java.awt.BorderLayout
import java.awt.Dimension
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.swing.*

class Client(private val socket: Socket) : Thread("kakkoii_chat_client") {
    private val frame = JFrame("KakkoiiChat Client")
    private val output = JTextPane()
    private val input = JTextField()

    private val writer = PrintWriter(socket.getOutputStream(), true)
    private val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

    init {
        input.addActionListener {
            val message = input.text
            input.text = ""

            write(message)
            send(message)
        }

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

    fun send(message: String) {
        writer.println(message)
    }

    override fun run() {
        while (true) {
            val result = reader.readLine()

            write(result)
        }

        socket.close()
    }
}