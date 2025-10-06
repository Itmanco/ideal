package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import controller.MenuOperationSvl;

/**
 * メニュー情報を管理するJavaBeansクラスです。
 * データベースのmenuテーブルおよび関連テーブルに対応しており、メニュー情報の取得や更新（新規登録、変更、削除）を行います。
 *
 * This is a JavaBeans class for managing menu information.
 * It corresponds to the 'menu' table and related tables in the database, and performs operations such as retrieving and updating (creating, modifying, deleting) menu information.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
public class Menu implements Serializable {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * メニューID。
	 * Menu ID.
	 */
	private int menuId;
	/**
	 * メニュー名。
	 * Menu name.
	 */
	private String menuName;
	/**
	 * メニューの詳細説明。
	 * Detailed description of the menu.
	 */
	private String detail;
	/**
	 * 注文可能フラグ（0:不可, 1:可能）。
	 * Orderable flag (0: no, 1: yes).
	 */
	private int orderFlg;
	/**
	 * メニューの価格。
	 * Menu price.
	 */
	private int price;
	/**
	 * メニュータイプID。
	 * Menu type ID.
	 */
	private int typeId;
	/**
	 * メニュータイプ名。
	 * Menu type name.
	 */
	private String typeName;

	/**
	 * オブジェクトの文字列表現を返します。
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", menuName=" + menuName + ", detail=" + detail + ", price=" + price
				+ ", orderFlg=" + orderFlg + ", typeId=" + typeId + ", typeName=" + typeName + "]";
	}

	/**
	 * デフォルトコンストラクタです。
	 * Default constructor.
	 */
	public Menu() {
		super();
	}

	/**
	 * メニューIDを取得します。
	 * Retrieves the menu ID.
	 *
	 * @return メニューID / Menu ID
	 */
	public int getMenuId() {
		return menuId;
	}

	/**
	 * メニューIDを設定します。
	 * Sets the menu ID.
	 *
	 * @param menuId 設定するメニューID / Menu ID to set
	 */
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	/**
	 * メニュー名を取得します。
	 * Retrieves the menu name.
	 *
	 * @return メニュー名 / Menu name
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * メニュー名を設定します。
	 * Sets the menu name.
	 *
	 * @param menuName 設定するメニュー名 / Menu name to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * メニュー詳細を取得します。
	 * Retrieves the menu details.
	 *
	 * @return メニュー詳細 / Menu details
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * メニュー詳細を設定します。
	 * Sets the menu details.
	 *
	 * @param detail 設定するメニュー詳細 / Menu details to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * 価格を取得します。
	 * Retrieves the price.
	 *
	 * @return 価格 / Price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * 価格を設定します。
	 * Sets the price.
	 *
	 * @param price 設定する価格 / Price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * 注文可能フラグを取得します。
	 * Retrieves the orderable flag.
	 *
	 * @return 注文可能フラグ / Orderable flag
	 */
	public int getOrderFlg() {
		return orderFlg;
	}

	/**
	 * 注文可能フラグを設定します。
	 * Sets the orderable flag.
	 *
	 * @param orderFlg 設定する注文可能フラグ / Orderable flag to set
	 */
	public void setOrderFlg(int orderFlg) {
		this.orderFlg = orderFlg;
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
	 * 指定されたIDとタイプIDを持つ単一のメニューまたはコース情報をデータベースから取得します。
	 * Retrieves single menu or course information from the database with the specified ID and type ID.
	 *
	 * @param menuId 取得するメニューIDまたはコースID / The menu ID or course ID to retrieve
	 * @param typeId 取得するメニュータイプID / The menu type ID to retrieve
	 * @return 取得したメニューオブジェクト / The retrieved Menu object
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static Menu getOneMenu(int menuId, int typeId) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		Menu m = null;
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// タイプIDが100の場合はコース情報を取得、それ以外はメニュー情報を取得
			// If type ID is 100, get course information; otherwise, get menu information
			if (typeId == 100) {
				sql =
					"SELECT"
					+ " c_id,"
					+ " c_name,"
					+ " detail,"
					+ " orderFlg,"
					+ " price,"
					+ " menuType.t_id,"
					+ " menuType.t_name"
					+ " FROM course"
					+ " JOIN"
					+ " menuType USING(t_id)"
					+ " WHERE c_id = ?";
			} else {
				sql =
					"SELECT"
					+ " m_id,"
					+ " m_name,"
					+ " detail,"
					+ " orderFlg,"
					+ " price,"
					+ " menuType.t_id,"
					+ " menuType.t_name"
					+ " FROM menu"
					+ " JOIN"
					+ " menuType USING(t_id)"
					+ " WHERE m_id = ?";
			}
	
			pst = con.prepareStatement(sql);
			pst.setInt(1, menuId);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				m = new Menu();
				m.setMenuId(rs.getInt(1));
				m.setMenuName(rs.getString(2));
				m.setDetail(rs.getString(3));
				m.setOrderFlg(rs.getInt(4));
				m.setPrice(rs.getInt(5));
				m.setTypeId(rs.getInt(6));
				m.setTypeName(rs.getString(7));
			}
		} catch (Exception e) {
			System.out.println(pst);
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
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
		return m;
	}
	
	/**
	 * すべてのメニュー情報をデータベースから取得します。
	 * Retrieves all menu information from the database.
	 *
	 * @return すべてのメニュー情報のリスト / A list of all menu information
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static ArrayList<Menu> getMenuList() throws IdealException {
		ArrayList<Menu> al = new ArrayList<Menu>();
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
				+ " m_id,"
				+ " m_name,"
				+ " detail,"
				+ " orderFlg,"
				+ " price,"
				+ " t_id,"
				+ " t_name"
				+ " FROM menu"
				+ " JOIN menuType USING(t_id)"
				+ " ORDER BY t_id";
	
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Menu m = new Menu();
				m.setMenuId(rs.getInt(1));
				m.setMenuName(rs.getString(2));
				m.setDetail(rs.getString(3));
				m.setOrderFlg(rs.getInt(4));
				m.setPrice(rs.getInt(5));
				m.setTypeId(rs.getInt(6));
				m.setTypeName(rs.getString(7));
				al.add(m);
			}
		} catch (Exception e) {
			System.out.println(pst);
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
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
	 * 指定されたタイプIDに一致するメニューまたはコースのリストをデータベースから取得します。
	 * Retrieves a list of menus or courses from the database that match the specified type ID.
	 *
	 * @param typeId 取得するメニュータイプID / The menu type ID to retrieve
	 * @return メニューまたはコースのリスト / A list of menus or courses
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static ArrayList<Menu> getMenu(int typeId) throws IdealException {
		ArrayList<Menu> al = new ArrayList<Menu>();
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
			
			// タイプIDが100の場合はコース情報を取得、それ以外はメニュー情報を取得
			// If type ID is 100, get course information; otherwise, get menu information
			if (typeId == 100) {
				sql =
					"SELECT"
					+ " c_id,"
					+ " c_name,"
					+ " detail,"
					+ " orderFlg,"
					+ " price,"
					+ " menuType.t_id,"
					+ " menuType.t_name"
					+ " FROM course"
					+ " JOIN"
					+ " menuType USING(t_id)"
					+ " WHERE t_id = ?"
					+ " ORDER BY t_id";
			} else {
				sql =
					"SELECT"
					+ " m_id,"
					+ " m_name,"
					+ " detail,"
					+ " orderFlg,"
					+ " price,"
					+ " t_id,"
					+ " t_name"
					+ " FROM menu"
					+ " JOIN menuType USING(t_id)"
					+ " WHERE t_id = ?"
					+ " ORDER BY t_id";
			}
	
			pst = con.prepareStatement(sql);
			pst.setInt(1, typeId);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Menu m = new Menu();
				m.setMenuId(rs.getInt(1));
				m.setMenuName(rs.getString(2));
				m.setDetail(rs.getString(3));
				m.setOrderFlg(rs.getInt(4));
				m.setPrice(rs.getInt(5));
				m.setTypeId(rs.getInt(6));
				m.setTypeName(rs.getString(7));
				al.add(m);
			}
		} catch (Exception e) {
			System.out.println(pst);
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
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
	
	public static ArrayList<Menu> getMenuActive(int typeId) throws IdealException {
		ArrayList<Menu> al = new ArrayList<Menu>();
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
			
			// タイプIDが100の場合はコース情報を取得、それ以外はメニュー情報を取得
			// If type ID is 100, get course information; otherwise, get menu information
			if (typeId == 100) {
				sql =
					"SELECT"
					+ " c_id,"
					+ " c_name,"
					+ " detail,"
					+ " orderFlg,"
					+ " price,"
					+ " menuType.t_id,"
					+ " menuType.t_name"
					+ " FROM course"
					+ " JOIN"
					+ " menuType USING(t_id)"
					+ " WHERE t_id = ? AND orderFlg = 1"
					+ " ORDER BY t_id";
			} else {
				sql =
					"SELECT"
					+ " m_id,"
					+ " m_name,"
					+ " detail,"
					+ " orderFlg,"
					+ " price,"
					+ " t_id,"
					+ " t_name"
					+ " FROM menu"
					+ " JOIN menuType USING(t_id)"
					+ " WHERE t_id = ? AND orderFlg = 1"
					+ " ORDER BY t_id";
			}
	
			pst = con.prepareStatement(sql);
			pst.setInt(1, typeId);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Menu m = new Menu();
				m.setMenuId(rs.getInt(1));
				m.setMenuName(rs.getString(2));
				m.setDetail(rs.getString(3));
				m.setOrderFlg(rs.getInt(4));
				m.setPrice(rs.getInt(5));
				m.setTypeId(rs.getInt(6));
				m.setTypeName(rs.getString(7));
				al.add(m);
			}
		} catch (Exception e) {
			System.out.println(pst);
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
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
	 * メニュー情報をデータベースで更新（新規登録、更新、削除）します。
	 *
	 * Updates (inserts, updates, or deletes) menu information in the database.
	 *
	 * @param m 処理対象のメニューオブジェクト / The Menu object to be processed
	 * @param mode 実行する操作モード（INSERT, UPDATE, DELETE） / The operation mode to execute (INSERT, UPDATE, DELETE)
	 * @return 更新されたレコード数 / The number of updated records
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static int updateMenu(Menu m, int mode) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		String sql = null;
		int num = 0;
		
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			switch (mode) {
				case MenuOperationSvl.INSERT:
					// 新規メニュー登録用のSQL
					// SQL for new menu insertion
					sql = "INSERT INTO menu VALUES (DEFAULT,?,?,?,?,?)";
					pst = con.prepareStatement(sql);
					pst.setString(1, m.getMenuName());
					pst.setString(2, m.getDetail());
					pst.setInt(3, m.getOrderFlg());
					pst.setInt(4, m.getPrice());
					pst.setInt(5, m.getTypeId());
					num = pst.executeUpdate();
					break;
				case MenuOperationSvl.UPDATE:
					try (							
						PreparedStatement selectPst = con.prepareStatement("select * FROM coursectl WHERE m_id = ?");
						// メニュー情報更新用のSQL
						// SQL for menu information update
						PreparedStatement updatePst = con.prepareStatement(
								"UPDATE menu "
								+ "SET m_name = ?, "
								+ "detail = ?, "
								+ "orderFlg = ?, "
								+ "price = ?, "
								+ "t_id = ? "
								+ "WHERE m_id = ?");
						) {					
						
						selectPst.setInt(1, m.getMenuId());
						ResultSet rs = selectPst.executeQuery();
						if(rs.next()) {
							// 予約が存在する場合は例外をスロー
							// Throw exception if a reservation exists
							System.out.println("reserveCourseChk:ERR_NO_NOT_MENU_DELETE");
							throw new IdealException(IdealException.ERR_NO_NOT_MENU_DELETE);
						}
						
						updatePst.setString(1, m.getMenuName());
						updatePst.setString(2, m.getDetail());
						updatePst.setInt(3, m.getOrderFlg());
						updatePst.setInt(4, m.getPrice());
						updatePst.setInt(5, m.getTypeId());
						updatePst.setInt(6, m.getMenuId());
						num = updatePst.executeUpdate();
					}
					break;
				case MenuOperationSvl.DELETE:
					// メニュー削除用のSQL
					// SQL for menu deletion
					sql = "DELETE FROM menu WHERE m_id = ?";
					pst = con.prepareStatement(sql);
					pst.setInt(1, m.getMenuId());
					num = pst.executeUpdate();
					break;
				default:
					break;
			}
			
			
		} catch (IdealException ei) {	        
			throw new IdealException(ei.getErrCd());	        
	    }catch (SQLIntegrityConstraintViolationException e) {
	        //e.printStackTrace();
	        throw new IdealException(IdealException.ERR_NO_NOT_MENU_DELETE);	        
	    }
		catch (Exception e) {
			System.out.println(pst);
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		} finally {
			try {
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
		return num;
	}
}
