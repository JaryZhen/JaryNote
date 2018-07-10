package jary.note.core;

import javax.servlet.*;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

public class MyServeletContainerInitializer implements ServletContainerInitializer {
    public static LinkedBlockingDeque<String> DEQUE = new LinkedBlockingDeque<>();

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext ctx) throws ServletException {
        ctx.addListener(new DynamicListener());
        ctx.setAttribute("testDL", "foo");
        //Assert.assertNotNull(ctx.getAttribute(ServletContext.TEMPDIR));

    }
    public static class DynamicListener implements ServletContextAttributeListener {

        @Override
        public void attributeAdded(ServletContextAttributeEvent event) {
            DEQUE.add("dl add " + event.getName());
        }

        @Override
        public void attributeRemoved(ServletContextAttributeEvent event) {
            DEQUE.add("dl remove " + event.getName());
        }

        @Override
        public void attributeReplaced(ServletContextAttributeEvent event) {
            DEQUE.add("dl replace " + event.getName());
        }
    }
}
