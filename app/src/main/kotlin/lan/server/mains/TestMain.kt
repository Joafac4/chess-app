package lan.server.mains

import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.Server
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder
import lan.client.listeners.ClientConnectionListenerImpl
import java.net.InetSocketAddress


fun main(args: Array<String>) {
    TestMain().main(args)
}


class TestMain {

    fun main(args: Array<String>) {
        val server = createServer()
        server.start()
        Thread.sleep(200)
        val client = createClient()
        client.connect()
        Thread.sleep(200)
        client.closeConnection()
        Thread.sleep(200)
        server.stop()
    }




    fun createServer() : Server {
        return NettyServerBuilder.createDefault()
            .withPort(8080)
           // .withConnectionListener(ServerConnectionListenerImpl())
            .build()
    }

    fun createClient() : Client {
        var client = NettyClientBuilder.createDefault()
            .withConnectionListener(ClientConnectionListenerImpl())
            .withAddress(InetSocketAddress("localhost", 8080))
            .build()

        return client
    }


}

