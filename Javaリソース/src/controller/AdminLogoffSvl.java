package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ログオフ処理を行うサーブレットです。<br>
 * セッションを無効化し、ログアウト後の画面に遷移させます。
 * <br><br>
 * This is a servlet that handles the logoff process.<br>
 * It invalidates the session and redirects to the screen after logout.
 *
 * @author モッタハイメ (Motta Jaime)
 */
@WebServlet("/admin/AdminLogoffSvl")
public class AdminLogoffSvl extends HttpServlet {
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
	public AdminLogoffSvl() {
		super();
		// TODO Auto-generated constructor stub
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
	 * セッションを無効化し、ログアウト後の画面にフォワードします。
	 * <br><br>
	 * Handles POST requests.<br>
	 * It invalidates the session and forwards to the post-logout page.
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

		// 既存のセッションを取得します。セッションが存在しない場合は新しく作成しません。
		// Gets the existing session. It does not create a new one if it doesn't exist.
		session = request.getSession(false);
		if (session != null) {
			// セッションが存在する場合、無効化（ログアウト）します。
			// If a session exists, it is invalidated (logged off).
			session.invalidate();
		}

		// ログオフ後の遷移先URLを設定します。
		// Sets the destination URL after logging off.
		url = "/adminLogin.jsp";

		// リクエストディスパッチャーを取得し、設定されたURLにフォワードします。
		// Gets the request dispatcher and forwards to the set URL.
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}