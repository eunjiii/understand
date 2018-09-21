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

@WebServlet(urlPatterns = "/user/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100) // 100 MB=\\
public class QusController extends AbstractController {

    private QuestionDAO dao = new QuestionDAO();
    private MemberDAO mdao = new MemberDAO();
    boolean updateable = false;


    public String qusGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        return "qus";
    }

    public String qusPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String qtitle = req.getParameter("qtitle");
        String unoStr = req.getParameter("uno");
        String qcmt = req.getParameter("qcmt");


        QuestionVO vo = new QuestionVO();
        vo.setQtitle(qtitle);
        vo.setUno(Integer.parseInt(unoStr));
        vo.setQcmt(qcmt);



        Collection<Part> parts = req.getParts();

        parts.stream().filter(part -> part.getContentType() != null).forEach(part -> {


            String uploadName = UUID.randomUUID() + "_" + part.getSubmittedFileName();

            System.out.println("===========================================");
            System.out.println(uploadName);

            try {
                InputStream in = part.getInputStream();
                FileOutputStream fos = new FileOutputStream("C:\\zzz\\upload\\" + uploadName);

                IOUtils.copy(in, fos);

                vo.setQfile(uploadName);

                in.close();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });



        dao.addQuestion(vo);

        return "redirect/quslist?result=success";
    }

    public String quslistGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        PageDTO dto = PageDTO.of()
                .setPage(Converter.getInt(req.getParameter("page"), 1))
                .setSize(Converter.getInt(req.getParameter("size"), 10));

        PageMaker pageMaker = new PageMaker(dao.getMaxQno(), dto);
        req.setAttribute("pageMaker", pageMaker);
        req.setAttribute("list", dao.getList(dto));

        return "quslist";
    }

    public String qusreadGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String qnoStr = req.getParameter("qno");
        int qno = Converter.getInt(qnoStr, -1);
        if (qno == -1) {
            throw new Exception(("Invalid data"));
        }

        QuestionVO vo = dao.getQuestion(qno);

        req.setAttribute("qus", vo);

        return "qusread";
    }
    @Override
    public String getBasic() {
        return "/user/";
    }
}
