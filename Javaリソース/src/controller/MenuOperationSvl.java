package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Menu;
import model.MenuType;
import model.IdealException;

/**
 * MenuOperationSvl サーブレット
 *
 * このサーブレットは、メニューの登録・更新・削除処理を行います。
 * - 管理者ログインの確認
 * - リクエストから操作モード（INSERT, UPDATE, DELETE）を取得
 * - Menu モデルを呼び出し、データベース操作を実行
 * - 結果に応じて JSP にフォワード
 *
 * This servlet handles menu operations:
 * - Verifies admin login
 * - Reads the requested operation mode (INSERT, UPDATE, DELETE)
 * - Calls the Menu model to perform DB operations
 * - Forwards to the appropriate JSP depending on the result
 *
 * @author モッタハイメ
 */
@WebServlet("/menu/MenuOperationSvl")
public class MenuOperationSvl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 操作モード定義 / Operation mode constants
    public static final int INSERT = 11;
    public static final int UPDATE = 12;
    public static final int DELETE = 13;

    /**
     * コンストラクタ
     * サーブレットのインスタンスを初期化します。
     *
     * Constructor: initializes the servlet instance.
     */
    public MenuOperationSvl() {
        super();
    }

    /**
     * GET リクエストを処理します。
     * 内部的に doPost を呼び出します。
     *
     * Handles GET requests.
     * Delegates internally to doPost.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * POST リクエストを処理します。
     *
     * - ログインチェック  
     * - モード（INSERT / UPDATE / DELETE）の判定  
     * - DB 操作実行（Menu.updateMenu() を使用）  
     * - 処理結果を JSP へフォワード  
     *
     * Handles POST requests.
     *
     * - Checks login session  
     * - Determines mode (INSERT / UPDATE / DELETE)  
     * - Executes DB operation using Menu.updateMenu()  
     * - Forwards result to the appropriate JSP  
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = null;
        String url = "";
        String message = "";

        // リクエスト・レスポンスの文字コードを設定
        // Set character encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // 既存セッションを取得（存在しなければ null）
        // Get existing session (null if none exists)
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminInfo") == null) {
            // 管理者が未ログインの場合はホームへ
            // Redirect to home if admin not logged in
            url = "/home.jsp";
            message = "このページにアクセスするにはログインが必要です。";
            // "You must be logged in to access this page."
        } else {
            int typeId;
            int mode;
            int result = -1;
            Menu m;

            // モードの取得 / Retrieve operation mode
            try {
                mode = Integer.parseInt(request.getParameter("mode"));
                System.out.println("---> Mode: " + mode);
            } catch (NumberFormatException e) {
                mode = 0;
            }

            // メニュータイプIDの取得 / Retrieve menu typeId
            try {
                typeId = Integer.parseInt(request.getParameter("typeId"));
                System.out.println("---> typeId: " + typeId);
            } catch (NumberFormatException e) {
                typeId = 100; // デフォルト値 Default value
            }

            switch (mode) {
                case INSERT:
                    // ===== 新規メニュー登録 / Insert new menu =====
                    m = new Menu();
                    m.setMenuName(request.getParameter("menuName"));
                    m.setPrice(Integer.parseInt(request.getParameter("price")));
                    m.setOrderFlg(Integer.parseInt(request.getParameter("orderFlg")));
                    m.setDetail(request.getParameter("detail"));
                    m.setTypeId(typeId);

                    System.out.println("MenuOperationSvl -> INSERT:");
                    System.out.println(m);

                    try {
                        Menu.updateMenu(m, mode);
                        request.setAttribute("menu", Menu.getMenu(typeId));
                        request.setAttribute("mType", MenuType.getAllType());
                    } catch (IdealException e) {
                        message = e.getMsg();
                    }

                    url = "../menuMaintenance.jsp";
                    break;

                case DELETE:
                    // ===== メニュー削除 / Delete menu =====
                    m = new Menu();
                    m.setMenuId(Integer.parseInt(request.getParameter("menuId")));
                    m.setTypeId(Integer.parseInt(request.getParameter("typeId")));

                    try {
                        System.out.println("MenuController - Delete - before");
                        result = Menu.updateMenu(m, mode);
                        System.out.println("MenuController - Delete - result:" + result);

                        // 削除後のメンテ画面へ / Forward to maintenance page
                        request.setAttribute("menu", Menu.getMenu(typeId));
                        request.setAttribute("mType", MenuType.getAllType());
                        url = "../menuMaintenance.jsp";
                        
                    } catch (IdealException e) {
                        message = e.getMsg();
                        url = "../menuDelete.jsp";
                        try {
                        	request.setAttribute("oneMenu", Menu.getOneMenu(m.getMenuId(), m.getTypeId()));
                        }catch(IdealException e2) {message = e2.getMsg();}
                    }
                    break;

                case UPDATE:
                    // ===== メニュー更新 / Update menu =====
                    m = new Menu();
                    m.setMenuId(Integer.parseInt(request.getParameter("menuId")));
                    m.setMenuName(request.getParameter("menuName"));
                    m.setDetail(request.getParameter("detail"));
                    m.setOrderFlg(Integer.parseInt(request.getParameter("orderFlg")));
                    m.setPrice(Integer.parseInt(request.getParameter("price")));
                    m.setTypeId(Integer.parseInt(request.getParameter("typeId")));

                    try {
                        Menu.updateMenu(m, mode);
                        Menu updatedMenu = Menu.getOneMenu(m.getMenuId(), m.getTypeId());
                        request.setAttribute("oneMenu", updatedMenu);
                        request.setAttribute("mType", MenuType.getAllType());
                        request.setAttribute("menu", Menu.getMenu(typeId));
                        url = "../menuMaintenance.jsp";
                    } catch (IdealException e) {
                        message = e.getMsg();
                        url = "../menuUpdate.jsp";
                    }
                    break;

                default:
                    // 不正なモードは何もしない / Do nothing for invalid mode
                    break;
            }
        }

        // メッセージを設定して JSP にフォワード
        // Set message and forward to JSP
        request.setAttribute("msg", message);
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
