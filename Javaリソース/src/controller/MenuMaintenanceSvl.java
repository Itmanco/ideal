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
 * MenuMaintenanceSvl サーブレット
 * 
 * このサーブレットは、メニューのメンテナンス画面を表示する役割を持ちます。
 * - ログイン確認
 * - メニュータイプの取得
 * - 選択されたメニュー一覧の取得
 * 
 * This servlet handles displaying the menu maintenance page.
 * - Verifies login
 * - Retrieves menu types
 * - Retrieves the list of menus for the selected type
 * 
 * @author モッタハイメ
 */
@WebServlet("/menu/MenuMaintenanceSvl")
public class MenuMaintenanceSvl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * 
     * サーブレットのインスタンスを初期化します。
     * 
     * Constructor that initializes the servlet instance.
     */
    public MenuMaintenanceSvl() {
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
     * - typeId に基づき、メニュー情報を取得  
     * - メンテナンス画面またはエラー画面にフォワード  
     * 
     * Handles POST requests.
     * 
     * - Checks if the user is logged in  
     * - Retrieves menu information based on typeId  
     * - Forwards to maintenance page or error page  
     * 
     * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = null;
        String url = null;
        String message = null;

        // リクエストとレスポンスの文字コードを設定
        // Set request and response character encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // 既存セッションを取得（存在しなければ null）
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
                // typeId をリクエストから取得
                // Retrieve typeId from request
                typeId = Integer.parseInt(request.getParameter("typeId"));
            } catch (NumberFormatException e) {
                // 不正な場合はデフォルト値 100
                // Default to 100 if invalid
                typeId = 100;
            }

            try {
                // ===== メニュータイプとメニュー情報の取得 =====
                // ===== Retrieve menu types and menu list =====
                request.setAttribute("mType", MenuType.getAllType());
                request.setAttribute("menu", Menu.getMenu(typeId));
                url = "/menuMaintenance.jsp";
            } catch (IdealException e) {
                // エラー発生時はメッセージを設定し、管理画面へ遷移
                // On error, set message and forward to admin index
                message = e.getMsg();
                url = "/adminIndex.jsp";
            }
        }

        // メッセージと遷移先をリクエストに設定し、フォワード
        // Set message and forward to destination
        request.setAttribute("msg", message);
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

}
