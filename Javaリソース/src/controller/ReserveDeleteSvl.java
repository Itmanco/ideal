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

/**
 * 予約削除画面表示用サーブレット。
 * 削除対象の予約情報を取得し、削除確認画面（reserveDelete.jsp）へフォワードします。
 * Servlet for displaying the reservation deletion screen.
 * It retrieves the reservation information to be deleted and forwards to the deletion confirmation screen (reserveDelete.jsp).
 *
 * @author モッタハイメ
 * @version 1.0.0
 * @see javax.servlet.http.HttpServlet
 */
@WebServlet("/reserve/ReserveDeleteSvl")
public class ReserveDeleteSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * コンストラクタ。
     * Constructor.
     * @see HttpServlet#HttpServlet()
     */
    public ReserveDeleteSvl() {
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
     * 予約IDを取得し、該当する予約情報を取得後、削除確認画面に遷移します。
	 * Processes HTTP POST requests.
     * It retrieves the reservation ID, gets the corresponding reservation information, and then transitions to the deletion confirmation screen.
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
		
		System.out.println("ReserveDeleteSvl->doPost"); 
		
		// ログイン状態を確認
		// Check for login status
		if (session == null || session.getAttribute("userInfo") == null) {
			// 未ログインの場合、ホーム画面へ遷移
			// If not logged in, transition to the home screen
			url = "/home.jsp";
			message = "このページにアクセスするにはログインが必要です。"; // You must be logged in to access this page.
		} else {
            int rsvId;
            try {
            	// リクエストパラメータから予約IDを取得
				// Get the reservation ID from the request parameter
            	rsvId = Integer.parseInt(request.getParameter("rsvId"));
			} catch (NumberFormatException e) {
				// 予約IDの取得に失敗した場合
				// If retrieval of the reservation ID fails
				// 取得に失敗した場合、デフォルト値を設定します。
				// If retrieval fails, set a default value.
				rsvId = -1;
			}
			
			try {
				// 予約IDを基に予約情報を取得
				// Get reservation information based on the reservation ID
				Reserve rsv = Reserve.getReserve(rsvId);
				
				// 取得した予約情報をリクエストスコープに格納
				// Store the retrieved reservation information in the request scope
				request.setAttribute("reserve", rsv);
				
				// 削除確認画面へ遷移
				// Transition to the deletion confirmation screen
				url = "../reserveDelete.jsp";
			} catch (IdealException e) {
				// 予約情報取得時に例外が発生した場合
				// If an exception occurs while retrieving reservation information
				
				// 例外が発生した場合、エラーメッセージを取得して予約一覧ページへ遷移
				// If an exception occurs, get the error message and transition to the reservation list page
				message = e.getMsg();
				url = "../reserveList.jsp";
			}
		}

		// メッセージをリクエストスコープに格納し、指定されたURLにフォワード
		// Store the message in the request scope and forward to the specified URL
		if (request.getAttribute("msg") == null) {
			request.setAttribute("msg", message);
		}		
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}