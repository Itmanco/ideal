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
 * MenuInsertSvl サーブレット
 * 
 * このサーブレットは、新しいメニューまたはコースの追加処理を行います。
 * 管理者がログインしていない場合、ホームページにリダイレクトされます。
 * 
 * Servlet that handles inserting a new menu or course.
 * If the administrator is not logged in, it redirects to the home page.
 * 
 * @author モッタハイメ
 */
@WebServlet("/menu/MenuInsertSvl")
public class MenuInsertSvl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * 
     * サーブレットのインスタンスを初期化します。
     * 
     * Constructor that initializes the servlet instance.
     */
    public MenuInsertSvl() {
        super();
    }

    /**
     * GETリクエストを処理します。
     * 内部的に doPost メソッドを呼び出します。
     * 
     * Handles GET requests.
     * Internally delegates to the doPost method.
     * 
     * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * POSTリクエストを処理します。
     * 
     * - ログインチェックを行う  
     * - typeId が 100 の場合はコース追加処理を実行  
     * - それ以外の場合はメニュー追加処理を実行  
     * 
     * Handles POST requests.
     * 
     * - Checks if the user is logged in  
     * - If typeId == 100, executes course insertion process  
     * - Otherwise, executes menu insertion process  
     * 
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
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
            int typeId;

            try {
                // typeId をパラメータから取得
                // Retrieve typeId from request parameter
                typeId = Integer.parseInt(request.getParameter("typeId"));
            } catch (NumberFormatException e) {
                // 不正な場合はデフォルト値 100
                // Default value if invalid
                typeId = 100;
            }

            try {
                if (typeId == 100) {
                    // ===== コース追加処理 =====
                    // ===== Course insertion process =====
                    ArrayList<ArrayList<Menu>> typeMenuList = new ArrayList<>();
                    for (int value : Course.COURSE_MENU_TYPE_ID) {
                        typeMenuList.add(Menu.getMenu(value));
                    }
                    request.setAttribute("typeMenuList", typeMenuList);
                    url = "../courseInsert.jsp";
                } else {
                    // ===== メニュー追加処理 =====
                    // ===== Menu insertion process =====
                    // request.setAttribute("mType", MenuType.getAllType());
                    request.setAttribute("mType", MenuType.getAllNoCourseType());
                    request.setAttribute("typeId", typeId);
                    url = "../menuInsert.jsp";
                }
            } catch (IdealException e) {
                // エラー処理: 例外発生時にメッセージを設定
                // Error handling: set error message if exception occurs
                message = e.getMsg();
                try {
                    request.setAttribute("mType", MenuType.getAllType());
                } catch (IdealException e1) {
                    message += e.getMsg();
                }
                request.setAttribute("typeId", typeId);
                url = "../menuMaintenance.jsp";
            }
        }

        // メッセージと遷移先をリクエストにセットし、フォワード
        // Set message and forward to destination
        request.setAttribute("msg", message);
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

}
