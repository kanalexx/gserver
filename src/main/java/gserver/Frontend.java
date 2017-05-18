package gserver;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alexander Kanunnikov
 */

public class Frontend extends AbstractHandler {
    private static final Logger LOGGER = Logger.getLogger(Frontend.class);
    private AtomicInteger sessionIdGenerator = new AtomicInteger(0);
    private AtomicInteger userIdGenerator = new AtomicInteger(0);
    private Map<String, UserSession> users = new HashMap<>();

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException, ServletException {
        Map<String, Object> pageVariables = new HashMap<>();
        if ("POST".equals(request.getMethod())) {
            String sessionId = request.getParameter("sessionId");
            String userName = request.getParameter("userName");
            if (sessionId != null) {
                UserSession userSession = users.get(sessionId);
                if (userSession == null) {
                    userSession = new UserSession(Integer.valueOf(sessionId), userName, userIdGenerator.addAndGet(1));
                    users.put(sessionId, userSession);
                }
                pageVariables.put("userSession", userSession);
            }
        } else {
            pageVariables.put("userSession", new UserSession(sessionIdGenerator.addAndGet(1), "", 0));
        }
        httpServletResponse.setContentType("text/html;charset=utf-8");
        httpServletResponse.getWriter().print(PageGenerator.getPage("index.html", pageVariables));
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        request.setHandled(true);
    }
}