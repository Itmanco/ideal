package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import controller.*;

/**
 * コース情報を管理するJavaBeansクラスです。
 * データベースのcourseテーブルおよび関連テーブルに対応しており、コース情報の取得や更新（新規登録、変更、削除）を行います。
 *
 * This is a JavaBeans class for managing course information.
 * It corresponds to the 'course' table and related tables in the database, and performs operations such as retrieving and updating (creating, modifying, deleting) course information.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
public class Course implements Serializable {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * コースID。
	 * Course ID.
	 */
	private int courseId;
	/**
	 * コース名。
	 * Course name.
	 */
	private String courseName;
	/**
	 * コースの詳細説明。
	 * Detailed description of the course.
	 */
	private String detail;
	/**
	 * 注文可能フラグ（0:不可, 1:可能）。
	 * Orderable flag (0: no, 1: yes).
	 */
	private int orderFlg;
	/**
	 * コースの価格。
	 * Course price.
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
	 * メニュータイプ。
	 * Menu type.
	 */
	private int menuType;
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
	 * メニュータイプを取得します。
	 * Retrieves the menu type.
	 *
	 * @return メニュータイプ / Menu type
	 */
	public int getMenuType() {
		return menuType;
	}

	/**
	 * オブジェクトの文字列表現を返します。
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", detail=" + detail + ", orderFlg="
				+ orderFlg + ", price=" + price + ", typeId=" + typeId + ", typeName=" + typeName + ", menuType="
				+ menuType + ", menuId=" + menuId + ", menuName=" + menuName + "]";
	}

	/**
	 * メニュータイプを設定します。
	 * Sets the menu type.
	 *
	 * @param menuType 設定するメニュータイプ / Menu type to set
	 */
	public void setMenuType(int menuType) {
		this.menuType = menuType;
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
	 * コースに含まれるメニュータイプIDの配列。
	 * An array of menu type IDs included in a course.
	 */
	public static final int[] COURSE_MENU_TYPE_ID = {200, 210, 220, 300, 310, 400};

	/**
	 * デフォルトコンストラクタ。
	 * Default constructor.
	 */
	public Course() {
		super();
	}

	/**
	 * コースIDを取得します。
	 * Retrieves the course ID.
	 *
	 * @return コースID / Course ID
	 */
	public int getCourseId() {
		return courseId;
	}

	/**
	 * コースIDを設定します。
	 * Sets the course ID.
	 *
	 * @param courseID 設定するコースID / Course ID to set
	 */
	public void setCourseId(int courseID) {
		this.courseId = courseID;
	}

	/**
	 * コース名を取得します。
	 * Retrieves the course name.
	 *
	 * @return コース名 / Course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * コース名を設定します。
	 * Sets the course name.
	 *
	 * @param courseName 設定するコース名 / Course name to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * コース詳細を取得します。
	 * Retrieves the course details.
	 *
	 * @return コース詳細 / Course details
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * コース詳細を設定します。
	 * Sets the course details.
	 *
	 * @param detail 設定するコース詳細 / Course details to set
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
	 * @param typeID 設定するタイプID / Type ID to set
	 */
	public void setTypeId(int typeID) {
		this.typeId = typeID;
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
	 * 指定されたIDの単一コース情報をデータベースから取得します。
	 * Retrieves single course information from the database for the given ID.
	 *
	 * @param c_Id 取得するコースID / Course ID to retrieve
	 * @return 取得したコースオブジェクト / The retrieved Course object
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static Course getCourse(int c_Id) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql1 = null;
		Course c = null;
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();

			sql1 =
					"SELECT"
							+ " c_id,"
							+ " c_name,"
							+ " detail,"
							+ " orderFlg,"
							+ " price,"
							+ " t_id,"
							+ " t_name"
							+ " FROM course"
							+ " JOIN"
							+ " menuType USING(t_id)"
							+ " WHERE c_id = ?";

			pst = con.prepareStatement(sql1);
			pst.setInt(1, c_Id);
			rs = pst.executeQuery();

			if (rs.next()) {
				c = new Course();
				c.setCourseId(rs.getInt(1));
				c.setCourseName(rs.getString(2));
				c.setDetail(rs.getString(3));
				c.setOrderFlg(rs.getInt(4));
				c.setPrice(rs.getInt(5));
				c.setTypeId(rs.getInt(6));
				c.setTypeName(rs.getString(7));
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
		return c;
	}

	/**
	 * 指定されたIDのコースに含まれるすべてのメニュー情報をデータベースから取得します。
	 * Retrieves all menu information included in the course for the given ID from the database.
	 *
	 * @param c_Id 取得するコースID / Course ID to retrieve
	 * @return コースに含まれるメニューのリスト / A list of menus included in the course
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static ArrayList<Course> getOneCourse(int c_Id) throws IdealException {
		ArrayList<Course> al = new ArrayList<Course>();
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

			sql = "SELECT "
					+ "c_id, "			//1
					+ "c_name, "		//2
					+ "c.detail, "		//3
					+ "c.orderFlg, "	//4
					+ "c.price, "		//5
					+ "c.t_id, "		//6
					+ "m.t_id,"			//7
					+ "m_id,"			//8
					+ "m_name, "		//9
					+ "mt.t_name "		//10
					+ "FROM coursectl JOIN course c USING(c_id) "
					+ "JOIN menu m USING(m_id) "
					+ "JOIN menutype mt ON m.t_id = mt.t_id "
					+ "WHERE c_id = ? "
					+ "ORDER BY m_id;";


			pst = con.prepareStatement(sql);
			pst.setInt(1, c_Id);
			rs = pst.executeQuery();

			while (rs.next()) {
				Course c = new Course();
				c.setCourseId(rs.getInt(1));
				c.setCourseName(rs.getString(2));
				c.setDetail(rs.getString(3));
				c.setOrderFlg(rs.getInt(4));
				c.setPrice(rs.getInt(5));
				c.setTypeId(rs.getInt(6));
				c.setMenuType(rs.getInt(7));
				c.setMenuId(rs.getInt(8));
				c.setMenuName(rs.getString(9));
				c.setTypeName(rs.getString(10));
				
				al.add(c);
			}
		} catch (Exception e) {
			System.out.println(pst);
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
	 * すべてのコース情報（コースとその構成メニュー）をデータベースから取得します。
	 * Retrieves all course information (courses and their constituent menus) from the database.
	 *
	 * @return すべてのコースとメニュー情報のリスト / A list of all course and menu information
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static ArrayList<Course> getCourseList() throws IdealException {
		ArrayList<Course> al = new ArrayList<Course>();
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

			sql = "SELECT "
					+ "c_id, "			//1
					+ "c_name, "		//2
					+ "c.detail, "		//3
					+ "c.orderFlg, "	//4
					+ "c.price, "		//5
					+ "c.t_id, "		//6
					+ "m.t_id,"			//7
					+ "m_id,"			//8
					+ "m_name "			//9
					+ "FROM coursectl JOIN course c USING(c_id) "
					+ "JOIN menu m USING(m_id) "
					+ "ORDER BY c_id;";

			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				Course c = new Course();
				c.setCourseId(rs.getInt(1));
				c.setCourseName(rs.getString(2));
				c.setDetail(rs.getString(3));
				c.setOrderFlg(rs.getInt(4));
				c.setPrice(rs.getInt(5));
				c.setTypeId(rs.getInt(6));
				c.setMenuType(rs.getInt(7));
				c.setMenuId(rs.getInt(8));
				c.setMenuName(rs.getString(9));
				
				al.add(c);
			}
		} catch (Exception e) {
			System.out.println(pst);
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
	
	public static ArrayList<Course> getOneCourseList() throws IdealException {
		ArrayList<Course> al = new ArrayList<Course>();
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

			sql = "SELECT "
					+ "c_id, "			//1
					+ "c_name, "		//2
					+ "detail, "		//3
					+ "orderFlg, "		//4
					+ "price, "			//5
					+ "t_id "			//6
					+ "FROM course "
					+ "WHERE orderFlg = 1 "
					+ "ORDER BY c_id;";

			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				Course c = new Course();
				c.setCourseId(rs.getInt(1));
				c.setCourseName(rs.getString(2));
				c.setDetail(rs.getString(3));
				c.setOrderFlg(rs.getInt(4));
				c.setPrice(rs.getInt(5));
				c.setTypeId(rs.getInt(6));
				
				al.add(c);
			}
		} catch (Exception e) {
			System.out.println(pst);
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
	 * すべてのコース情報（メニュータイプを含む）をデータベースから取得します。
	 * Retrieves all course information (including menu types) from the database.
	 *
	 * @param t_Id 使用されない引数 / An unused argument
	 * @return すべてのコース情報のリスト / A list of all course information
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static ArrayList<Course> getTypeCourseList(int t_Id) throws IdealException {
		//TODO:このメソッドが何をするのかよく分かりません。すべてのコースが同じメニュータイプを持つことが前提となっています。
		ArrayList<Course> al = new ArrayList<Course>();
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql1 = null;

		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();

			sql1 =
					"SELECT"
							+ " c_id,"
							+ " c_name,"
							+ " detail,"
							+ " orderFlg,"
							+ " price,"
							+ " t_id,"
							+ " t_name"
							+ " FROM course"
							+ " JOIN"
							+ " menuType USING(t_id)"
							+ " ORDER BY c_id";

			pst = con.prepareStatement(sql1);
			rs = pst.executeQuery();

			while (rs.next()) {
				Course c = new Course();
				c.setCourseId(rs.getInt(1));
				c.setCourseName(rs.getString(2));
				c.setDetail(rs.getString(3));
				c.setOrderFlg(rs.getInt(4));
				c.setPrice(rs.getInt(5));
				c.setTypeId(rs.getInt(6));
				c.setTypeName(rs.getString(7));
				
				al.add(c);
			}
		} catch (Exception e) {
			System.out.println(pst);
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
	 * コース情報をデータベースで更新（新規登録、更新、削除）します。
	 * このメソッドは、複数のテーブルにわたるトランザクションを扱います。
	 *
	 * Updates (inserts, updates, or deletes) course information in the database.
	 * This method handles transactions across multiple tables.
	 *
	 * @param c 処理対象のコースオブジェクト / The Course object to be processed
	 * @param mode 実行する操作モード（INSERT, UPDATE, DELETE） / The operation mode to execute (INSERT, UPDATE, DELETE)
	 * @param coursectl コースに紐づくメニューのリスト / A list of menus associated with the course
	 * @return 更新されたレコード数 / The number of updated records
	 * @throws IdealException データベースアクセスに失敗した場合、またはロールバックが発生した場合 / If database access fails or a rollback occurs
	 */
	public static int updateCourse(Course c, int mode, ArrayList<Coursectl> coursectl) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		int num = 0;

		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();

			// トランザクションを開始
			// Begin transaction
			con.setAutoCommit(false);
			
			switch (mode) {
				case CourseOperationSvl.INSERT:
					// コース新規登録処理
					// Process for new course insertion
					try (
						PreparedStatement insertC = con.prepareStatement("INSERT INTO course VALUES (DEFAULT,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
						PreparedStatement insertCt = con.prepareStatement("INSERT INTO coursectl VALUES (?,?)")
					) {
						// 1. courseテーブルに挿入
						// 1. Insert into the course table
						insertC.setString(1, c.getCourseName());
						insertC.setString(2, c.getDetail());
						insertC.setInt(3, c.getOrderFlg());
						insertC.setInt(4, c.getPrice());
						insertC.setInt(5, c.getTypeId());
						num = insertC.executeUpdate();
						
						// 自動生成されたc_idを取得
						// Retrieve the auto-generated c_id
						int newCourseId = -1;
						try (ResultSet rs = insertC.getGeneratedKeys()) {
							if (rs.next()) {
								newCourseId = rs.getInt(1);
							}
						}
						
						// 2. coursectlテーブルに挿入（バッチ処理）
						// 2. Insert into the coursectl table (batch process)
						for (Coursectl ct : coursectl) {
							insertCt.setInt(1, newCourseId);
							insertCt.setInt(2, ct.getM_Id());
							insertCt.addBatch();
						}
						insertCt.executeBatch();
					}
					// トランザクションをコミット
					// Commit the transaction
					con.commit();
					break;
				
				case CourseOperationSvl.UPDATE:
					// コース情報更新処理
					// Process for course information update
					try (
						PreparedStatement updatePst = con.prepareStatement("UPDATE course SET c_name = ?, detail = ?, orderFlg = ?, price = ?, t_id = ? WHERE c_id = ?");
						PreparedStatement deletePst = con.prepareStatement("DELETE FROM coursectl WHERE c_id = ?");
						PreparedStatement insertPst = con.prepareStatement("INSERT INTO coursectl VALUES (?,?)")
					) {
						// 1. courseテーブルを更新
						// 1. Update the course table
						updatePst.setString(1, c.getCourseName());
						updatePst.setString(2, c.getDetail());
						updatePst.setInt(3, c.getOrderFlg());
						updatePst.setInt(4, c.getPrice());
						updatePst.setInt(5, c.getTypeId());
						updatePst.setInt(6, c.getCourseId());
						num = updatePst.executeUpdate();
						
						// 2. coursectlテーブルから既存データを削除
						// 2. Delete existing data from the coursectl table
						deletePst.setInt(1, c.getCourseId());
						deletePst.executeUpdate();
						
						// 3. coursectlテーブルに新しいデータを挿入（バッチ処理）
						// 3. Insert new data into the coursectl table (batch process)
						for (Coursectl ct : coursectl) {
							insertPst.setInt(1, ct.getC_Id());
							insertPst.setInt(2, ct.getM_Id());
							insertPst.addBatch();
						}
						insertPst.executeBatch();
					}
					// トランザクションをコミット
					// Commit the transaction
					con.commit();
					break;
					
				case CourseOperationSvl.DELETE:
					// コース削除処理
					// Process for course deletion
					try (
							PreparedStatement deleteCtlPst = con.prepareStatement("DELETE FROM coursectl WHERE c_id = ?");
							// 1. コース名（c_name）をreserveテーブルにコピーする
							// 1. Copy the course name (c_name) to the reserve table
							PreparedStatement copyNamePst = con.prepareStatement(
							    "UPDATE reserve r JOIN course c ON r.c_id = c.c_id SET r.c_name = c.c_name WHERE r.c_id = ?"
							);
							// 2. c_idをNULLに更新する
							// 2. Update c_id to NULL
							PreparedStatement nullifyCidPst = con.prepareStatement(
							    "UPDATE reserve SET c_id = NULL WHERE c_id = ?"
							);
							// 3. courseテーブルからデータを削除する
							// 3. Delete data from the course table
							PreparedStatement deleteCoursePst = con.prepareStatement(
							    "DELETE FROM course WHERE c_id = ?"
							)

					) {
						// A. coursectlテーブルからデータを削除
						// A. Delete data from the coursectl table
						deleteCtlPst.setInt(1, c.getCourseId());
						deleteCtlPst.executeUpdate();
						
						// B. reserveテーブルのc_nameフィールドを更新 (履歴保存)
						// B. Update the c_name field in the reserve table (for history)
						copyNamePst.setInt(1, c.getCourseId());
						copyNamePst.executeUpdate();
						
						// C. reserveテーブルのc_idをNULLに更新 (外部キーの解除)
						// C. Update c_id to NULL in the reserve table (remove foreign key link)
						nullifyCidPst.setInt(1, c.getCourseId());
						nullifyCidPst.executeUpdate();
						
						// D. courseテーブルからデータを削除
						// D. Delete data from the course table
						deleteCoursePst.setInt(1, c.getCourseId());
						num = deleteCoursePst.executeUpdate();
					}
					// トランザクションをコミット
					// Commit the transaction
					con.commit();
					break;
					
				default:
					break;
			}
		} catch (Exception e) {
			if (con != null) {
				try {
					// エラーが発生した場合、トランザクションをロールバック
					// If an error occurs, roll back the transaction
					con.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new IdealException(5);
		} finally {
			try {
				if (con != null) {
					// 接続の自動コミットモードを元に戻し、接続を閉じる
					// Restore the connection's auto-commit mode and close the connection
					con.setAutoCommit(true);
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return num;
	}
}