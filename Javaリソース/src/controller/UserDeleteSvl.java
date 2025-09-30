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
 * ユーザーの削除処理を制御するサーブレットです。
 * ログインしているユーザーの情報を取得し、削除確認ページへ遷移させます。
 *
 * This servlet controls the user deletion process.
 * It retrieves the information of the logged-in user and transitions to the deletion confirmation page.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
@WebServlet("/user/UserDeleteSvl")
public class UserDeleteSvl extends HttpServlet {
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
	public UserDeleteSvl() {
		super();
	}

	/**
	 * GETリクエストを処理します。doPostメソッドに処理を委譲します。
	 * Handles GET requests. Delegates the process to the doPost method.
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
	 * ユーザーがログインしているかを確認し、ログインしていればユーザー情報を取得して削除確認ページにフォワードします。
	 * ログインしていない場合は、ログインページにリダイレクトし、エラーメッセージを表示します。
	 *
	 * Handles POST requests.
	 * It checks if the user is logged in. If logged in, it retrieves user information and forwards to the deletion confirmation page.
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

		// リクエストとレスポンスのエンコーディングを設定
		// Set request and response encoding
		System.out.println("UserDeleteSvl->doPost");
		HttpSession session = request.getSession(false);

		// セッションを取得し、ログイン状態を確認
		// Get the session and check the login status
		if (session == null || session.getAttribute("userInfo") == null) {
			url = "/home.jsp";
			message = "このページにアクセスするにはログインが必要です。"; // You must be logged in to access this page.
		} else {
			// セッションからユーザー情報を取得
			// Retrieve user information from the session
			User usr = (User) session.getAttribute("userInfo");
			System.out.println("UserDeleteSvl->usr");
			System.out.println(usr);

			try {
				// データベースから最新のユーザー情報を取得
				// Retrieve the latest user information from the database
				usr = User.getUser(usr.getUsrId());
				if (usr != null) {
					// ユーザー情報をリクエストスコープに格納し、削除確認ページへ遷移
					// Store user information in the request scope and transition to the deletion confirmation page
					request.setAttribute("user", usr);
					url = "../userDelete.jsp";
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