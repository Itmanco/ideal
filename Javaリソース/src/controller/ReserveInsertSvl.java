package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Course;
import model.IdealException;

/**
 * 予約登録画面表示用サーブレット。
 * 現在の日付情報や、予約に必要な選択肢（年、月、時間、人数、コースリスト）を準備し、
 * 登録画面（reserveInsert.jsp）へフォワードします。
 * Servlet for displaying the reservation insertion screen.
 * It prepares necessary selection options (year, month, time, people, course list) 
 * and forwards to the insertion screen (reserveInsert.jsp).
 *
 * @author モッタハイメ
 * @version 1.0.0
 * @see javax.servlet.http.HttpServlet
 */
@WebServlet("/reserve/ReserveInsertSvl")
public class ReserveInsertSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * コンストラクタ。
     * Constructor.
     * @see HttpServlet#HttpServlet()
     */
    public ReserveInsertSvl() {
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
     * 予約登録フォームに必要なデータを準備し、JSPへフォワードします。
	 * Processes HTTP POST requests.
     * It prepares the necessary data for the reservation insertion form and forwards to the JSP.
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
            // 現在の日付情報を取得
            // Get current date information
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            int currentMonth = currentDate.getMonthValue();
            int currentDay = currentDate.getDayOfMonth();

            // 1. 選択肢として年（今年と翌年）を取得
            // 1. Get years (current and next) as options
            ArrayList<Integer> years = new ArrayList<>();
            years.add(currentYear);
            years.add(currentYear + 1);

            // 2. 月と名称を取得
            // 2. Get months and their names
            // LinkedHashMapを使用して挿入順序を保持
            // Use a LinkedHashMap to maintain insertion order
            Map<Integer, String> monthsMap = new LinkedHashMap<>();
            // 日本語の月名配列
            // Japanese month name array
            String[] japaneseMonthNames = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
            
            // Current month to start the options from
            // 選択肢を開始する現在の月
            Month startMonth = LocalDate.now().getMonth();
            
            // 現在の月から年末までの月を追加
            // Add months from the current month until the end of the year
            for (Month month : Month.values()) {
                if (month.getValue() >= startMonth.getValue()) {
                    monthsMap.put(month.getValue(), japaneseMonthNames[month.getValue()]);
                }
            }

            // 3. データ（選択肢、現在日）をリクエスト属性に設定
            // 3. Set data (options, current date) as request attributes
            request.setAttribute("years", years);
            request.setAttribute("monthsMap", monthsMap); 
            request.setAttribute("currentYear", currentYear);
            request.setAttribute("currentMonth", currentMonth);
            request.setAttribute("currentDay", currentDay);
            
            // 時刻と人数（固定値）を設定
            // Set time and people (fixed values)
            String[] hours = {"18", "19", "20", "21"};
            String[] minutes = {"00", "15", "30", "45"};
            String[] people = {"1", "2", "3", "4", "5", "6"};
            
            request.setAttribute("hours", hours);
            request.setAttribute("minutes", minutes);
            request.setAttribute("people", people);
			
            
			try {				
				// コースリストを取得
				// Get course list
				request.setAttribute("courseList", Course.getOneCourseList());
				
				// 登録画面へ遷移
				// Transition to the insertion screen
				url = "../reserveInsert.jsp";
			} catch (IdealException e) {
				// コースリスト取得時に例外が発生した場合
				// If an exception occurs while retrieving the course list
				
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