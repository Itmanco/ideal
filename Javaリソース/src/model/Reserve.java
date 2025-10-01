package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 予約情報を扱うモデルクラス。
 * データベースのreserveテーブルに対応し、予約データ、CRUD操作、および空席チェック機能を提供します。
 * Model class for handling reservation information.
 * It corresponds to the database 'reserve' table and provides reservation data, CRUD operations, and vacancy check functionality.
 *
 * @author モッタハイメ
 * @version 1.0.0
 * @see java.io.Serializable
 */
public class Reserve implements Serializable {

	private static final long serialVersionUID = 1L;
	private int rsvId;
	private int usrId;
	private String usrName;
	private int rsvYy;
	private int rsvMm;
	private int rsvDd;
	private int rsvHh;
	private int rsvMi;
	private int person;
	private int tableId;
	private String tableName;
	private int courseId;
	private String courseName;
	private String appDate;
	private int appYy;
	private int appMm;
	private int appDd;
	private int appHh;
	private int appMi;
	
	
	/**
	 * オブジェクトの文字列表現を返します。
	 * Returns a string representation of the object.
	 */
	@Override
	public String toString() {
		return "Reserve [rsvId=" + rsvId + ", usrId=" + usrId + ", usrName=" + usrName + ", rsvYy=" + rsvYy + ", rsvMm="
				+ rsvMm + ", rsvDd=" + rsvDd + ", rsvHh=" + rsvHh + ", rsvMi=" + rsvMi + ", person=" + person
				+ ", tableId=" + tableId + ", tableName=" + tableName + ", courseId=" + courseId + ", courseName="
				+ courseName + ", appDate=" + appDate + ", appYy=" + appYy + ", appMm=" + appMm + ", appDd=" + appDd
				+ ", appHh=" + appHh + ", appMi=" + appMi + "]";
	}

	/**
	 * コンストラクタ。
	 * Constructor.
	 */
	public Reserve() {
		super();
	}


	//#region Getters and Setters
	
	public int getRsvId() { return rsvId; }
	public void setRsvId(int rsvId) { this.rsvId = rsvId; }

	public int getUsrId() { return usrId; }
	public void setUsrId(int usrId) { this.usrId = usrId; }

	public String getUsrName() { return usrName; }
	public void setUsrName(String usrName) { this.usrName = usrName; }

	public int getRsvYy() { return rsvYy; }
	public void setRsvYy(int rsvYy) { this.rsvYy = rsvYy; }

	public int getRsvMm() { return rsvMm; }
	public void setRsvMm(int rsvMm) { this.rsvMm = rsvMm; }

	public int getRsvDd() { return rsvDd; }
	public void setRsvDd(int rsvDd) { this.rsvDd = rsvDd; }

	public int getRsvHh() { return rsvHh; }
	public void setRsvHh(int rsvHh) { this.rsvHh = rsvHh; }

	public int getRsvMi() { return rsvMi; }
	public void setRsvMi(int rsvMi) { this.rsvMi = rsvMi; }

	public int getPerson() { return person; }
	public void setPerson(int person) { this.person = person; }

	public int getTableId() { return tableId; }
	public void setTableId(int tableId) { this.tableId = tableId; }

	public String getTableName() { return tableName; }
	public void setTableName(String tableName) { this.tableName = tableName; }

	public int getCourseId() { return courseId; }
	public void setCourseId(int courseId) { this.courseId = courseId; }

	public String getCourseName() { return courseName; }
	public void setCourseName(String courseName) { this.courseName = courseName; }

	public String getAppDate() { return appDate; }
	public void setAppDate(String appDate) { this.appDate = appDate; }

	public int getAppYy() { return appYy; }
	public void setAppYy(int appYy) { this.appYy = appYy; }

	public int getAppMm() { return appMm; }
	public void setAppMm(int appMm) { this.appMm = appMm; }

	public int getAppDd() { return appDd; }
	public void setAppDd(int appDd) { this.appDd = appDd; }

	public int getAppHh() { return appHh; }
	public void setAppHh(int appHh) { this.appHh = appHh; }

	public int getAppMi() { return appMi; }
	public void setAppMi(int appMi) { this.appMi = appMi; }
	//#endregion

	/**
	 * 指定されたユーザーIDの予約一覧を取得します。
	 * Retrieves the list of reservations for the specified user ID.
	 * * @param usrId ユーザーID / User ID
	 * @return 予約オブジェクトのArrayList / ArrayList of Reserve objects
	 * @throws IdealException DB接続エラー時 / When a DB connection error occurs
	 */
	public static ArrayList<Reserve> getReserveList(int usrId) throws IdealException{		
		ArrayList<Reserve> rsvs = new ArrayList<Reserve>();
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// DB接続の確立
			// Establish DB connection
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// SQL: ユーザーの全予約情報を結合して取得
			// SQL: Retrieve all reservation information for the user by joining tables
			sql =
				"SELECT"
				+ " rsv_id," 		// 1
				+ " usr_id,"		// 2
				+ " usr_name,"		// 3
				+ " rsv_date,"		// 4
				+ " person,"		// 5
				+ " table_id,"		// 6
				+ " table_name,"	// 7
				+ " c_id,"			// 8
				+ " c.c_name,"		// 9
				+ " app_date"		// 10
				+ " FROM reserve JOIN user USING(usr_id)"
				+ " JOIN table_loc USING(table_id)"
				+ " JOIN course c USING(c_id)"
				+ " WHERE usr_id = ?"
				+ " ORDER BY rsv_id";		
			

			pst = con.prepareStatement(sql);
			pst.setInt(1, usrId);
			rs = pst.executeQuery();
			
			Calendar rsvCal = Calendar.getInstance();
			
			// 結果セットの処理
			// Process the result set
			while(rs.next()) {
				Reserve rsv = new Reserve();
				rsv.setRsvId(rs.getInt(1));
				rsv.setUsrId(rs.getInt(2));
				rsv.setUsrName(rs.getString(3));
				
				// 予約日時をCalendarにセットし、年、月、日、時、分に分割
				// Set reservation datetime to Calendar and split into Year, Month, Day, Hour, Minute
				Timestamp rsvDateTime = rs.getTimestamp(4);			
				rsvCal.setTimeInMillis(rsvDateTime.getTime());
				
				rsv.setRsvYy(rsvCal.get(Calendar.YEAR));
				rsv.setRsvMm(rsvCal.get(Calendar.MONTH) + 1);
				rsv.setRsvDd(rsvCal.get(Calendar.DAY_OF_MONTH));
				rsv.setRsvHh(rsvCal.get(Calendar.HOUR_OF_DAY));
				rsv.setRsvMi(rsvCal.get(Calendar.MINUTE));
				
				rsv.setPerson(rs.getInt(5));
				rsv.setTableId(rs.getInt(6));
				rsv.setTableName(rs.getString(7));
				rsv.setCourseId(rs.getInt(8));
				rsv.setCourseName(rs.getString(9));
				
				// 登録日時をCalendarにセットし、年、月、日、時、分に分割
				// Set application datetime to Calendar and split into Year, Month, Day, Hour, Minute
				rsvDateTime = rs.getTimestamp(10);
				rsvCal.setTimeInMillis(rsvDateTime.getTime());
				rsv.setAppYy(rsvCal.get(Calendar.YEAR));
				rsv.setAppMm(rsvCal.get(Calendar.MONTH) + 1);
				rsv.setAppDd(rsvCal.get(Calendar.DAY_OF_MONTH));
				rsv.setAppHh(rsvCal.get(Calendar.HOUR_OF_DAY));
				rsv.setAppMi(rsvCal.get(Calendar.MINUTE));

				rsvs.add(rsv);
			}
		}catch (Exception e) {
			System.out.println(pst);
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		}finally {
			// リソースの解放
			// Release resources
			try {
				if(rs != null) {
					rs.close();
				}
				if(pst != null) {
					pst.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
		return rsvs;
	}
	
	/**
	 * 指定された予約IDの単一の予約情報を取得します。
	 * Retrieves a single reservation record for the specified reservation ID.
	 * * @param rsvId 予約ID / Reservation ID
	 * @return 予約オブジェクト / Reserve object
	 * @throws IdealException DB接続エラー時 / When a DB connection error occurs
	 */
	public static Reserve getReserve(int rsvId) throws IdealException{		
		Reserve rsv = null;
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			// DB接続の確立
			// Establish DB connection
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// SQL: 指定された予約IDの情報を取得
			// SQL: Retrieve information for the specified reservation ID
//			sql =
//				"SELECT"
//				+ " rsv_id," 		// 1
//				+ " usr_id,"		// 2
//				+ " usr_name,"		// 3
//				+ " rsv_date,"		// 4
//				+ " person,"		// 5
//				+ " table_id,"		// 6
//				+ " table_name,"	// 7
//				+ " c_id,"			// 8
//				+ " c_name,"		// 9
//				+ " app_date"		// 10
//				+ " FROM reserve JOIN user USING(usr_id)"
//				+ " JOIN table_loc USING(table_id)"
//				+ " JOIN course USING(c_id)"
//				+ " WHERE rsv_id = ?";		
			sql =     "SELECT"
				    + " r.rsv_id," 		// 1
				    + " r.usr_id,"		// 2
				    + " usr_name,"		// 3 (from user table)
				    + " r.rsv_date,"	// 4
				    + " r.person,"		// 5
				    + " table_loc.table_id,"// 6
				    + " table_loc.table_name,"// 7 (from table_loc)
				    + " r.c_id,"		// 8
				    + " COALESCE(c.c_name, r.c_name) AS displayed_course_name," // 9 <-- FIX: Conditional name
				    + " r.app_date"		// 10
				    + " FROM reserve r JOIN user USING(usr_id)"
				    + " JOIN table_loc USING(table_id)"
				    + " LEFT JOIN course c ON r.c_id = c.c_id" // <-- FIX: LEFT JOIN is mandatory
				    + " WHERE r.rsv_id = ?";

			pst = con.prepareStatement(sql);
			pst.setInt(1, rsvId);
			rs = pst.executeQuery();
			
			Calendar rsvCal = Calendar.getInstance();
			
			// 結果セットの処理
			// Process the result set
			if(rs.next()) {
				rsv = new Reserve();
				rsv.setRsvId(rs.getInt(1));
				rsv.setUsrId(rs.getInt(2));
				rsv.setUsrName(rs.getString(3));
				
				// 予約日時をCalendarにセットし、年、月、日、時、分に分割
				// Set reservation datetime to Calendar and split into Year, Month, Day, Hour, Minute
				Timestamp rsvDateTime = rs.getTimestamp(4);			
				rsvCal.setTimeInMillis(rsvDateTime.getTime());
				
				rsv.setRsvYy(rsvCal.get(Calendar.YEAR));
				rsv.setRsvMm(rsvCal.get(Calendar.MONTH) + 1);
				rsv.setRsvDd(rsvCal.get(Calendar.DAY_OF_MONTH));
				rsv.setRsvHh(rsvCal.get(Calendar.HOUR_OF_DAY));
				rsv.setRsvMi(rsvCal.get(Calendar.MINUTE));
				
				rsv.setPerson(rs.getInt(5));
				rsv.setTableId(rs.getInt(6));
				rsv.setTableName(rs.getString(7));
				rsv.setCourseId(rs.getInt(8));
				rsv.setCourseName(rs.getString(9));
				
				// 登録日時をCalendarにセットし、年、月、日、時、分に分割
				// Set application datetime to Calendar and split into Year, Month, Day, Hour, Minute
				rsvDateTime = rs.getTimestamp(10);
				rsvCal.setTimeInMillis(rsvDateTime.getTime());
				rsv.setAppYy(rsvCal.get(Calendar.YEAR));
				rsv.setAppMm(rsvCal.get(Calendar.MONTH) + 1);
				rsv.setAppDd(rsvCal.get(Calendar.DAY_OF_MONTH));
				rsv.setAppHh(rsvCal.get(Calendar.HOUR_OF_DAY));
				rsv.setAppMi(rsvCal.get(Calendar.MINUTE));
				
			}
		}catch (Exception e) {
			System.out.println(pst);
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		}finally {
			// リソースの解放
			// Release resources
			try {
				if(rs != null) {
					rs.close();
				}
				if(pst != null) {
					pst.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
		return rsv;
	}
	
	/**
	 * 指定されたコースIDが将来の予約で使用されているかチェックします。
	 * Checks if the specified course ID is used in any future reservations.
	 * * @param c_Id チェックするコースID / Course ID to check
	 * @throws IdealException 予約が存在する場合またはDBエラー時 / When a reservation exists or a DB error occurs
	 */
	public static void reserveCourseChk(int c_Id) throws IdealException {
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		try {
			// DB接続の確立
			// Establish DB connection
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// SQL: 現在時刻以降の予約で使用されているかチェック
			// SQL: Check if used in reservations after the current time
			sql =
				"SELECT"
				+ " *"
				+ " FROM reserve"
				+ " WHERE c_id = ? and rsv_date > current_timestamp()";	
			
			pst = con.prepareStatement(sql);
			pst.setInt(1, c_Id);
			rs = pst.executeQuery();

			if(rs.next()) {
				// 予約が存在する場合は例外をスロー
				// Throw exception if a reservation exists
				throw new IdealException(IdealException.ERR_NO_NOT_RESERV_DELETE);
			}
		}catch (IdealException ie) {
			throw new IdealException(ie.getErrCd());
		
		}catch (Exception e) {
			// DBエラーの場合、IdealExceptionを再スロー
			// Re-throw IdealException on DB error
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		}finally {
			// リソースの解放
			// Release resources
			try {
				if(rs != null) {
					rs.close();
				}
				if(pst != null) {
					pst.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
	}
	
	
	/**
	 * 指定された日時に空きテーブルがあるかチェックし、あれば最小キャパシティのテーブルを返します。（新規登録用）
	 * Checks if a table is available at the specified date/time and returns the smallest suitable one. (For new insertion)
	 * * @param dateStr 予約日時文字列 (yyyy-MM-dd HH:mm:ss形式) / Reservation datetime string (yyyy-MM-dd HH:mm:ss format)
	 * @param personNum 人数 / Number of people
	 * @return 空きがあるTableLocオブジェクト、なければnull / Available TableLoc object, or null if none
	 * @throws IdealException DB接続エラー時 / When a DB connection error occurs
	 */
	public static TableLoc insertChk(String dateStr, int personNum) throws IdealException {
		TableLoc availableTable = null;
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		try {
			// DB接続の確立
			// Establish DB connection
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// SQL: 指定人数以上かつ、指定時間に予約がないテーブルを取得
			// SQL: Retrieve tables with sufficient capacity that are NOT reserved during the specified time slot
			sql =
		 	    "SELECT " +
		 	    "    t.table_id, t.table_name, max_capacity " +
		 	    "FROM " +
		 	    "    table_loc AS t " +
		 	    "WHERE " +
		 	    "    t.max_capacity >= ? " + // 1. Check if table capacity is sufficient
		 	    "    AND t.table_id NOT IN (" + // 2. Exclude tables that are already reserved
		 	    "        SELECT r.table_id " +
		 	    "        FROM reserve AS r " +
		 	    "        WHERE r.rsv_date <= ? " + // The start of the new reservation must not overlap with the end of an existing one
		 	    "        AND r.rsv_date >= ?" + // The end of the new reservation must not overlap with the start of an existing one
		 	    "    ) " +
		 	    "ORDER BY t.max_capacity ASC " + // Order by capacity to find the smallest suitable table
		 	    "LIMIT 1"; // Get only one available table
			
			// 予約開始時刻と終了時刻を計算 (ここでは3時間を予約期間として仮定)
			// Calculate reservation start and end times (assuming a 3-hour reservation period here)
			java.sql.Timestamp reservationStartTime = java.sql.Timestamp.valueOf(dateStr);
			java.sql.Timestamp reservationEndTime = new java.sql.Timestamp(reservationStartTime.getTime() + 10800000); // 3 hours (10,800,000 ms)

			pst = con.prepareStatement(sql);
			pst.setInt(1, personNum);
			pst.setTimestamp(2, reservationEndTime); // 予約終了時刻
			pst.setTimestamp(3, reservationStartTime); // 予約開始時刻

			System.out.println("pst->" + pst);
			rs = pst.executeQuery();

			if(rs.next()) {
				availableTable = new TableLoc();
				availableTable.setTableId(rs.getInt("table_id"));
	            availableTable.setTableName(rs.getString("table_name"));
	            availableTable.setMaxCapacity(rs.getInt("max_capacity"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		}finally {
			// リソースの解放
			// Release resources
			try {
				if(rs != null) {
					rs.close();
				}
				if(pst != null) {
					pst.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
		return availableTable;
	}
	

	/**
	 * 指定された日時に空きテーブルがあるかチェックし、あれば最小キャパシティのテーブルを返します。（更新用）
	 * チェック時に自身の予約IDを除外します。
	 * Checks if a table is available at the specified date/time and returns the smallest suitable one. (For update)
	 * Excludes its own reservation ID during the check.
	 * * @param rsvID 更新対象の予約ID / Reservation ID being updated
	 * @param dateStr 予約日時文字列 (yyyy-MM-dd HH:mm:ss形式) / Reservation datetime string (yyyy-MM-dd HH:mm:ss format)
	 * @param personNum 人数 / Number of people
	 * @return 空きがあるTableLocオブジェクト、なければnull / Available TableLoc object, or null if none
	 * @throws IdealException DB接続エラー時 / When a DB connection error occurs
	 */
	public static TableLoc updateChk(int rsvID, String dateStr, int personNum) throws IdealException {
		System.out.println("Reserve->updateChk[rsvID:"+rsvID+", dateStr:"+dateStr+", personNum:"+personNum);
		TableLoc availableTable = null;
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		try {
			// DB接続の確立
			// Establish DB connection
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// SQL: 指定人数以上かつ、指定時間に予約がないテーブルを取得 (自身の予約IDを除く)
			// SQL: Retrieve tables with sufficient capacity that are NOT reserved during the specified time slot (excluding its own reservation ID)
			sql =
		 	    "SELECT " +
		 	    "    t.table_id, t.table_name, max_capacity " +
		 	    "FROM " +
		 	    "    table_loc AS t " +
		 	    "WHERE " +
		 	    "    t.max_capacity >= ? " + // 1. Check if table capacity is sufficient
		 	    "    AND t.table_id NOT IN (" + // 2. Exclude tables that are already reserved
		 	    "        SELECT r.table_id " +
		 	    "        FROM reserve AS r " +
		 	    "        WHERE r.rsv_date <= ? " + 
		 	    "        AND r.rsv_date >= ?" +
		 	    "        AND r.rsv_id != ?" + // 3. Exclude the current reservation being updated
		 	    "    ) " +
		 	    "ORDER BY t.max_capacity ASC " + // Order by capacity to find the smallest suitable table
		 	    "LIMIT 1"; // Get only one available table
			
			// 予約開始時刻と終了時刻を計算 (ここでは2時間を予約期間として仮定)
			// Calculate reservation start and end times (assuming a 2-hour reservation period here)
			java.sql.Timestamp reservationStartTime = java.sql.Timestamp.valueOf(dateStr);
			java.sql.Timestamp reservationEndTime = new java.sql.Timestamp(reservationStartTime.getTime() + 7200000); // 2 hours (7,200,000 ms)

			pst = con.prepareStatement(sql);
			pst.setInt(1, personNum);
			pst.setTimestamp(2, reservationEndTime); // 予約終了時刻
			pst.setTimestamp(3, reservationStartTime); // 予約開始時刻
			pst.setInt(4, rsvID); // 自身の予約IDを除外
			
			rs = pst.executeQuery();

			if(rs.next()) {
				availableTable = new TableLoc();
				availableTable.setTableId(rs.getInt("table_id"));
	            availableTable.setTableName(rs.getString("table_name"));
	            availableTable.setMaxCapacity(rs.getInt("max_capacity"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		}finally {
			// リソースの解放
			// Release resources
			try {
				if(rs != null) {
					rs.close();
				}
				if(pst != null) {
					pst.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
		return availableTable;
	}
	
	/**
	 * 予約情報をデータベースに挿入します。
	 * Inserts a new reservation record into the database.
	 * * @param reserve 挿入する予約オブジェクト / Reserve object to insert
	 * @return 挿入された予約IDが設定されたReserveオブジェクト / Reserve object with the inserted reservation ID set
	 * @throws IdealException DB接続エラー時 / When a DB connection error occurs
	 */
	public static Reserve insert(Reserve reserve) throws IdealException {		
		
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		String sql = null;
		
		try {
			// DB接続の確立
			// Establish DB connection
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// 予約日時をTimestamp型に変換
			// Convert reservation date/time to Timestamp type
			Calendar rsvCal = Calendar.getInstance();
			rsvCal.set(reserve.getRsvYy(), reserve.getRsvMm() - 1, reserve.getRsvDd(), reserve.getRsvHh(), reserve.getRsvMi(), 0);
			Timestamp rsvTimestamp = new Timestamp(rsvCal.getTimeInMillis());
			
			// SQL: 予約情報の挿入
			// SQL: Insert reservation information
			sql = "INSERT INTO reserve (usr_id, rsv_date, person, table_id, c_id) VALUES (?,?,?,?,?)";
			//sql = "INSERT INTO reserve VALUES (DEFAULT,?,?,?,?,?,DEFAULT)";
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, reserve.getUsrId());
			pst.setTimestamp(2, rsvTimestamp);
	 	    pst.setInt(3, reserve.getPerson());
	 	    pst.setInt(4, reserve.getTableId());
	 	    pst.setInt(5, reserve.getCourseId());
			
			
		    pst.executeUpdate();

		    // 自動生成された予約IDを取得
		    // Retrieve the generated reservation ID
		    try (ResultSet rs = pst.getGeneratedKeys()) {
		        if (rs.next()) {
		            int newReserveId = rs.getInt(1);
		            reserve.setRsvId(newReserveId);
		            System.out.println("New Reserve ID: " + newReserveId);
		        }
			}
		 	
		}catch (Exception e) {
			System.out.println(pst);
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		}finally {
			// リソースの解放
			// Release resources
			try {				
				if(pst != null) {
					pst.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
		return reserve;
	}
	
	/**
	 * 予約情報をデータベースで更新します。
	 * Updates an existing reservation record in the database.
	 * * @param reserve 更新する予約オブジェクト / Reserve object to update
	 * @return 更新されたReserveオブジェクト / Updated Reserve object
	 * @throws IdealException DB接続エラー時 / When a DB connection error occurs
	 */
	public static Reserve update(Reserve reserve) throws IdealException {		
		
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		PreparedStatement pst = null;
		
		String sql = null;
		try {
			// DB接続の確立
			// Establish DB connection
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// 予約日時をTimestamp型に変換
			// Convert reservation date/time to Timestamp type
			Calendar rsvCal = Calendar.getInstance();
			rsvCal.set(reserve.getRsvYy(), reserve.getRsvMm() - 1, reserve.getRsvDd(), reserve.getRsvHh(), reserve.getRsvMi(), 0);
			Timestamp rsvTimestamp = new Timestamp(rsvCal.getTimeInMillis());
			
			// SQL: 予約情報の更新
			// SQL: Update reservation information
			sql = "UPDATE reserve SET usr_id = ?, rsv_date = ?, person = ?, table_id = ?, c_id = ?"
						+ " WHERE rsv_id = ?";
				pst = con.prepareStatement(sql);
				pst.setInt(1, reserve.getUsrId());
				pst.setTimestamp(2, rsvTimestamp);
		 	    pst.setInt(3, reserve.getPerson());
		 	    pst.setInt(4, reserve.getTableId());
		 	    pst.setInt(5, reserve.getCourseId());
		 	    pst.setInt(6, reserve.getRsvId());			
			
			pst.executeUpdate();
		 	
		}catch (Exception e) {
			System.out.println(pst);
			e.printStackTrace();
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		}finally {
			// リソースの解放
			// Release resources
			try {				
				if(pst != null) {
					pst.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
		return reserve;
	}
	
	/**
	 * 予約をデータベースから削除します。
	 * Deletes a reservation record from the database.
	 * * @param reserve 削除する予約オブジェクト / Reserve object to delete
	 * @throws IdealException DB接続エラー時 / When a DB connection error occurs
	 */
	public static void delete(Reserve reserve) throws IdealException {		
		InitialContext ic = null;
		DataSource ds = null;
		Connection con = null;
		
		try {
			// DB接続の確立
			// Establish DB connection
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/mariadb");
			con = ds.getConnection();
			
			// SQL: 予約情報の削除
			// SQL: Delete reservation information
			try (PreparedStatement deleteReservePst = con.prepareStatement("DELETE FROM reserve WHERE rsv_id = ?"))
			{		
				deleteReservePst.setInt(1,reserve.getRsvId());
				deleteReservePst.executeUpdate();
             }
		 	
		}catch (Exception e) {
			// DBエラーの場合、IdealExceptionを再スロー
			// Re-throw IdealException on DB error
			e.printStackTrace();			
			throw new IdealException(IdealException.ERR_NO_DB_EXCEPTION);
		}finally {
			// リソースの解放
			// Release resources
			try {				
				if(con != null) {	
					con.close();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * 予約日時が現在時刻を過ぎているかどうかをチェックします。
	 * Checks if the reservation date/time is in the past (<= current time).
	 * * @return true 予約日時が現在時刻を過ぎている場合 / if the reservation date/time has passed.
	 */
	public boolean isPassed() {
	    // 1. Reservation Date (rsvYy, rsvMm, ...)
	    Calendar rsvCal = Calendar.getInstance();
	    
	    // Note: Calendar months are 0-indexed (January=0), so subtract 1 from rsvMm.
	    rsvCal.set(
	        this.rsvYy, 
	        this.rsvMm - 1, 
	        this.rsvDd, 
	        this.rsvHh, 
	        this.rsvMi, 
	        0 // Seconds
	    );
	    rsvCal.set(Calendar.MILLISECOND, 0); // Clear milliseconds for accurate comparison
	    
	    long rsvTimeMillis = rsvCal.getTimeInMillis();

	    // 2. Current Date
	    long currentTimeMillis = System.currentTimeMillis();

	    // The reservation has passed if its time is less than or equal to the current time.
	    return rsvTimeMillis <= currentTimeMillis;
	}

}
