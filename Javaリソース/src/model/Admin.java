package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 管理者情報を管理するJavaBeansクラスです。
 * データベースのadminテーブルに対応しており、管理者の認証処理も担当します。
 *
 * This is a JavaBeans class for managing administrator information.
 * It corresponds to the 'admin' table in the database and is also responsible for administrator authentication.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
public class Admin implements Serializable {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 管理者名。
	 * Administrator name.
	 */
	private String admName;
	/**
	 * パスワード。
	 * Password.
	 */
	private String password;
	/**
	 * 説明。
	 * Description.
	 */
	private String exp;
	
	/**
	 * 管理者名を取得します。
	 * Retrieves the administrator name.
	 *
	 * @return 管理者名 / Administrator name
	 */
	public String getAdmName() {
		return admName;
	}

	/**
	 * 管理者名を設定します。
	 * Sets the administrator name.
	 *
	 * @param admName 設定する管理者名 / Administrator name to set
	 */
	public void setAdmName(String admName) {
		this.admName = admName;
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
	 * 説明を取得します。
	 * Retrieves the description.
	 *
	 * @return 説明 / Description
	 */
	public String getExp() {
		return exp;
	}

	/**
	 * 説明を設定します。
	 * Sets the description.
	 *
	 * @param exp 設定する説明 / Description to set
	 */
	public void setExp(String exp) {
		this.exp = exp;
	}
	
	/**
	 * デフォルトコンストラクタです。
	 * Default constructor.
	 */
	public Admin() {
		super();
	}
	
	/**
	 * オブジェクトの文字列表現を返します。
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "Admin [admName=" + admName + ", password=" + password + ", exp=" + exp + "]";
	}

	/**
	 * 指定された管理者名とパスワードでログイン認証を行います。
	 * 認証に成功した場合はAdminオブジェクトを、失敗した場合はnullを返します。
	 *
	 * Performs login authentication with the specified administrator name and password.
	 * Returns an Admin object on successful authentication, and null on failure.
	 *
	 * @param admName 認証する管理者名 / Administrator name to authenticate
	 * @param password 認証するパスワード / Password to authenticate
	 * @return 認証に成功した場合はAdminオブジェクト、失敗した場合はnull / Admin object on success, null on failure
	 * @throws IdealException データベースアクセスに失敗した場合 / If database access fails
	 */
	static public Admin login(String admName, String password) throws IdealException {
		Admin local = null;
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql1 = null;
		
		try {
			// データソースからデータベース接続を取得
			// Get database connection from data source
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();

			sql1 =
				"SELECT"
				+ " adm_name,"
				+ " password,"
				+ " exp"
				+ " FROM admin"
				+ " WHERE adm_name = ? and password = ?";	

			// SQLステートメントを実行
			// Execute the SQL statement
			pst = con.prepareStatement(sql1);
			pst.setString(1, admName);
			pst.setString(2, password);
			rs = pst.executeQuery();

			if (rs.next()) {
				// 認証成功: 取得したデータでAdminオブジェクトを生成
				// Authentication successful: Create an Admin object with the retrieved data
				local = new Admin();
				local.setAdmName(rs.getString(1));
				local.setPassword(rs.getString(2));
				local.setExp(rs.getString(3));
			}
					
		} catch (Exception e) {
			// データベースエラーが発生した場合
			// If a database error occurs
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		} finally {
			// リソースを解放
			// Release resources
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
		return local;
	}
}