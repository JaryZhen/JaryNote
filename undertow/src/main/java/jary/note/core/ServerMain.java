package jary.note.core;

import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.*;
import jary.note.nonblocking.HelloWorldHandler;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import java.util.Collections;

public class ServerMain {
    public static void main(String[] args) throws ServletException {
        DeploymentInfo builder = new DeploymentInfo()
                .setClassLoader(ServerMain.class.getClassLoader())
                .setContextPath("/")
                .setClassIntrospecter(MyClassIntrospector.INSTANCE)
                .setDeploymentName("listener.war")
                .addServletContainerInitalizer(new ServletContainerInitializerInfo(MyServeletContainerInitializer.class, Collections.emptySet()))
                .addServlet(
                        new ServletInfo("servlet", MyServlet.class)
                                .addInitParam("message2", "hello message")
                                .addMapping("/my")
                ).addListener(new ListenerInfo(MyServletContextListener.class));

        builder.addFilter(new FilterInfo("*", PathFilter.class));
        builder.addFilterUrlMapping("*", "*", DispatcherType.REQUEST);

        ServletContainer container = ServletContainer.Factory.newInstance();
        DeploymentManager manager = container.addDeployment(builder);
        manager.deploy();

        PathHandler path = new PathHandler();
        //PathHandler path = Handlers.path();
        path.addExactPath("/my",new HelloWorldHandler());

        path.addPrefixPath(builder.getContextPath(), manager.start());

        Undertow server = Undertow.builder()
                .addHttpListener(8089, "localhost")
                .setHandler(path)
                .build();
        server.start();
    }
}
