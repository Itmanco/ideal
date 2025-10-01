package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;
import model.IdealException;

/**
 * MenuDeleteSvl サーブレット
 * * このサーブレットは、メニューまたはコースの削除処理を行います。
 * 管理者がログインしていない場合、ホームページにリダイレクトされます。
 * * Servlet that handles menu or course deletion.
 * If the administrator is not logged in, it redirects to the home page.
 * * @author モッタハイメ
 */
@WebServlet("/menu/MenuDeleteSvl")
public class MenuDeleteSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * * サーブレットのインスタンスを初期化します。
	 * * Constructor that initializes the servlet instance.
	 * * @see HttpServlet#HttpServlet()
	 */
	public MenuDeleteSvl() {
		super();
	}

	/**
	 * GETリクエストを処理します。
	 * 内部的に doPost メソッドを呼び出します。
	 * * Handles GET requests.
	 * Internally delegates to the doPost method.
	 * * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * POSTリクエストを処理します。
	 * * - ログインチェックを行う 
	 * - typeId が 100 の場合はコース削除処理を実行 
	 * - それ以外の場合はメニュー削除処理を実行 
	 * * Handles POST requests.
	 * * - Checks if the user is logged in 
	 * - If typeId == 100, executes course deletion process 
	 * - Otherwise, executes menu deletion process 
	 * * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = null;
		String url = "";
		String message = "";

		// リクエストとレスポンスの文字コードを設定
		// Set request and response character encoding
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// 既存のセッションを取得（存在しなければ null）
		// Get existing session (null if none exists)
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("adminInfo") == null) {
			// 管理者がログインしていない場合、ホーム画面へリダイレクト
			// If admin is not logged in, redirect to home page
			url = "/home.jsp";
			message = "このページにアクセスするにはログインが必要です。";
			// "You must be logged in to access this page."
		} else {
			int typeId = 100; // Default values
			int menuId = -1;  // Default values
			
			try {
				// 1. Try to get values from Request Attributes (used when forwarded on error)
				Object typeIdAttr = request.getAttribute("typeId");
				Object menuIdAttr = request.getAttribute("menuId");
				
				if (typeIdAttr != null) {
					// Assuming the attribute is an Integer or can be parsed
					if (typeIdAttr instanceof Integer) {
						typeId = (Integer) typeIdAttr;
					} else {
						typeId = Integer.parseInt(typeIdAttr.toString());
					}
				}
				
				if (menuIdAttr != null) {
					// Assuming the attribute is an Integer or can be parsed
					if (menuIdAttr instanceof Integer) {
						menuId = (Integer) menuIdAttr;
					} else {
						menuId = Integer.parseInt(menuIdAttr.toString());
					}
				}
				
				// 2. If the values are still at defaults (meaning they weren't attributes), 
				//    try to get them from Request Parameters (used for direct form submission)
				if (typeId == 100 && request.getParameter("typeId") != null) {
					typeId = Integer.parseInt(request.getParameter("typeId"));
				}
				if (menuId == -1 && request.getParameter("menuId") != null) {
					menuId = Integer.parseInt(request.getParameter("menuId"));
				}
				
			} catch (NumberFormatException e) {
				// If parsing fails for parameters/attributes, stick with the defaults (100 and -1)
				System.out.println("MenuDeleteSvl: Invalid Number Format for ID. Using defaults.");
			}

			System.out.println("MenuDeleteSvl->typeId:" + typeId + ", menuId:" + menuId);

			try {
				if (typeId == 100) {
					// ===== コース削除処理 =====
					// ===== Course deletion process =====
					
					ArrayList<Course> localOneCourse = Course.getOneCourse(menuId);
					
					if (localOneCourse == null || localOneCourse.size() < 1) {
						localOneCourse = new ArrayList<Course>();
						localOneCourse.add(Course.getCourse(menuId));
					}

					request.setAttribute("oneCourse", localOneCourse);
					url = "../courseDelete.jsp";
				} else {
					// ===== メニュー削除処理 =====
					// ===== Menu deletion process =====
					request.setAttribute("mType", MenuType.getAllType());
					request.setAttribute("oneMenu", Menu.getOneMenu(menuId, typeId));
					request.setAttribute("typeId", typeId);
					url = "../menuDelete.jsp";
				}
			} catch (IdealException e) {
				// エラーが発生した場合の処理
				// Error handling
				message = e.getMsg();
				url = "../menuMaintenance.jsp";
			}
		}

		// メッセージと遷移先をリクエストにセットし、フォワード
		// Set message and forward to destination
		if (request.getAttribute("msg") == null) {
			request.setAttribute("msg", message);
		}	
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}

