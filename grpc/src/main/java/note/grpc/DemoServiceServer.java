package note.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.internal.ServerImpl;
import io.grpc.netty.NettyServerBuilder;
import jary.grpc.study.service.DemoServiceGrpc;

/**
 * Created by Jary on 2017/10/27 0027.
 */
public class DemoServiceServer {
    private int port = 50051;
    private Server server;

    private void start() throws Exception {
       /* server = NettyServerBuilder.forPort(port)
                .addService(new DemoServiceGrpc.DemoServiceBlockingStub(new DemoServiceImpl()))
                .build().start();

        server = InProcessServerBuilder.forName("testServer")
                .addService(DemoServiceGrpc.bindService(new DemoServiceImpl()))
                .build().start();
                       server = NettyServerBuilder.forPort(port).addService(new DemoServiceImpl()).build().start();

*/
        server = ServerBuilder.forPort(port).addService(new DemoServiceImpl()).build().start();


       // server = InProcessServerBuilder.forName("testServer").addService(new DemoServiceImpl()).build().start();

        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("*** shutting down gRPC server since JVM is shutting down");
                DemoServiceServer.this.stop();
                System.out.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        final DemoServiceServer server = new DemoServiceServer();
        server.start();
        server.blockUntilShutdown();

    }
}
