package jary.note.nonblocking;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import javax.servlet.ServletException;

public class Main {
    public static void main(String[] args) throws ServletException {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(Main.class.getClassLoader())
                .setContextPath("/myapp")
                .setDeploymentName("test.war")
                .addServlets(
                        Servlets.servlet("MessageServlet", SimpleServlet.class)
                                .addInitParam("message", "Hello World")
                                .addMapping("/ab"));

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        PathHandler path = Handlers.path();

        path.addPrefixPath(servletBuilder.getContextPath(), manager.start());
        //path.addExactPath("/hello",new HelloWorldHandler());

        Undertow server = Undertow.builder()
                .addHttpListener(8089, "localhost")
                .setHandler(path)
                .build();
        server.start();
    }
}
