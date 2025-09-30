package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.IdealException;
import model.Reserve;
import model.User;

/**
 * ユーザーの予約一覧画面表示用サーブレット。
 * ログインユーザーの予約情報を取得し、一覧画面（reserveList.jsp）へフォワードします。
 * Servlet for displaying the user's reservation list screen.
 * It retrieves the logged-in user's reservation information and forwards to the list screen (reserveList.jsp).
 *
 * @author モッタハイメ
 * @version 1.0.0
 * @see javax.servlet.http.HttpServlet
 */
@WebServlet("/reserve/ReserveListSvl")
public class ReserveListSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * コンストラクタ。
     * Constructor.
     * @see HttpServlet#HttpServlet()
     */
    public ReserveListSvl() {
        super();
    }

	/**
	 * HTTP GETリクエストを処理し、doPost()メソッドへ委譲します。
     * Processes HTTP GET requests and delegates them to the doPost() method.
	 * @param request サーブレットリクエスト / Servlet request
	 * @param response サーブレットレスポンス / Servlet response
	 * @throws ServletException サーブレット例外 / Servlet exception
	 * @throws IOException 入出力例外 / I/O exception
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * HTTP POSTリクエストを処理します。
     * ユーザーIDに基づいて予約一覧を取得し、JSPへフォワードします。
	 * Processes HTTP POST requests.
     * It retrieves the reservation list based on the user ID and forwards to the JSP.
	 * @param request サーブレットリクエスト / Servlet request
	 * @param response サーブレットレスポンス / Servlet response
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
		HttpSession session = request.getSession(false);

		// ログイン状態を確認
		// Check for login status
		if (session == null || session.getAttribute("userInfo") == null) {
			// 未ログインの場合、ホーム画面へ遷移
			// If not logged in, transition to the home screen
			url = "/home.jsp";
			message = "このページにアクセスするにはログインが必要です。"; // You must be logged in to access this page.
		} else {
			// セッションからユーザー情報を取得
			// Retrieve user information from the session
			User usr = (User) session.getAttribute("userInfo");
			try {
				// ユーザーIDに基づき予約一覧を取得し、リクエストスコープに格納
				// Retrieve reservation list based on user ID and store it in the request scope
				request.setAttribute("reserveList", Reserve.getReserveList(usr.getUsrId()));
				
				// 予約一覧画面へ遷移
				// Transition to the reservation list screen
				url = "../reserveList.jsp";				
			} catch (IdealException e) {
				// 例外が発生した場合、エラーメッセージを取得してユーザーインデックスページへ遷移
				// If an exception occurs, get the error message and transition to the user index page
				message = e.getMsg();
				url = "../userIndex.jsp";
			}
		}

		// メッセージをリクエストスコープに格納し、指定されたURLにフォワード
		// Store the message in the request scope and forward to the specified URL
		request.setAttribute("msg", message);
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}