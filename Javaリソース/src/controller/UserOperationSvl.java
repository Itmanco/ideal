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
 * ユーザー情報の新規登録、更新、削除を制御するサーブレットです。
 * リクエストの「mode」パラメータに応じて、それぞれの操作を実行します。
 *
 * This servlet controls the creation, updating, and deletion of user information.
 * It executes the respective operation based on the "mode" parameter in the request.
 *
 * @author モッタハイメ (Motta Jaime)
 * @version 1.0
 */
@WebServlet("/user/UserOperationSvl")
public class UserOperationSvl extends HttpServlet {
	/**
	 * 直列化のためのバージョンIDです。
	 * Version ID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ユーザー情報を登録するモード。
	 * Mode for registering new user information.
	 */
	public static final int INSERT = 11;
	/**
	 * ユーザー情報を更新するモード。
	 * Mode for updating user information.
	 */
	public static final int UPDATE = 12;
	/**
	 * ユーザー情報を削除するモード。
	 * Mode for deleting user information.
	 */
	public static final int DELETE = 13;
	
	/**
	 * HttpServletのデフォルトコンストラクタです。
	 * Default constructor for HttpServlet.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public UserOperationSvl() {
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
	 * リクエストパラメータの「mode」に応じて、ユーザーの新規登録、更新、削除のいずれかの処理を実行します。
	 * 登録以外の操作は、ログイン状態であることを前提とします。
	 *
	 * Handles POST requests.
	 * It executes one of the operations—user creation, update, or deletion—based on the "mode" request parameter.
	 * All operations except creation assume the user is logged in.
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
		String message = "";
		User usr = null;
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		int mode = -1;
		try {
			// リクエストパラメータからモードを取得
			// Get the mode from the request parameter
			mode = Integer.parseInt(request.getParameter("mode"));
		} catch (NumberFormatException e) {
			// パラメータが不正な場合は何もしない
			// Do nothing if the parameter is invalid
		}
		
		HttpSession session = request.getSession(false);
		// 登録（INSERT）以外の操作ではログイン状態をチェック
		// Check for login status for all operations except INSERT
		if (mode != INSERT && (session == null || session.getAttribute("userInfo") == null)) {
			url = "/home.jsp";
			message = "このページにアクセスするにはログインが必要です。"; // You must be logged in to access this page.
		} else {
			System.out.println("UserOperationSvl->mode:" + mode);
			
			switch (mode) {
				case INSERT:
					// 新規ユーザー登録処理
					// Process for new user creation
					usr = new User();
					usr.setUsrName(request.getParameter("usrName"));
					usr.setPassword(request.getParameter("password"));
					usr.setAddress(request.getParameter("address"));
					usr.setPhone(request.getParameter("phone"));
					usr.setMail(request.getParameter("mail"));
					
					try {
						User.insert(usr);
						session = request.getSession();
						session.setAttribute("userInfo", usr);
						System.out.println("UserOperationSvl->INSERT");
						System.out.println(usr);
						url = "../userInsertCompletion.jsp";
					} catch (IdealException e) {
						message = e.getMsg();
						url = "../userInsert.jsp";
					}
					break;
				
				case DELETE:
					// ユーザー削除処理
					// Process for user deletion
					usr = new User();
					usr.setUsrId(Integer.parseInt(request.getParameter("usrId")));
					
					try {
						User.delete(usr);
					} catch (IdealException e) {
						message = e.getMsg();
					}
					
					session = request.getSession(false);
					if (session != null) {
						session.invalidate(); // セッションを無効化
						// Invalidate the session
					}
					url = "../home.jsp";
					break;
					
				case UPDATE:
					// ユーザー情報更新処理
					// Process for user information update
					usr = new User();
					usr.setUsrId(Integer.parseInt(request.getParameter("usrId")));
					usr.setUsrName(request.getParameter("usrName"));
					usr.setPassword(request.getParameter("password"));
					usr.setAddress(request.getParameter("address"));
					usr.setPhone(request.getParameter("phone"));
					usr.setMail(request.getParameter("mail"));
					
					try {
						User.update(usr);
						session = request.getSession();
						session.setAttribute("userInfo", usr);
						url = "../userIndex.jsp";
					} catch (IdealException e) {
						message = e.getMsg();
						request.setAttribute("user", usr);
						url = "../userUpdate.jsp";
					}
					break;
					
				default:
					// 不正なモードの場合は何もしない
					// Do nothing for an invalid mode
					break;
			}
		}
		
		request.setAttribute("msg", message);
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}