package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import model.IdealException;

/**
 * ユーザーのログイン処理を制御するサーブレットです。
 * フォームから送信されたユーザーIDとパスワードを検証し、ログインが成功した場合はセッションにユーザー情報を保存して専用ページへ遷移させます。
 *
 * This servlet controls the user login process.
 * It validates the user ID and password submitted from the form. If the login is successful, it saves the user information to the session and transitions to a dedicated user page.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
@WebServlet("/user/UserLoginSvl")
public class UserLoginSvl extends HttpServlet {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * HttpServletのデフォルトコンストラクタです。
	 * Default constructor for HttpServlet.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public UserLoginSvl() {
		super();
	}

	/**
	 * GETリクエストを処理します。doPostメソッドに処理を委譲します。
	 * Handles GET requests. It delegates the process to the doPost method.
	 *
	 * @param request HTTPリクエスト / HTTP request
	 * @param response HTTPレスポンス / HTTP response
	 * @throws ServletException サーブレット例外 / Servlet exception
	 * @throws IOException 入出力例外 / I/O exception
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * POSTリクエストを処理します。
	 * フォームからユーザーIDとパスワードを取得し、データベースで認証を行います。
	 * ログイン成功時にはセッションにユーザー情報を格納し、成功ページへフォワードします。
	 * 認証失敗時にはエラーメッセージを設定し、ログインページへ戻ります。
	 *
	 * Handles POST requests.
	 * It retrieves the user ID and password from the form and performs authentication against the database.
	 * Upon successful login, it stores the user information in the session and forwards to the success page.
	 * If authentication fails, it sets an error message and returns to the login page.
	 *
	 * @param request HTTPリクエスト / HTTP request
	 * @param response HTTPレスポンス / HTTP response
	 * @throws ServletException サーブレット例外 / Servlet exception
	 * @throws IOException 入出力例外 / I/O exception
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		String url = "";
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = null;

		String message = "";
		String pwd = null;
		int usrId;
		try {
			// ユーザーIDとパスワードをリクエストパラメータから取得
			// Get user ID and password from request parameters
			usrId = Integer.parseInt(request.getParameter("usrId"));
			pwd = request.getParameter("password");
		} catch (NumberFormatException e) {
			// パラメータの形式が不正な場合の処理
			// Handle cases where parameter format is invalid
			System.out.println("Parameters error:");
			e.printStackTrace();
			usrId = -1;
			pwd = "";
		}

		User usr = new User();
		try {
			// ユーザー認証を試行
			// Attempt to authenticate the user
			usr = User.login(usrId, pwd);
			System.out.println("---> usr: " + usr);
			if (usr != null) {
				// ログイン成功: セッションを開始し、ユーザー情報を格納
				// Login successful: Start a session and store user information
				session = request.getSession();
				session.setAttribute("userInfo", usr);
				url = "../userIndex.jsp";
			} else {
				// ログイン失敗: ユーザーが見つからない場合の例外をスロー
				// Login failed: Throw an exception if the user is not found
				throw new IdealException(IdealException.ERR_NO_NOT_MEMBER_EXCEPTION);
			}
		} catch (IdealException e) {
			// 認証失敗: エラーメッセージを設定し、ログインページへ戻る
			// Authentication failed: Set an error message and return to the login page
			message = e.getMsg();
			url = "../userLogin.jsp";
		}

		// メッセージをリクエストスコープに格納し、指定されたURLにフォワード
		// Store the message in the request scope and forward to the specified URL
		request.setAttribute("msg", message);
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}