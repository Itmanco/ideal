package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * メニュータイプ情報を管理するJavaBeansクラスです。
 * データベースのmenuTypeテーブルに対応しており、メニュータイプの情報を取得する機能を提供します。
 *
 * This is a JavaBeans class for managing menu type information.
 * It corresponds to the 'menuType' table in the database and provides functions to retrieve menu type information.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
public class MenuType implements Serializable {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * タイプID。
	 * Type ID.
	 */
	private int typeId;
	/**
	 * タイプ名。
	 * Type name.
	 */
	private String typeName;
		
	/**
	 * デフォルトコンストラクタです。
	 * Default constructor.
	 */
	public MenuType() {
		super();
	}

	/**
	 * タイプIDを取得します。
	 * Retrieves the type ID.
	 *
	 * @return タイプID / Type ID
	 */
	public int getTypeId() {
		return typeId;
	}

	/**
	 * タイプIDを設定します。
	 * Sets the type ID.
	 *
	 * @param typeId 設定するタイプID / Type ID to set
	 */
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	/**
	 * タイプ名を取得します。
	 * Retrieves the type name.
	 *
	 * @return タイプ名 / Type name
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * タイプ名を設定します。
	 * Sets the type name.
	 *
	 * @param typeName 設定するタイプ名 / Type name to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	/**
	 * 指定されたタイプIDのメニュータイプ情報をデータベースから取得します。
	 * Retrieves menu type information from the database for the given type ID.
	 *
	 * @param typeId 取得するタイプID / The type ID to retrieve
	 * @return 取得したメニュータイプオブジェクト / The retrieved MenuType object
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static MenuType getMenuType(int typeId) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		MenuType mt = null;
		
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			sql = "SELECT * FROM menuType WHERE t_id = ?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, typeId);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				mt = new MenuType();
				mt.setTypeId(rs.getInt("t_id"));
				mt.setTypeName(rs.getString("t_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IdealException(1);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mt;
	}

	/**
	 * すべてのメニュータイプ情報をデータベースから取得します。
	 * Retrieves all menu type information from the database.
	 *
	 * @return すべてのメニュータイプのリスト / A list of all menu types
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static ArrayList<MenuType> getAllType() throws IdealException {
		ArrayList<MenuType> al = new ArrayList<MenuType>();
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			sql = "SELECT * FROM menuType ORDER BY t_id";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				MenuType mt = new MenuType();
				mt.setTypeId(rs.getInt("t_id"));
				mt.setTypeName(rs.getString("t_name"));
				al.add(mt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IdealException(1);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return al;
	}

	public static ArrayList<MenuType> getAllTypeActive() throws IdealException {
		ArrayList<MenuType> al = new ArrayList<MenuType>();
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			sql = "SELECT * FROM menuType ORDER BY t_id";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				MenuType mt = new MenuType();
				mt.setTypeId(rs.getInt("t_id"));
				mt.setTypeName(rs.getString("t_name"));
				al.add(mt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IdealException(1);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return al;
	}

	
	
	/**
	 * コースタイプ（t_id = 100）を除いたすべてのメニュータイプ情報をデータベースから取得します。
	 * Retrieves all menu type information from the database, excluding the course type (t_id = 100).
	 *
	 * @return コースタイプ以外のメニュータイプのリスト / A list of menu types other than the course type
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static ArrayList<MenuType> getAllNoCourseType() throws IdealException {
		ArrayList<MenuType> al = new ArrayList<MenuType>();
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			sql = "SELECT * FROM menuType where t_id != 100 ORDER BY t_id";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				MenuType mt = new MenuType();
				mt.setTypeId(rs.getInt("t_id"));
				mt.setTypeName(rs.getString("t_name"));
				al.add(mt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IdealException(1);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return al;
	}
}
