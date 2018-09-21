package org.honeyrock.web;

import org.apache.commons.io.IOUtils;
import org.honeyrock.dao.MemberDAO;
import org.honeyrock.dao.QuestionDAO;
import org.honeyrock.domain.*;
import org.honeyrock.web.util.Converter;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/admin/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100) // 100 MB
public class QuestionController extends AbstractController {

    private QuestionDAO dao = new QuestionDAO();
    private MemberDAO mdao = new MemberDAO();
    boolean updateable = false;


    public String listGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("listGET.......................");

        PageDTO dto = PageDTO.of()
                .setPage(Converter.getInt(req.getParameter("page"), 1))
                .setSize(Converter.getInt(req.getParameter("size"), 10));

        PageMaker pageMaker = new PageMaker(dao.getMaxQno(), dto);
        req.setAttribute("pageMaker", pageMaker);
        req.setAttribute("list", dao.getList(dto));

        return "list";
    }
    public String readGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("readGET.......................");

        String qnoStr = req.getParameter("qno");
        int qno = Converter.getInt(qnoStr, -1);
        if (qno == -1) {
            throw new Exception(("Invalid data"));
        }

        QuestionVO vo = dao.getQuestion(qno);

        req.setAttribute("qus", vo);
//        String fileName = req.getParameter("qfile");

//        String ext = fileName.substring(fileName.lastIndexOf(".")+1);
//        System.out.println("-=================ext=================================");
//        System.out.println(fileName);
//        System.out.println(ext);
//        System.out.println("-=================filename=================================");
//        if(ext.equals("jpg")) {
//            resp.setContentType("image/jpeg");
//        }else {   //jpg 가 아닌경우 다운로드
//            resp.setContentType("application/octet-stream");
//            resp.setHeader("Content-disposition", "attachment; filename="+ fileName);
//        }
//
//        OutputStream os = resp.getOutputStream();
//        InputStream in = new FileInputStream("C:\\zzz\\upload\\"+ fileName);
//
//        IOUtils.copy(in, os);

        return "read";
    }
    public String writeGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        return "write";
    }
    public String writePOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String qtitle = req.getParameter("qtitle");
        String unoStr = req.getParameter("uno");
        String qcmt = req.getParameter("qcmt");
        String qfile = req.getParameter("qfile");
        String uname = req.getParameter("uname");
        QuestionVO vo = new QuestionVO();
        vo.setQtitle(qtitle);
        vo.setUno(Integer.parseInt(unoStr));
        vo.setUname(uname);
        vo.setQcmt(qcmt);
        vo.setQfile(qfile);

        dao.addQuestion(vo);

        return "redirect/list";
    }
    public String modifyGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int qno = Integer.parseInt(req.getParameter("qno"));
        QuestionVO vo = dao.getQuestion(qno);
        req.setAttribute("qus", vo);

        return "modify";
    }
    public String modifyPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        req.setCharacterEncoding("UTF-8");

        String qnoStr = req.getParameter("qno");
        String unoStr = req.getParameter("uno");
        String qtitle = req.getParameter("qtitle");
        String qcmt = req.getParameter("qcmt");
        String qfile = req.getParameter("qfile");
        int page = Integer.parseInt(req.getParameter("page"));
        int qno = Integer.parseInt(qnoStr);

//        MovieVO vo = new MovieVO();
        QuestionVO vo = dao.getQuestion(qno);
        vo.setQno(qno);
        vo.setUno(Integer.parseInt(unoStr));
        vo.setQtitle(qtitle);
        vo.setQcmt(qcmt);
        vo.setQfile(qfile);

        dao.updateQuestion(qno, vo);
        return "redirect/list?page=" + page;
    }
    public String removePOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int qno = Integer.parseInt(req.getParameter("qno"));
        dao.deleteQuestion(qno);           //수정해야함
        return "redirect/list";
    }
    public String understandGET(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        return "understand";
    }
    public String understandPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        req.setCharacterEncoding("UTF-8");

        String unqtitle = req.getParameter("unqtitle");
        String unqgubunStr = req.getParameter("unqgubun");
        String unqlimitStr = req.getParameter("unqlimit");

        UnderstandVO vo = new UnderstandVO();
        vo.setUnqtitle(unqtitle);
        vo.setUnqgubun(Integer.parseInt(unqgubunStr));
        vo.setUnqlimit(Integer.parseInt(unqlimitStr));

        dao.addUnderstand(vo);

        return "redirect/question";

    }
    public String questionGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        PageDTO dto = PageDTO.of()
                .setPage(Converter.getInt(req.getParameter("page"), 1))
                .setSize(Converter.getInt(req.getParameter("size"), 10));

        PageMaker pageMaker = new PageMaker(dao.getMaxUnqno(), dto);
        req.setAttribute("pageMaker", pageMaker);
        req.setAttribute("question", dao.getUnquestion(dto));

        return "question";
    }
    public String resultGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        PageDTO dto = PageDTO.of()
                .setPage(Converter.getInt(req.getParameter("page"), 1))
                .setSize(Converter.getInt(req.getParameter("size"), 10));

        PageMaker pageMaker = new PageMaker(dao.getMaxUno(), dto);
        req.setAttribute("pageMaker", pageMaker);
        int unqno =Integer.parseInt(req.getParameter("unqno"));
        req.setAttribute("list", dao.getResult(unqno));
        req.setAttribute("resultList", dao.getResultCalc(unqno));
        return "result";
    }
    public String boardGET(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        return "board";
    }
    public String userListGET(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        req.setAttribute("userList",dao.getUserList());
        return "userList";
    }
    public String userManagerGET(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        int uno =Integer.parseInt(req.getParameter("uno"));
        req.setAttribute("info",dao.getUserInfo(uno));
        req.setAttribute("qlist",dao.getUserQlist(uno));

        return "userManager";
    }
    public String userRemovePOST(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        int uno =Integer.parseInt(req.getParameter("uno"));
        dao.deleteUser(uno);

        return "redirect/userList";
    }



    @Override
    public String getBasic() {
        return "/admin/";
    }
}
