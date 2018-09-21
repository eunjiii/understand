package org.honeyrock.dao;

import org.honeyrock.domain.MemberVO;

import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    private static final String sql = "select * from tbl_unmember";

    public List<MemberVO> checkLogin(){

        final List<MemberVO> list = new ArrayList<MemberVO>();


        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {

                stmt=con.prepareStatement(sql);
                rs = stmt.executeQuery();

                while (rs.next()){
                    MemberVO vo = new MemberVO();
                    vo.setUno(rs.getInt("uno"));
                    vo.setUserid(rs.getString("userid"));
                    vo.setUrole(rs.getString("urole"));
                    vo.setRegdate(rs.getDate("regdate"));
                    vo.setUname(rs.getString("uname"));
                    vo.setUserpw(rs.getString("userpw"));
                    list.add(vo);
                }
            }
        }.executeAll();


        return list;
    }
    public List<MemberVO> getList() {
        final List<MemberVO> list = new ArrayList<MemberVO>();


        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    MemberVO vo = new MemberVO();
                    vo.setUno(rs.getInt("uno"));
                    vo.setUserid(rs.getString("userid"));
                    vo.setUrole(rs.getString("urole"));
                    vo.setRegdate(rs.getDate("regdate"));
                    vo.setUname(rs.getString("uname"));
                    vo.setUserpw(rs.getString("userpw"));
                    vo.setUserusing(rs.getString("userusing"));
                    list.add(vo);
                }
            }
        }.executeAll();
        return  list;
    }




}
