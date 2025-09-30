package model;

import java.io.Serializable;

/**
 * 理想的なアプリケーションにおける例外を扱うカスタム例外クラスです。
 * このクラスは、システムエラー、データベースエラー、認証エラーなど、特定のコードに対応するメッセージを提供します。
 *
 * This is a custom exception class for handling exceptions in an ideal application.
 * It provides messages corresponding to specific codes for system errors, database errors, authentication errors, and more.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
public class IdealException extends Exception {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = -7477002882218014411L;

	/**
	 * 例外なし、一般的なメッセージコード。
	 * No exception, general message code.
	 */
	public static final int ERR_NO_EXCEPTION = 0;
	/**
	 * データベース処理での例外を示すコード。
	 * Code indicating an exception in a database process.
	 */
	public static final int ERR_NO_DB_EXCEPTION = 1;
	/**
	 * お客様IDまたはパスワードが不正であることの例外を示すコード。
	 * Code indicating an exception for an invalid customer ID or password.
	 */
	public static final int ERR_NO_NOT_MEMBER_EXCEPTION = 2;
	/**
	 * 管理者名またはパスワードが不正であることの例外を示すコード。
	 * Code indicating an exception for an invalid administrator name or password.
	 */
	public static final int ERR_NO_ADMIN_EXCEPTION = 3;
	/**
	 * 指定された日時に空席がないことの例外を示すコード。
	 * Code indicating an exception for no vacancies at the specified date and time.
	 */
	public static final int ERR_NO_NOT_VACANCY_EXCEPTION = 4;
	/**
	 * 予約されているコースのため削除できないことの例外を示すコード。
	 * Code indicating an exception where a course cannot be deleted because it is reserved.
	 */
	public static final int ERR_NO_NOT_RESERV_DELETE = 5;
	/**
	 * コースに登録されているメニューのため削除できないことの例外を示すコード。
	 * Code indicating an exception where a menu cannot be deleted because it is registered in a course.
	 */
	public static final int ERR_NO_NOT_MENU_DELETE = 6;
	
	/**
	 * エラーコードに対応するメッセージの配列です。
	 * An array of messages corresponding to error codes.
	 */
	private static String[] ERR_MSGS = {
		"障害が発生しました。",									// 0: An error has occurred.
		"データベース処理で例外が発生しました。",				// 1: An exception occurred during database processing.
		"お客様ID、パスワードを確認してください。",				// 2: Please check your customer ID and password.
		"管理者名、パスワードを確認してください。",				// 3: Please check your administrator name and password.
		"ご指定された日時に、空席がございませんでした。",		// 4: There were no vacancies at the time you specified.
		"予約されているコースなので削除できません。",			// 5: This course cannot be deleted because it is reserved.
		"コースに登録されているメニューなので削除できません。"	// 6: This menu cannot be deleted because it is registered in a course.
	};
	
	/**
	 * この例外のエラーコードです。
	 * The error code for this exception.
	 */
	private int errCd;
	/**
	 * この例外のメッセージです。
	 * The message for this exception.
	 */
	private String ERR_MSG;
	
	/**
	 * 指定されたエラーコードでIdealExceptionを初期化します。
	 * Initializes an IdealException with the specified error code.
	 *
	 * @param errCd エラーコード / The error code
	 */
	public IdealException(int errCd) {
		this.errCd = errCd;
		this.ERR_MSG = ERR_MSGS[errCd];
	}
	
	/**
	 * この例外のメッセージを取得します。
	 * Retrieves the message for this exception.
	 *
	 * @return 例外メッセージ / The exception message
	 */
	public String getMsg() {
		return ERR_MSG;
	}

	/**
	 * この例外のエラーコードを取得します。
	 * Retrieves the error code for this exception.
	 *
	 * @return エラーコード / The error code
	 */
	public int getErrCd() {
		return errCd;
	}
}