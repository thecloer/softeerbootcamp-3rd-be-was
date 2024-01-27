package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();

    private final ThreadPoolExecutor executorService;

    private WebServer() {
        int corePoolSize = PROCESSOR_COUNT * 3;
        int maximumPoolSize = PROCESSOR_COUNT * 20;
        long keepAliveTime = 60L;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

        this.executorService = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                workQueue
        );
    }

    public static void main(String args[]) throws Exception {
        int port = (args == null || args.length == 0) ? DEFAULT_PORT : Integer.parseInt(args[0]);

        WebServer webServer = new WebServer();
        webServer.listen(port);
    }

    private void listen(int port) {
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null)
                executorService.submit(new RequestHandler(connection));

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            executorService.shutdown();
            logger.info("Web Application Server stopped");
        }
    }
}
