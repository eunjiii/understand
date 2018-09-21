package org.honeyrock.filter;

import lombok.extern.log4j.Log4j;
import org.honeyrock.domain.MemberVO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Member;

@Log4j
@WebFilter(urlPatterns = {"/admin/*","/user/*"})
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();

        MemberVO vo = (MemberVO)session.getAttribute("member");

        if(vo ==null){
            resp.sendRedirect("/login");
        }



        filterChain.doFilter(servletRequest, servletResponse);  //다음필터...

//        if(cks == null || cks.length == 0){
//            resp.sendRedirect("/main");
//        }
//
//        boolean check = false;
//        for (Cookie ck:cks) {
//            if(ck.getName().equals("login")){
//                check = true;
//                break;
//            }
//        }//end for
//
//        if(!check){
//            resp.sendRedirect("/login");
//            return;
//        }
//
//        log.info("CHECK RESULT:  "  +  check);

    }
}
