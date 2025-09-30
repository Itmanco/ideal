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
 * ユーザーのログオフ処理を制御するサーブレットです。
 * 現在のセッションを無効化し、ユーザーをログアウトさせます。その後、ホーム画面に遷移します。
 *
 * This servlet controls the user logoff process.
 * It invalidates the current session, logs the user out, and then transitions to the home page.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
@WebServlet("/user/UserLogoffSvl")
public class UserLogoffSvl extends HttpServlet {
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
	public UserLogoffSvl() {
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
	 * ユーザーのセッションが存在する場合、そのセッションを無効化（ログアウト）します。
	 * 処理後、ユーザーをホーム画面にフォワードします。
	 *
	 * Handles POST requests.
	 * If a user session exists, it invalidates the session (logs the user out).
	 * After the process, it forwards the user to the home page.
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
		
		// セッションを破棄（ログアウト）
		// Invalidate the session (log out)
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			System.out.println("Inside: LOGOUT");
		}
		
		// ホーム画面へ遷移
		// Transition to the home page
		url = "/home.jsp";
		
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}