package org.honeyrock.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/logout")
public class LogoutController extends AbstractController{

    public String logoutPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        HttpSession session = req.getSession();
        session.invalidate();
        return "redirect/login";
    }

    @Override
    public String getBasic() {
        return "/";
    }
}
