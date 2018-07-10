package jary.note.nonblocking;

import io.undertow.predicate.Predicates;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.PredicateHandler;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;

import javax.servlet.ServletContext;

public class NonBlockingHandlerExtension implements ServletExtension {

    @Override
    public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {
        deploymentInfo.addInitialHandlerChainWrapper(new HandlerWrapper() {
            @Override
            public HttpHandler wrap(final HttpHandler handler) {

                //we use a path handler to either delegate to our non-blocking server
                //or forward through to the default servlet handler
                final PathHandler pathHandler = new PathHandler();
                //pathHandler.addPath("/", handler); //if nothing matches just forward to the servlet chain
                pathHandler.addPath("/hello", new HelloWorldHandler());

                final ResourceHandler resourceHandler = new ResourceHandler()
                        .setResourceManager(deploymentInfo.getResourceManager());

                PredicateHandler predicateHandler = new PredicateHandler(Predicates.suffix(".css"), resourceHandler, pathHandler);

                return predicateHandler;
            }
        });
    }
}
