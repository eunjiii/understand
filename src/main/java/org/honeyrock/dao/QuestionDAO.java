package org.honeyrock.dao;

import org.honeyrock.domain.*;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private static final String LIST =  "select * from (select/*+INDEX_DESC(tbl_question_board pk_question_board) */\n" +
            "ROWNUM rn, qno, uname, qtitle, qcmt, qfile, QREGDATE\n" +
            "from TBL_question_board,(select uno, uname from tbl_unmember) mem\n" +
            "where QNO > 0\n" +
            "AND ROWNUM <= (? * ?)\n" +
            "and mem.uno=tbl_question_board.uno)\n" +
            "where rn > ((? -1) * ?)";

    private static final String RESULTLIST =  "select * from (select\n" +
            "            ROWNUM rn, mem.uname,UNQNO, anqty, anqtitle, anqREGDATE\n" +
            "            from TBL_Answer_board,(select uno, uname from tbl_unmember) mem\n" +
            "            where UNQNO = ?\n" +
            "            and mem.uno=tbl_Answer_board.uno)\n" +
            "            where rn > 0";

    private static final String QUESTION =  "select * from (select/*+INDEX_DESC(tbl_understand_board pk_understand_board) */\n" +
            "ROWNUM rn, unqno, mem.uname, unqtitle, unqgubun, unqlimit, unqregdate\n" +
            "from TBL_understand_board,(select uno, uname from tbl_unmember) mem\n" +
            "where UNQNO > 0\n" +
            "AND ROWNUM <= (? * ?)\n" +
            "and mem.uno=tbl_understand_board.uno)\n" +
            "where rn > ((? -1) * ?)";

    private static final String MAXUNQNO = "select count(*) from tbl_understand_board";
    private static final String MAXQNO = "select count(*) from tbl_question_board";
    private static final String MAXUNO = "select count(*) from tbl_unmember";
    private static final String READ = "select qno, mem.uno, uname, qtitle, qregdate, qcmt, qfile \n" +
            "from (select uno, uname from tbl_unmember) mem, tbl_question_board \n" +
            "where qno = ? \n" +
            "and mem.uno=tbl_question_board.uno";

    private final String WRITE = "insert into tbl_question_board (qno, uno, qtitle, qcmt, qfile)\n" +
            "values (seq_question_board.nextval, ?, ?, ?, ?)\n";

    private final String DELETE = "delete from tbl_question_board where qno = ?";

    private final String UPDATE = "update tbl_question_board set qtitle = ?,uno = ?, qcmt = ?, qfile = ? where qno= ?";

    private final String UNWRITE ="insert into tbl_understand_board (unqno, uno, unqtitle, unqgubun, unqlimit)\n" +
            "values (seq_understand_board.nextval, 1, ?, ?, (select sysdate + (?/1440) m_minute from dual))\n";

    private final String RESULTCALC ="select \n" +
            "  t2.qct, nvl(t1.cnt,0) cnt, round((nvl(t1.cnt,0)/sum(nvl(t1.cnt,0)) over())*100) total\n" +
            "from \n" +
            "  (select anqty qct, count(uno) cnt\n" +
            "    from tbl_answer_board\n" +
            "    where unqno=?\n" +
            "    group by anqty ) t1, \n" +
            "  (select 0 qct from dual\n" +
            "    union all \n" +
            "    select 25 qct from dual\n" +
            "    union all \n" +
            "    select 50 qct from dual\n" +
            "    union all \n" +
            "    select 75 qct from dual\n" +
            "    union all \n" +
            "    select 100 qct from dual \n" +
            "  ) t2\n" +
            "where t1.qct(+) = t2.qct  \n" +
            "order by 1" ;

    private final String USERLIST = "select uno, userid, uname, regdate, userusing from tbl_unmember\n" +
            "order by uno asc";
    private final String USERINFO = "select uno, userid, uname, regdate from tbl_unmember\n" +
            "where uno = ?";

    private final String USERQLIST = "select t1.uno, unqtitle, anqtitle, anqty, anqregdate\n" +
            "from\n" +
            "(select un.unqno,unqtitle, anqtitle, anqty ,uno, anqregdate\n" +
            "from \n" +
            "(select unqtitle,unqno\n" +
            "from tbl_understand_board) un, tbl_answer_board \n" +
            "where un.unqno=tbl_answer_board.unqno) t1, tbl_unmember mem\n" +
            "where t1.uno=mem.uno\n" +
            "and mem.uno=?\n" +
            "order by anqregdate desc";

    private final String USERREMOVE = "UPDATE tbl_unmember\n" +
            "set userusing = 'N' " +
            "where uno = ?" ;

    public List<QuestionVO> getList(final PageDTO pageDTO) throws Exception{

        final List<QuestionVO> list = new ArrayList<>();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(LIST);
                int i = 1;
                stmt.setInt(i++, pageDTO.getPage());
                stmt.setInt(i++, pageDTO.getSize());
                stmt.setInt(i++, pageDTO.getPage());
                stmt.setInt(i++, pageDTO.getSize());
                rs = stmt.executeQuery();

                while (rs.next()) {
                    QuestionVO vo = new QuestionVO();
                    int idx = 2;
                    vo.setQno(rs.getInt(idx++));
                    vo.setUname(rs.getString(idx++));
                    vo.setQtitle(rs.getString(idx++));
                    vo.setQcmt(rs.getString(idx++));
                    vo.setQfile(rs.getString(idx++));
                    vo.setQregdate(rs.getDate(idx++));

                    list.add(vo);
                }
            }
        }.executeAll();
        return list;
    }

    public int getMaxQno(){
        final int[] maxQno = {0};
        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {
                stmt = con.prepareStatement(MAXQNO);
                rs = stmt.executeQuery();
                rs.next();
                maxQno[0] = rs.getInt(1);

            }
        }.executeAll();

        return maxQno[0];
    }

    public int getMaxUnqno(){
        final int[] maxUnqno = {0};
        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {
                stmt = con.prepareStatement(MAXUNQNO);
                rs = stmt.executeQuery();
                rs.next();
                maxUnqno[0] = rs.getInt(1);

            }
        }.executeAll();

        return maxUnqno[0];
    }

    public int getMaxUno(){
        final int[] maxUno = {0};
        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {
                stmt = con.prepareStatement(MAXUNO);
                rs = stmt.executeQuery();
                rs.next();
                maxUno[0] = rs.getInt(1);

            }
        }.executeAll();

        return maxUno[0];
    }

    public QuestionVO getQuestion(final int qno) {
        final QuestionVO vo = new QuestionVO();

        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(READ);
                stmt.setInt(1,qno);
                rs = stmt.executeQuery();
                while(rs.next()) {
                    int idx = 1;
                    vo.setQno(rs.getInt(idx++));
                    vo.setUno(rs.getInt(idx++));
                    vo.setUname(rs.getString(idx++));
                    vo.setQtitle(rs.getString(idx++));
                    vo.setQregdate(rs.getDate(idx++));
                    vo.setQcmt(rs.getString(idx++));
                    vo.setQfile(rs.getString(idx++));
                }
            }
        }.executeAll();

        //code
        return vo;
    }

    public void addQuestion(final QuestionVO vo) {

        //uno, qtitle, qcmt, qfile
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(WRITE);
                stmt.setInt(1, vo.getUno());
                stmt.setString(2,vo.getQtitle());
                stmt.setString(3,vo.getQcmt());
                stmt.setString(4,vo.getQfile());
                stmt.executeUpdate();
            }
        }.executeAll();
    }

    public void deleteQuestion(final int qno) {


        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(DELETE);
                stmt.setInt(1,qno);
                stmt.executeUpdate();
            }
        }.executeAll();
    }

    public void updateQuestion(final int qno,final QuestionVO vo) {


        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(UPDATE);
                //qtitle = ?,uno = ?, qcmt = ?, qfile = ? where qno= ?
                stmt.setString(1,vo.getQtitle());
                stmt.setInt(2,vo.getUno());
                stmt.setString(3,vo.getQcmt());
                stmt.setString(4,vo.getQfile());
                stmt.setInt(5,vo.getQno());

                stmt.executeUpdate();
            }
        }.executeAll();
    }

    public void addUnderstand(final UnderstandVO vo){

        // uno, unqtitle, unqgubun, unqlimit
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(UNWRITE);
         //       stmt.setInt(1, vo.getUno());
                stmt.setString(1,vo.getUnqtitle());
                stmt.setInt(2,vo.getUnqgubun());
                stmt.setInt(3,vo.getUnqlimit());
                stmt.executeUpdate();
            }
        }.executeAll();

    }

    public List<UnderstandVO> getUnquestion(final PageDTO pageDTO) throws Exception{

        final List<UnderstandVO> list = new ArrayList<>();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(QUESTION);
                int i = 1;
                stmt.setInt(i++, pageDTO.getPage());
                stmt.setInt(i++, pageDTO.getSize());
                stmt.setInt(i++, pageDTO.getPage());
                stmt.setInt(i++, pageDTO.getSize());
                rs = stmt.executeQuery();

                while (rs.next()) {
                    UnderstandVO vo = new UnderstandVO();
                    int idx = 2;
                    vo.setUnqno(rs.getInt(idx++));
                    vo.setUname(rs.getString(idx++));
                    vo.setUnqtitle(rs.getString(idx++));
                    vo.setUnqgubun(rs.getInt(idx++));
                    vo.setUnqlimitDate(rs.getDate(idx++));
                    vo.setUnqregdate(rs.getDate(idx++));
                    list.add(vo);
                }
            }
        }.executeAll();
        return list;
    }

    public List<AnswerVO> getResult(int unqno) throws Exception{

        final List<AnswerVO> list = new ArrayList<>();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(RESULTLIST);
                stmt.setInt(1,unqno);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    AnswerVO vo = new AnswerVO();
                    int idx = 2;
                    vo.setUname(rs.getString(idx++));
                    vo.setUnqno(rs.getInt(idx++));
                    vo.setAnqty(rs.getInt(idx++));
                    vo.setAnqtitle(rs.getString(idx++));
                    vo.setAnregdate(rs.getDate(idx++));
                    list.add(vo);
                }
            }
        }.executeAll();
        return list;
    }

    public List<ResultVO> getResultCalc(int unqno) throws Exception{

        final List<ResultVO> list = new ArrayList<>();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(RESULTCALC);
                //       stmt.setInt(1, vo.getUno());
                stmt.setInt(1,unqno);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    ResultVO vo = new ResultVO();
                    int idx = 1;

                    vo.setQct(rs.getInt(idx++));
                    vo.setCnt(rs.getInt(idx++));
                    vo.setTotal(rs.getInt(idx++));

                    list.add(vo);
                }
            }
        }.executeAll();
        return list;
    }

    public List<MemberVO> getUserList() throws Exception{

        final List<MemberVO> list = new ArrayList<>();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(USERLIST);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    // uno, userid, uname, regdate
                    MemberVO vo = new MemberVO();
                    int idx = 1;
                    vo.setUno(rs.getInt(idx++));
                    vo.setUserid(rs.getString(idx++));
                    vo.setUname(rs.getString(idx++));
                    vo.setRegdate(rs.getDate(idx++));
                    vo.setUserusing(rs.getString(idx++));
                    list.add(vo);
                }
            }
        }.executeAll();
        return list;
    }

    public MemberVO getUserInfo(int uno) throws Exception{
        MemberVO vo = new MemberVO();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(USERINFO);
                stmt.setInt(1,uno);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    //ans.uno, userid, uname, regdate, unqtitle, anqtitle, anqty
                    int idx = 1;
                    vo.setUno(rs.getInt(idx++));
                    vo.setUserid(rs.getString(idx++));
                    vo.setUname(rs.getString(idx++));
                    vo.setRegdate(rs.getDate(idx++));
                }
            }
        }.executeAll();
        return vo;
    }

    public List<MemberVO> getUserQlist(int uno) throws Exception{

        final List<MemberVO> list = new ArrayList<>();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(USERQLIST);
                stmt.setInt(1,uno);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    // uno, userid, uname, regdate
                    MemberVO vo = new MemberVO();
                    int idx = 1;
                    vo.setUno(rs.getInt(idx++));
                    vo.setUnqtitle(rs.getString(idx++));
                    vo.setAnqtitle(rs.getString(idx++));
                    vo.setAnqty(rs.getInt(idx++));
                    vo.setAnqregdate(rs.getDate(idx++));
                    list.add(vo);
                }
            }
        }.executeAll();
        return list;
    }

    public void deleteUser(final int uno) {


        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(USERREMOVE);
                stmt.setInt(1,uno);
                stmt.executeUpdate();
            }
        }.executeAll();
    }
}
