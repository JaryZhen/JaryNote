package jary.note.undertow.handle;

import brave.Tracing;
import brave.servlet.TracingFilter;
import io.undertow.Undertow;
import io.undertow.Undertow.ListenerBuilder;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.EnumSet;

public class TracingServletContextListener implements ServletContextListener {
    Sender sender = OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans");
    AsyncReporter<Span> spanReporter = AsyncReporter.create(sender);
    Tracing tracing = Tracing.newBuilder()
            .localServiceName("my-service-name")
            .spanReporter(spanReporter).build();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent
                .getServletContext()
                .addFilter("tracingFilter", TracingFilter.create(tracing))
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            tracing.close(); // disables Tracing.current()
            spanReporter.close(); // stops reporting thread and flushes data
            sender.close(); // closes any transport resources
        } catch (IOException e) {
            // do something real
        }
    }
}
