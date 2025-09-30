package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * コースとメニューの関連付け情報を管理するJavaBeansクラスです。
 * データベースのcoursectlテーブルに対応しており、コースとメニューの関連性を確認する機能を提供します。
 *
 * This is a JavaBeans class for managing the association between courses and menus.
 * It corresponds to the 'coursectl' table in the database and provides a function to check the relationship between courses and menus.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
public class Coursectl implements Serializable {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * コースID。
	 * Course ID.
	 */
	private int c_Id;
	/**
	 * メニューID。
	 * Menu ID.
	 */
	private int m_Id;
	
	/**
	 * デフォルトコンストラクタです。
	 * Default constructor.
	 */
	public Coursectl() {
		super();
	}

	/**
	 * コースIDを取得します。
	 * Retrieves the course ID.
	 *
	 * @return コースID / Course ID
	 */
	public int getC_Id() {
		return c_Id;
	}

	/**
	 * コースIDを設定します。
	 * Sets the course ID.
	 *
	 * @param c_Id 設定するコースID / Course ID to set
	 */
	public void setC_Id(int c_Id) {
		this.c_Id = c_Id;
	}

	/**
	 * メニューIDを取得します。
	 * Retrieves the menu ID.
	 *
	 * @return メニューID / Menu ID
	 */
	public int getM_Id() {
		return m_Id;
	}

	/**
	 * メニューIDを設定します。
	 * Sets the menu ID.
	 *
	 * @param m_Id 設定するメニューID / Menu ID to set
	 */
	public void setM_Id(int m_Id) {
		this.m_Id = m_Id;
	}

	/**
	 * オブジェクトの文字列表現を返します。
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "Coursectl [c_Id=" + c_Id + ", m_Id=" + m_Id + "]";
	}

	/**
	 * 指定されたメニューIDが、現在いずれかのコースに関連付けられているかを確認します。
	 * 関連付けられている場合はIdealException(6)をスローします。
	 *
	 * Checks if the specified menu ID is currently associated with any course.
	 * Throws IdealException(6) if it is associated.
	 *
	 * @param m_Id 確認するメニューID / The menu ID to check
	 * @throws IdealException データベースアクセスに失敗した場合、またはメニューが関連付けられている場合 / If database access fails or the menu is associated with a course
	 */
	public static void courseMenuChk(int m_Id) throws IdealException {
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

			sql =
				"SELECT"
				+ " *"
				+ " FROM coursectl"
				+ " WHERE m_id = ?";	


			pst = con.prepareStatement(sql);
			pst.setInt(1, m_Id);
			rs = pst.executeQuery();

			// メニューがコースに関連付けられているか確認
			// Check if the menu is associated with a course
			if (rs.next()) {
				throw new IdealException(6);
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
	}
}