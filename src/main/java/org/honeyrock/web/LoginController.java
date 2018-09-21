package org.honeyrock.web;

import org.honeyrock.dao.MemberDAO;
import org.honeyrock.domain.MemberVO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@WebServlet(urlPatterns = "/login")
public class LoginController extends AbstractController {

    public String loginGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return "login";
    }
    public String loginPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        MemberDAO dao = new MemberDAO();
        List<MemberVO> memberVOList = dao.getList();
        System.out.println("Login post.....");
        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        System.out.println(id + " : " + pw);



        MemberVO vo = new MemberVO();
        vo.setUserid(id);
        vo.setUserpw(pw);
        for (MemberVO vo1 : memberVOList) {
            if (id.equals(vo1.getUserid()) && pw.equals(vo1.getUserpw())) {
                vo.setUname(vo1.getUname());
                vo.setUno(vo1.getUno());
                HttpSession session = req.getSession();
                String urole = vo1.getUrole();
                String userusing = vo1.getUserusing();
                session.setAttribute("member", vo);
                if(urole.equals("user") && userusing.equals("Y")){
                    return "redirect/user/qus";
                }else if(urole.equals("user") && userusing.equals("N")){
                    return "redirect/login";
                }
                if (vo != null) {
                    session.setAttribute("member", vo);
                }

                return "redirect/admin/board";
            }
        }
        return "redirect/login";
    }

    @Override
    public String getBasic() {
        return "/";
    }
}
