package gserver;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Alexander Kanunnikov
 */

public class Frontend extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Frontend.class);
    private AtomicInteger sessionIdGenerator = new AtomicInteger(0);
    private AtomicInteger userIdGenerator = new AtomicInteger(0);
    private Map<String, UserSession> users = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("userSession", new UserSession(sessionIdGenerator.addAndGet(1), "", 0));
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().print(PageGenerator.getPage("index.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String sessionId = req.getParameter("sessionId");
        String userName = req.getParameter("userName");
        if (sessionId != null) {
            UserSession userSession = users.get(sessionId);
            if (userSession == null) {
                userSession = new UserSession(Integer.valueOf(sessionId), userName, userIdGenerator.addAndGet(1));
                users.put(sessionId, userSession);
            }
            pageVariables.put("userSession", userSession);
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().print(PageGenerator.getPage("index.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}