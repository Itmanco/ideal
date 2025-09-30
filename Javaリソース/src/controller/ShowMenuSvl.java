package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Course;
import model.Menu;
import model.MenuType;
import model.IdealException;

/**
 * ShowMenuSvl サーブレット
 *
 * このサーブレットは、メニューおよびコース一覧を表示するための処理を行います。  
 * - メニュータイプ一覧の取得  
 * - コース一覧の取得  
 * - メニュー一覧の取得  
 * - 結果を JSP (showMenu.jsp) に渡す  
 *
 * This servlet handles the display of all menus and courses.  
 * - Retrieves menu type list  
 * - Retrieves course list  
 * - Retrieves menu list  
 * - Forwards results to JSP (showMenu.jsp)  
 *
 * @author モッタハイメ
 */
@WebServlet("/menu/ShowMenuSvl")
public class ShowMenuSvl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * サーブレットのインスタンスを初期化します。
     *
     * Constructor: Initializes the servlet instance.
     */
    public ShowMenuSvl() {
        super();
    }

    /**
     * GET リクエストを処理します。
     *
     * - メニュータイプ、コース、メニューの一覧を取得  
     * - リクエスト属性に格納  
     * - showMenu.jsp へフォワード  
     *
     * Handles GET requests.
     *
     * - Retrieves menu types, courses, and menus  
     * - Sets them as request attributes  
     * - Forwards to showMenu.jsp  
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ShowMenuSvl > doGet");

        RequestDispatcher rd = null;
        String url = "";
        String message = null;

        // リクエスト・レスポンスの文字コード設定
        // Set encoding for request and response
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            // メニュータイプ一覧の取得 / Get all menu types
            request.setAttribute("mType", MenuType.getAllType());

            // コース一覧の取得 / Get all courses
            request.setAttribute("courses", Course.getCourseList());

            // メニュー一覧の取得 / Get all menus
            request.setAttribute("menus", Menu.getMenuList());

        } catch (IdealException e) {
            message = e.getMsg();
        }

        // JSP にフォワード / Forward to JSP
        url = "../showMenu.jsp";
        request.setAttribute("msg", message);
        rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    /**
     * POST リクエストを処理します。
     * 内部的に doGet を呼び出します。
     *
     * Handles POST requests.
     * Internally delegates to doGet.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ShowMenuSvl > doPost");
        doGet(request, response);
    }
}
