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
 * ユーザー情報の更新画面への遷移を制御するサーブレットです。
 * ログインしているユーザーの情報を取得し、更新フォームへフォワードします。
 *
 * This servlet controls the transition to the user information update page.
 * It retrieves the information of the logged-in user and forwards to the update form.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
@WebServlet("/user/UserUpdateSvl")
public class UserUpdateSvl extends HttpServlet {
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
	public UserUpdateSvl() {
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
	 * ユーザーがログインしているかを確認し、ログインしていればセッションからユーザー情報を取得して更新ページへフォワードします。
	 * ログインしていない場合は、ログインページにリダイレクトし、エラーメッセージを表示します。
	 *
	 * Handles POST requests.
	 * It checks if the user is logged in. If logged in, it retrieves user information from the session and forwards to the update page.
	 * If not logged in, it redirects to the login page and displays an error message.
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
		String message = null;
		System.out.println("UserUpdateSvl->doPost");
		HttpSession session = request.getSession(false);

		// ログイン状態を確認
		// Check for login status
		if (session == null || session.getAttribute("userInfo") == null) {
			url = "/home.jsp";
			message = "このページにアクセスするにはログインが必要です。"; // You must be logged in to access this page.
		} else {
			// セッションからユーザー情報を取得
			// Retrieve user information from the session
			User usr = (User) session.getAttribute("userInfo");
			try {
				// データベースから最新のユーザー情報を取得
				// Retrieve the latest user information from the database
				usr = User.getUser(usr.getUsrId());
				if (usr != null) {
					// ユーザー情報をリクエストスコープに格納し、更新ページへ遷移
					// Store user information in the request scope and transition to the update page
					request.setAttribute("user", usr);
					url = "../userUpdate.jsp";
				} else {
					// ユーザー情報が見つからない場合の例外をスロー
					// Throw an exception if the user information is not found
					throw new IdealException(IdealException.ERR_NO_NOT_MEMBER_EXCEPTION);
				}
			} catch (IdealException e) {
				// 例外が発生した場合、エラーメッセージを取得してログインページへ遷移
				// If an exception occurs, get the error message and transition to the login page
				message = e.getMsg();
				url = "../userLogin.jsp";
			}
		}

		// メッセージをリクエストスコープに格納し、指定されたURLにフォワード
		// Store the message in the request scope and forward to the specified URL
		request.setAttribute("msg", message);
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}