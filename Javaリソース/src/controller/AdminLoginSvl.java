package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Admin;
import model.IdealException;

/**
 * ログイン画面からのリクエストを処理するサーブレットです。<br>
 * 管理者IDとパスワードの認証を行い、セッションに管理者情報を格納します。
 * <br><br>
 * This is a servlet that processes requests from the login screen.<br>
 * It authenticates the administrator ID and password and stores the administrator information in the session.
 *
 * @author モッタハイメ (Motta Jaime)
 */
@WebServlet("/admin/AdminLoginSvl")
public class AdminLoginSvl extends HttpServlet {
	/**
	 * シリアルバージョンUIDです。<br>
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタです。<br>
	 * The constructor.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminLoginSvl() {
		super();
	}

	/**
	 * GETリクエストを処理します。<br>
	 * GETリクエストはPOSTリクエストに転送されます。
	 * <br><br>
	 * Handles GET requests.<br>
	 * GET requests are forwarded to the POST request handler.
	 *
	 * @param request  サーブレットが受け取ったHTTPリクエストオブジェクトです。<br>The HTTP request object received by the servlet.
	 * @param response サーブレットが送り返すHTTPレスポンスオブジェクトです。<br>The HTTP response object the servlet sends back.
	 * @throws ServletException サーブレットが特定の条件で例外を投げる場合です。<br>If a servlet encounters a servlet-specific problem.
	 * @throws IOException      入出力操作が失敗または中断した場合です。<br>If an I/O error occurs.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POSTメソッドを呼び出すことで、GETリクエストもPOSTと同じロジックで処理します。
		// By calling the doPost method, GET requests are processed with the same logic as POST.
		this.doPost(request, response);
	}

	/**
	 * POSTリクエストを処理します。<br>
	 * フォームから送信された管理者IDとパスワードを検証し、ログイン処理を行います。
	 * <br><br>
	 * Handles POST requests.<br>
	 * It validates the administrator ID and password submitted from the form and performs the login process.
	 *
	 * @param request  サーブレットが受け取ったHTTPリクエストオブジェクトです。<br>The HTTP request object received by the servlet.
	 * @param response サーブレットが送り返すHTTPレスポンスオブジェクトです。<br>The HTTP response object the servlet sends back.
	 * @throws ServletException サーブレットが特定の条件で例外を投げる場合です。<br>If a servlet encounters a servlet-specific problem.
	 * @throws IOException      入出力操作が失敗または中断した場合です。<br>If an I/O error occurs.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストディスパッチャーとURLを初期化します。
		// Initializes the request dispatcher and URL.
		RequestDispatcher rd = null;
		String url = "";
		// 文字エンコーディングを設定します。
		// Sets the character encoding.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// セッションオブジェクトを初期化します。
		// Initializes the session object.
		HttpSession session = null;

		// ログイン情報とメッセージ変数を初期化します。
		// Initializes login information and message variables.
		String admName = null, password = null;
		String message = "";

		try {
			// フォームから管理者IDとパスワードを取得します。
			// Retrieves the administrator ID and password from the form.
			admName = request.getParameter("admName");
			password = request.getParameter("password");
		} catch (NumberFormatException e) {
			// 取得に失敗した場合、空の文字列を設定します。
			// If retrieval fails, set empty strings.
			admName = "";
			password = "";
		}

		Admin adm;
		try {
			// Adminモデルクラスのloginメソッドを呼び出して認証を試みます。
			// Calls the login method of the Admin model class to attempt authentication.
			adm = Admin.login(admName, password);
			// 認証が成功し、パスワードも一致する場合
			// If authentication is successful and the password also matches
			if (adm != null && adm.getPassword().equals(password)) {
				// ログイン成功時の遷移先URLを設定します。
				// Sets the destination URL for a successful login.
				url = "../adminIndex.jsp";
				// セッションを取得し、管理者情報を属性として保存します。
				// Gets the session and stores the administrator information as an attribute.
				session = request.getSession();
				session.setAttribute("adminInfo", adm);
			} else {
				// 認証失敗時の遷移先URLを設定します。
				// Sets the destination URL for a failed authentication.
				url = "../adminLogin.jsp";
				// 認証エラーメッセージを取得します。
				// Retrieves the authentication error message.
				IdealException local = new IdealException(3);
				message = local.getMsg();
			}
		} catch (IdealException e) {
			// 例外が発生した場合、エラーメッセージを取得します。
			// If an exception occurs, retrieves the error message.
			message = e.getMsg();
		}
		// リクエストスコープにメッセージを設定します。
		// Sets the message in the request scope.
		request.setAttribute("msg", message);
		// リクエストディスパッチャーを取得し、設定されたURLにフォワードします。
		// Gets the request dispatcher and forwards to the set URL.
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}