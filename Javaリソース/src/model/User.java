package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * ユーザー情報を管理するJavaBeansクラスです。
 * データベースのuserテーブルに対応しており、ユーザーの認証、情報取得、新規登録、更新、削除の機能を提供します。
 *
 * This is a JavaBeans class for managing user information.
 * It corresponds to the 'user' table in the database and provides functionalities for user authentication, data retrieval, creation, updating, and deletion.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
public class User implements Serializable {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ユーザーID。
	 * User ID.
	 */
	private int usrId;
	/**
	 * ユーザー名。
	 * User name.
	 */
	private String usrName;
	/**
	 * パスワード。
	 * Password.
	 */
	private String password;
	/**
	 * 住所。
	 * Address.
	 */
	private String address;
	/**
	 * 電話番号。
	 * Phone number.
	 */
	private String phone;
	/**
	 * メールアドレス。
	 * Mail address.
	 */
	private String mail;
	/**
	 * 備考。
	 * Remarks.
	 */
	private String exp;

	/**
	 * オブジェクトの文字列表現を返します。
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "User [usrId=" + usrId + ", usrName=" + usrName + ", password=" + password + ", address=" + address
				+ ", phone=" + phone + ", mail=" + mail + ", exp=" + exp + "]";
	}

	/**
	 * デフォルトコンストラクタです。
	 * Default constructor.
	 */
	public User() {
		super();
	}
	
	/**
	 * ユーザーIDを取得します。
	 * Retrieves the user ID.
	 *
	 * @return ユーザーID / User ID
	 */
	public int getUsrId() {
		return usrId;
	}

	/**
	 * ユーザーIDを設定します。
	 * Sets the user ID.
	 *
	 * @param userId 設定するユーザーID / User ID to set
	 */
	public void setUsrId(int userId) {
		this.usrId = userId;
	}

	/**
	 * ユーザー名を取得します。
	 * Retrieves the user name.
	 *
	 * @return ユーザー名 / User name
	 */
	public String getUsrName() {
		return usrName;
	}

	/**
	 * ユーザー名を設定します。
	 * Sets the user name.
	 *
	 * @param usrName 設定するユーザー名 / User name to set
	 */
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	/**
	 * パスワードを取得します。
	 * Retrieves the password.
	 *
	 * @return パスワード / Password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定します。
	 * Sets the password.
	 *
	 * @param password 設定するパスワード / Password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 住所を取得します。
	 * Retrieves the address.
	 *
	 * @return 住所 / Address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 住所を設定します。
	 * Sets the address.
	 *
	 * @param address 設定する住所 / Address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 電話番号を取得します。
	 * Retrieves the phone number.
	 *
	 * @return 電話番号 / Phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 電話番号を設定します。
	 * Sets the phone number.
	 *
	 * @param phone 設定する電話番号 / Phone number to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * メールアドレスを取得します。
	 * Retrieves the mail address.
	 *
	 * @return メールアドレス / Mail address
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * メールアドレスを設定します。
	 * Sets the mail address.
	 *
	 * @param mail 設定するメールアドレス / Mail address to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * 備考を取得します。
	 * Retrieves the remarks.
	 *
	 * @return 備考 / Remarks
	 */
	public String getExp() {
		return exp;
	}

	/**
	 * 備考を設定します。
	 * Sets the remarks.
	 *
	 * @param exp 設定する備考 / Remarks to set
	 */
	public void setExp(String exp) {
		this.exp = exp;
	}

	/**
	 * 指定されたユーザーIDとパスワードでログイン認証を行います。
	 * 認証に成功した場合はUserオブジェクトを、失敗した場合はnullを返します。
	 *
	 * Performs login authentication with the specified user ID and password.
	 * Returns a User object on successful authentication, and null on failure.
	 *
	 * @param usrId 認証するユーザーID / User ID to authenticate
	 * @param password 認証するパスワード / Password to authenticate
	 * @return 認証に成功した場合はUserオブジェクト、失敗した場合はnull / User object on success, null on failure
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static User login(int usrId, String password) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		User u = null;
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			System.out.println("Inside: User->login /n usrId:" + usrId + ", password:" + password);
			sql =
				"SELECT"
				+ " usr_id,"	//1
				+ " usr_name,"	//2
				+ " password,"	//3
				+ " address,"	//4
				+ " phone,"		//5
				+ " mail,"		//6
				+ " exp"		//7
				+ " FROM user"
				+ " WHERE usr_id = ? and password = ?";
	
			pst = con.prepareStatement(sql);
			pst.setInt(1, usrId);
			pst.setString(2, password);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				u = new User();
				u.setUsrId(rs.getInt(1));
				u.setUsrName(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setAddress(rs.getString(4));
				u.setPhone(rs.getString(5));
				u.setMail(rs.getString(6));
				u.setExp(rs.getString(7));
			}
			System.out.println("Inside - after: User-> ..");
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
		return u;
	}
	
	/**
	 * 指定されたユーザーIDのユーザー情報をデータベースから取得します。
	 * Retrieves user information from the database for the given user ID.
	 *
	 * @param usrId 取得するユーザーID / The user ID to retrieve
	 * @return 取得したユーザーオブジェクト / The retrieved User object
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static User getUser(int usrId) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		User u = null;
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			System.out.println("Inside: User->getUser ..");
			sql =
				"SELECT"
				+ " usr_id,"	//1
				+ " usr_name,"	//2
				+ " password,"	//3
				+ " address,"	//4
				+ " phone,"		//5
				+ " mail,"		//6
				+ " exp"		//7
				+ " FROM user"
				+ " WHERE usr_id = ?";
	
			pst = con.prepareStatement(sql);
			pst.setInt(1, usrId);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				u = new User();
				u.setUsrId(rs.getInt(1));
				u.setUsrName(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setAddress(rs.getString(4));
				u.setPhone(rs.getString(5));
				u.setMail(rs.getString(6));
				u.setExp(rs.getString(7));
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
		return u;
	}
	
	/**
	 * 新しいユーザー情報をデータベースに登録します。
	 * Registers new user information in the database.
	 *
	 * @param user 登録するユーザーオブジェクト / The User object to register
	 * @return ユーザーIDが設定された登録後のユーザーオブジェクト / The registered User object with its ID set
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static User insert(User user) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		String sql = null;
		
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// SQLステートメントを準備
			// Prepare the SQL statement
			sql = "INSERT INTO user VALUES (DEFAULT,?,?,?,?,?,?)";
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, user.getUsrName());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getAddress());
			pst.setString(4, user.getPhone());
			pst.setString(5, user.getMail());
			pst.setString(6, "");
			
			pst.executeUpdate();
	
			// 自動生成されたキーを取得してユーザーオブジェクトに設定
			// Retrieve the generated key and set it on the user object
			try (ResultSet rs = pst.getGeneratedKeys()) {
				if (rs.next()) {
					int newUserId = rs.getInt(1);
					user.setUsrId(newUserId);
					System.out.println("New User ID: " + newUserId);
				}
			}
		} catch (Exception e) {
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
		return user;
	}
	
	/**
	 * 既存のユーザー情報をデータベースで更新します。
	 * Updates existing user information in the database.
	 *
	 * @param user 更新するユーザーオブジェクト / The User object to update
	 * @return 更新後のユーザーオブジェクト / The updated User object
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	public static User update(User user) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		String sql = null;
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// パスワードが更新されるかどうかでSQLを分岐
			// Branch the SQL based on whether the password is being updated
			if (user.password != null && !user.password.equals("")) {
				sql = "UPDATE user SET usr_name = ?, password = ?, address = ?, phone = ?, mail = ?"
						+ " WHERE usr_id = ?";
				pst = con.prepareStatement(sql);
				pst.setString(1, user.getUsrName());
				pst.setString(2, user.getPassword());
				pst.setString(3, user.getAddress());
				pst.setString(4, user.getPhone());
				pst.setString(5, user.getMail());
				pst.setInt(6, user.getUsrId());
			} else {
				sql = "UPDATE user SET usr_name = ?, address = ?, phone = ?, mail = ?"
						+ " WHERE usr_id = ?";
				pst = con.prepareStatement(sql);
				pst.setString(1, user.getUsrName());
				pst.setString(2, user.getAddress());
				pst.setString(3, user.getPhone());
				pst.setString(4, user.getMail());
				pst.setInt(5, user.getUsrId());
			}
			
			pst.executeUpdate();
		} catch (Exception e) {
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
		return user;
	}
	
	/**
	 * 指定されたユーザーIDのユーザーをデータベースから削除します。
	 * 関連する予約情報も同時に削除されます（トランザクション処理）。
	 *
	 * Deletes the user with the specified user ID from the database.
	 * Associated reservation information is also deleted simultaneously (transaction process).
	 *
	 * @param user 削除するユーザーオブジェクト / The User object to delete
	 * @return 削除後のユーザーオブジェクト / The deleted User object
	 * @throws IdealException データベースアクセスに失敗した場合、またはロールバックが発生した場合 / If database access fails or a rollback occurs
	 */
	public static User delete(User user) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// トランザクションを開始
			// Begin transaction
			con.setAutoCommit(false);
			
			try (
				PreparedStatement deleteReservePst = con.prepareStatement("DELETE FROM reserve WHERE usr_id = ?");
				PreparedStatement deleteUserPst = con.prepareStatement("DELETE FROM user WHERE usr_id = ?")
			) {
				// 1. 予約情報を削除
				// 1. Delete reservation information
				deleteReservePst.setInt(1, user.getUsrId());
				deleteReservePst.executeUpdate();
				
				// 2. ユーザー情報を削除
				// 2. Delete user information
				deleteUserPst.setInt(1, user.getUsrId());
				deleteUserPst.executeUpdate();
			}
			// トランザクションをコミット
			// Commit the transaction
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (con != null) {
				try {
					// エラーが発生した場合、トランザクションをロールバック
					// If an error occurs, roll back the transaction
					con.rollback();
				} catch (SQLException rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
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
		return user;
	}
}