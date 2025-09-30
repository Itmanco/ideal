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
 * MenuUpdateSvl サーブレット
 *
 * このサーブレットは、メニューまたはコースの更新画面を表示するために必要なデータを準備します。
 * - 管理者のログイン確認
 * - typeId と menuId をリクエストから取得
 * - typeId が 100 の場合 → コース更新処理
 * - typeId が 100 以外の場合 → 通常メニュー更新処理
 * - 取得したデータをリクエスト属性に格納し、対応する JSP へフォワード
 *
 * This servlet prepares data for updating a menu or course.
 * - Checks if admin is logged in
 * - Retrieves typeId and menuId from request
 * - If typeId == 100 → course update mode
 * - Else → normal menu update mode
 * - Sets attributes and forwards to the correct JSP
 *
 * @author モッタハイメ
 */
@WebServlet("/menu/MenuUpdateSvl")
public class MenuUpdateSvl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * サーブレットのインスタンスを初期化します。
     *
     * Constructor: initializes the servlet instance.
     */
    public MenuUpdateSvl() {
        super();
    }

    /**
     * GET リクエストを処理します。
     * 内部的に doPost を呼び出します。
     *
     * Handles GET requests.
     * Internally delegates to doPost.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * POST リクエストを処理します。
     *
     * - ログインチェック  
     * - typeId と menuId の取得  
     * - typeId = 100 → コース更新用データを準備  
     * - typeId ≠ 100 → メニュー更新用データを準備  
     * - 結果を JSP にフォワード  
     *
     * Handles POST requests.
     *
     * - Checks login session  
     * - Retrieves typeId and menuId  
     * - If typeId = 100 → prepare course update data  
     * - Else → prepare menu update data  
     * - Forwards to the correct JSP  
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd = null;
        String url = "";
        String message = "";

        // リクエスト・レスポンスの文字コード設定
        // Set character encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // セッション取得（存在しなければ null）
        // Get existing session (null if none exists)
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("adminInfo") == null) {
            // 管理者が未ログインの場合 / Redirect to home if not logged in
            url = "/home.jsp";
            message = "このページにアクセスするにはログインが必要です。";
            // "You must be logged in to access this page."
        } else {
            int typeId, menuId;

            // typeId と menuId の取得 / Retrieve typeId and menuId
            try {
                typeId = Integer.parseInt(request.getParameter("typeId"));
                menuId = Integer.parseInt(request.getParameter("menuId"));
            } catch (NumberFormatException e) {
                typeId = 100; // default to course
                menuId = -1;  // invalid menuId
            }

            System.out.println("MenuUpdateSvl -> typeId:" + typeId + ", menuId:" + menuId);

            try {
                if (typeId == 100) {
                    // ===== コース更新モード / Course update mode =====
                    ArrayList<ArrayList<Menu>> typeMenuList = new ArrayList<>();
                    for (int value : Course.COURSE_MENU_TYPE_ID) {
                        typeMenuList.add(Menu.getMenu(value));
                    }
                    request.setAttribute("typeMuneList", typeMenuList);

                    // コース情報を取得 / Fetch course info
                    ArrayList<Course> localOneCourse = Course.getOneCourse(menuId);
                    if (localOneCourse == null || localOneCourse.size() < 1) {
                        localOneCourse = new ArrayList<Course>();
                        localOneCourse.add(Course.getCourse(menuId));
                    }

                    request.setAttribute("oneCourse", localOneCourse);
                    url = "../courseUpdate.jsp";
                } else {
                    // ===== メニュー更新モード / Menu update mode =====
                    request.setAttribute("mType", MenuType.getAllNoCourseType());
                    request.setAttribute("oneMenu", Menu.getOneMenu(menuId, typeId));
                    request.setAttribute("typeId", typeId);
                    url = "../menuUpdate.jsp";
                }
            } catch (IdealException e) {
                message = e.getMsg();
            }
        }

        // メッセージを設定して JSP へフォワード
        // Set message and forward to JSP
        request.setAttribute("msg", message);
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
