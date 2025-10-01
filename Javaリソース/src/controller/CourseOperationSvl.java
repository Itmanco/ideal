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

import model.Course;
import model.Coursectl;
import model.Menu;
import model.MenuType;
import model.Reserve;
import model.IdealException;

/**
 * コースメニューの登録、更新、削除を処理するサーブレットです。<br>
 * 管理者権限を持つユーザーからのリクエストを受け付け、データベースのコース情報を操作します。
 * <br><br>
 * This is a servlet that processes the insertion, updating, and deletion of course menus.<br>
 * It accepts requests from users with administrator privileges and manipulates course information in the database.
 *
 * @author モッタハイメ (Motta Jaime)
 */
@WebServlet("/menu/CourseOperationSvl")
public class CourseOperationSvl extends HttpServlet {
	/**
	 * シリアルバージョンUIDです。<br>
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 登録モードを表す定数です。<br>
	 * Constant representing the insertion mode.
	 */
	public static final int INSERT = 21;

	/**
	 * 更新モードを表す定数です。<br>
	 * Constant representing the update mode.
	 */
	public static final int UPDATE = 22;

	/**
	 * 削除モードを表す定数です。<br>
	 * Constant representing the deletion mode.
	 */
	public static final int DELETE = 23;

	/**
	 * コースに含まれるメニュータイプIDの配列です。<br>
	 * An array of menu type IDs included in a course.
	 */
	public static final int[] COURSE_MENU_TYPE_ID = { 200, 210, 220, 300, 310, 400 };

	/**
	 * コンストラクタです。<br>
	 * The constructor.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public CourseOperationSvl() {
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
	 * コースの登録・更新・削除処理を実行します。
	 * <br><br>
	 * Handles POST requests.<br>
	 * Executes the insertion, updating, or deletion of a course.
	 *
	 * @param request  サーブレットが受け取ったHTTPリクエストオブジェクトです。<br>The HTTP request object received by the servlet.
	 * @param response サーブレットが送り返すHTTPレスポンスオブジェクトです。<br>The HTTP response object the servlet sends back.
	 * @throws ServletException サーブレットが特定の条件で例外を投げる場合です。<br>If a servlet encounters a servlet-specific problem.
	 * @throws IOException      入出力操作が失敗または中断した場合です。<br>If an I/O error occurs.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストディスパッチャーとURL、メッセージ変数を初期化します。
		// Initializes the request dispatcher, URL, and message variables.
		RequestDispatcher rd = null;
		String url = "";
		String message = "";
		// 文字エンコーディングを設定します。
		// Sets the character encoding.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// セッションを取得し、管理者情報が存在するかチェックします。
		// Gets the session and checks if administrator information exists.
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("adminInfo") == null) {
			// ログインしていない場合、ログインページにリダイレクトします。
			// If not logged in, redirect to the login page.
			url = "/home.jsp";
			message = "このページにアクセスするにはログインが必要です。";
		} else {
			// ログインしている場合、処理を続行します。
			// If logged in, continues with the process.
			int typeId;
			int mode;
			ArrayList<Coursectl> cts = new ArrayList<Coursectl>();
			Course c;
			try {
				// リクエストパラメータからタイプIDを取得します。
				// Retrieves the type ID from the request parameters.
				typeId = Integer.parseInt(request.getParameter("typeId"));
			} catch (NumberFormatException e) {
				// 取得に失敗した場合、デフォルト値を設定します。
				// If retrieval fails, set a default value.
				typeId = 100;
			}
			try {
				// リクエストパラメータからモードを取得します（登録、更新、削除）。
				// Retrieves the mode from the request parameters (insert, update, delete).
				mode = Integer.parseInt(request.getParameter("mode"));
			} catch (NumberFormatException e) {
				// 取得に失敗した場合、デフォルト値を設定します。
				// If retrieval fails, set a default value.
				mode = 0;
			}

			try {
				// メニュータイプをリクエストスコープに設定します。
				// Sets the menu types in the request scope.
				request.setAttribute("mType", MenuType.getAllType());
				// モードに応じて処理を分岐します。
				// Branches the process based on the mode.
				switch (mode) {
					case INSERT:
						// 登録処理
						// Insertion process
						c = new Course();
						c.setCourseName(request.getParameter("courseName"));
						c.setPrice(Integer.parseInt(request.getParameter("price")));
						c.setOrderFlg(Integer.parseInt(request.getParameter("orderFlg")));
						c.setDetail(request.getParameter("detail"));
						c.setTypeId(typeId);

						cts = new ArrayList<Coursectl>();						
						
						getMenus(request, cts, c.getCourseId());

						System.out.println("-->> CourseOperationSvl: " + c + "  cts.size:" + cts.size());
						
						try {
							Course.updateCourse(c, mode, cts);
						} catch (IdealException ee) {
							request.setAttribute("msg", ee.getMsg());
							ArrayList<ArrayList<Menu>> typeMenuList = new ArrayList<>();
		                    for (int value : Course.COURSE_MENU_TYPE_ID) {
		                        typeMenuList.add(Menu.getMenu(value));
		                    }
		                    request.setAttribute("typeMenuList", typeMenuList);
		                    url = "../courseInsert.jsp";
		                    return;
						}

						request.setAttribute("menu", Menu.getMenu(typeId));
						url = "../menuMaintenance.jsp";
						break;
					case DELETE:
						// 削除処理
						// Deletion process
						c = new Course();
						c.setCourseId(Integer.parseInt(request.getParameter("courseId")));
						cts = new ArrayList<Coursectl>();					
												
						try {
							Reserve.reserveCourseChk(c.getCourseId());
							Course.updateCourse(c, mode, cts);
						} catch (IdealException ee) {
							request.setAttribute("msg", ee.getMsg());
							request.setAttribute("typeId", typeId); 
							request.setAttribute("menuId", c.getCourseId()); 

							System.out.println("menuId:"+c.getCourseId()); 
							System.out.println("typeId:"+typeId); 

							rd = request.getRequestDispatcher("/menu/MenuDeleteSvl");
							rd.forward(request, response); 
					        return; 
						}

						request.setAttribute("menu", Menu.getMenu(typeId));
						url = "../menuMaintenance.jsp";
						break;
					case UPDATE:
						// 更新処理
						// Update process
						c = new Course();
						c.setCourseId(Integer.parseInt(request.getParameter("courseId")));
						c.setCourseName(request.getParameter("courseName"));
						c.setPrice(Integer.parseInt(request.getParameter("price")));
						c.setOrderFlg(Integer.parseInt(request.getParameter("orderFlg")));
						c.setDetail(request.getParameter("detail"));
						c.setTypeId(typeId);

						cts = new ArrayList<Coursectl>();
						getMenus(request, cts, c.getCourseId());

						System.out.println("-->> CourseOperationSvl: " + c + "  cts.size:" + cts.size());
						Course.updateCourse(c, mode, cts);

						request.setAttribute("menu", Menu.getMenu(typeId));
						url = "../menuMaintenance.jsp";
						break;
					default:
						// その他のモードの場合
						// For other modes
						break;
				}
			} catch (IdealException e) {
				// 業務例外が発生した場合、メッセージを取得します。
				// If a business exception occurs, get the message.
				message = e.getMsg();
			}
		}
		// リクエストスコープにメッセージを設定します。
		// Sets the message in the request scope.
		request.setAttribute("msg", message);
		// リクエストディスパッチャーを取得し、設定されたURLにフォワードします。
		// Gets the request dispatcher and forwards to the set URL.
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * リクエストからメニューIDを取得し、Coursectlオブジェクトのリストを作成します。<br>
	 * 各メニューカテゴリ（前菜、スープなど）に対応するメニューIDを取得し、リストに追加します。
	 * <br><br>
	 * Retrieves menu IDs from the request and creates a list of Coursectl objects.<br>
	 * It gets the menu ID corresponding to each menu category (appetizer, soup, etc.) and adds it to the list.
	 *
	 * @param request  HttpServletRequestオブジェクトです。<br>The HttpServletRequest object.
	 * @param cts      Coursectlオブジェクトを格納するArrayListです。<br>The ArrayList to store Coursectl objects.
	 * @param c_Id     コースIDです。<br>The course ID.
	 */
	private void getMenus(HttpServletRequest request, ArrayList<Coursectl> cts, int c_Id) {
		// 各メニューカテゴリのIDをリクエストから取得し、有効な場合にリストに追加します。
		// Retrieves the ID of each menu category from the request and adds it to the list if valid.
		try {
			int appetizerId = Integer.parseInt(request.getParameter("appetizerId"));
			if (appetizerId > 0) {
				Coursectl a1 = new Coursectl();
				a1.setC_Id(c_Id);
				a1.setM_Id(appetizerId);
				cts.add(a1);
			}
		} catch (Exception e) {}
		try {
			int soupId = Integer.parseInt(request.getParameter("soupId"));
			if (soupId > 0) {
				Coursectl a1 = new Coursectl();
				a1.setC_Id(c_Id);
				a1.setM_Id(soupId);
				cts.add(a1);
			}
		} catch (Exception e) {}
		try {
			int pastaId = Integer.parseInt(request.getParameter("pastaId"));
			if (pastaId > 0) {
				Coursectl a1 = new Coursectl();
				a1.setC_Id(c_Id);
				a1.setM_Id(pastaId);
				cts.add(a1);
			}
		} catch (Exception e) {}
		try {
			int meatId = Integer.parseInt(request.getParameter("meatId"));
			if (meatId > 0) {
				Coursectl a1 = new Coursectl();
				a1.setC_Id(c_Id);
				a1.setM_Id(meatId);
				cts.add(a1);
			}
		} catch (Exception e) {}
		try {
			int fishId = Integer.parseInt(request.getParameter("fishId"));
			if (fishId > 0) {
				Coursectl a1 = new Coursectl();
				a1.setC_Id(c_Id);
				a1.setM_Id(fishId);
				cts.add(a1);
			}
		} catch (Exception e) {}
		try {
			int dessertId = Integer.parseInt(request.getParameter("dessertId"));
			if (dessertId > 0) {
				Coursectl a1 = new Coursectl();
				a1.setC_Id(c_Id);
				a1.setM_Id(dessertId);
				cts.add(a1);
			}
		} catch (Exception e) {}

		// デバッグ用にコンソールに出力します。
		// Outputs to the console for debugging purposes.
		for (Coursectl local : cts) {
			System.out.println(local);
		}

		return;
	}


}
