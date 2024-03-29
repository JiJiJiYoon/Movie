package com.movieRc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.movieRc.dto.BasketDTO;
import com.movieRc.dto.Like_rDTO;

public class BasketDAO {
	private BasicDataSource bds;
	
	public BasketDAO() {
		try {
			Context iCtx = new InitialContext();
			Context envCtx = (Context)iCtx.lookup("java:comp/env");
			bds = (BasicDataSource)envCtx.lookup("jdbc/bds");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws Exception{
		return bds.getConnection();
	}
	
	// 찜 목록 모두 조회
	public ArrayList<BasketDTO> selectAll(String user_id) throws Exception{
		String sql = "select * from tbl_basket where user_id = ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<BasketDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq_basket = rs.getInt("seq_basket");
				String movieCd = rs.getString("movieCd");
				String movieNm = rs.getString("movieNm");
				String movieNmEn = rs.getString("movieNmEn");
				String prdtYear = rs.getString("prdtYear");
				String user_category = rs.getString("user_category");
				
				list.add(new BasketDTO(seq_basket, user_id, movieCd, movieNm, movieNmEn, prdtYear, user_category));
			}
			return list;
		}
	}

	// 찜 목록에서 삭제
	public int delete(int seq_basket) throws Exception{
		String sql="delete from tbl_basket where seq_basket = ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, seq_basket);
			int rs = pstmt.executeUpdate();
			return rs;
		}
	}
	
	// 찜 목록 정렬(등록순)
	// order by ? 로 해서 정렬방식도 서버에서 넘겨받게 할지... 고민해보기
	public ArrayList<BasketDTO> selectByAdd(String user_id) throws Exception{
		String sql = "select * from tbl_basket where user_id = ? order by seq_basket";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<BasketDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq_basket = rs.getInt("seq_basket");
				String movieCd = rs.getString("movieCd");
				String movieNm = rs.getString("movieNm");
				String movieNmEn = rs.getString("movieNmEn");
				String prdtYear = rs.getString("prdtYear");
				String user_category = rs.getString("user_category");
				
				list.add(new BasketDTO(seq_basket, user_id, movieCd, movieNm, movieNmEn, prdtYear, user_category));
			}
			return list;
		}
	}
	
	// 찜 목록 정렬(이름(한글)순)
	// 이것도 테이블에서 영화명 받아와야...하니까 나중에 말하기 ㅠ
	public ArrayList<BasketDTO> selectByName(String user_id) throws Exception{
		String sql = "select * from tbl_basket where user_id = ? order by movieNm";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<BasketDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq_basket = rs.getInt("seq_basket");
				String movieCd = rs.getString("movieCd");
				String movieNm = rs.getString("movieNm");
				String movieNmEn = rs.getString("movieNmEn");
				String prdtYear = rs.getString("prdtYear");
				String user_category = rs.getString("user_category");
				
				list.add(new BasketDTO(seq_basket, user_id, movieCd, movieNm, movieNmEn, prdtYear, user_category));
			}
			return list;
		}
	}
	// 찜 목록 정렬(이름(영문)순)
	public ArrayList<BasketDTO> selectByNameEn(String user_id) throws Exception{
		String sql = "select * from tbl_basket where user_id = ? order by movieNmEn";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
	
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<BasketDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq_basket = rs.getInt("seq_basket");
				String movieCd = rs.getString("movieCd");
				String movieNm = rs.getString("movieNm");
				String movieNmEn = rs.getString("movieNmEn");
				String prdtYear = rs.getString("prdtYear");
				String user_category = rs.getString("user_category");
				
				list.add(new BasketDTO(seq_basket, user_id, movieCd, movieNm, movieNmEn, prdtYear, user_category));
			}
			return list;
		}
	}
	
	// 찜 목록 정렬(최신순) -> 이거 바꿔야할듯?
//	public ArrayList<BasketDTO> selectByLatest(String user_id) throws Exception{
//		String sql = "select * from tbl_basket where user_id = ? order by prdtYear";
//		try(Connection con = bds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql);){
//			
//			pstmt.setString(1, user_id);
//			ResultSet rs = pstmt.executeQuery();
//			ArrayList<BasketDTO> list = new ArrayList<>();
//			while(rs.next()) {
//				int seq_basket = rs.getInt("seq_basket");
//				String movieCd = rs.getString("movieCd");
//				String user_category = rs.getString("user_category");
//				
//				list.add(new BasketDTO(seq_basket, user_id, movieCd, user_category));
//			}
//			return list;
//		}
//	}
	
	// 찜 개수 카운팅 중복 방지를 위해 아디값 조회로 수정
	public int getListCnt(String user_id) throws Exception{
		String sql = "select count(*) as listCnt from tbl_basket where user_id=?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt("listCnt"); // 전체 찜 개수
			
			return count;
		}
	}
	
//	찜버튼 insert
	public int wish_insert (BasketDTO dto) throws Exception{
		String sql = "insert into tbl_basket values(seq_basket.nextval,?,?,?,?,?,?)";
		
		try(Connection con =getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)){

			pstmt.setString(1, dto.getUser_id());
			pstmt.setString(2, dto.getMovieCd());
			pstmt.setString(3, dto.getMovieNm());
			pstmt.setString(4, dto.getMovieNmEn());
			pstmt.setString(5, dto.getPrdtYear());
			pstmt.setString(6, dto.getUser_category());

			int rs = pstmt.executeUpdate();

			return rs;
		}
	}
	
	public int wishCnt(String movieCd) throws Exception{
		String sql = "select count(*) from tbl_basket where movieCd=?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, movieCd);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int wishCnt = rs.getInt(1); // 전체 찜 개수
			
			return wishCnt;
		}
	}
	// 중복 찜 등록 방지
	public int id_Cd_Select (String movieCd, String user_id) throws Exception{
		String sql = "select*from tbl_basket where movieCd=? and user_id =?";
		
		try(Connection con =getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)){

			pstmt.setString(1, movieCd);
			pstmt.setString(2, user_id);
			

			int rs = pstmt.executeUpdate();

			return rs;
		}
	}
}
