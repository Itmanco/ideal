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
import model.Reserve;

/**
 * Servlet implementation class ReserveListSvl
 */
@WebServlet("/reserve/ReserveUpdateSvl")
public class ReserveUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReserveUpdateSvl() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		String url = "";
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String message = null;
		HttpSession session = request.getSession(false);
		System.out.println("ReserveUpdateSvl->doPost");
		// ログイン状態を確認
		// Check for login status
		if (session == null || session.getAttribute("userInfo") == null) {
			url = "/home.jsp";
			message = "このページにアクセスするにはログインが必要です。"; // You must be logged in to access this page.
		} else {
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            int currentMonth = currentDate.getMonthValue();
            int currentDay = currentDate.getDayOfMonth();

            // 1. Get years (current and next)
            ArrayList<Integer> years = new ArrayList<>();
            years.add(currentYear);
            years.add(currentYear + 1);

            // 2. Get months and their names
            // Use a LinkedHashMap to maintain insertion order
            Map<Integer, String> monthsMap = new LinkedHashMap<>();
            //String[] japaneseMonthNames = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
            String[] japaneseMonthNames = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                        
            // Only add remaining months of the current year
            Month startMonth = LocalDate.now().getMonth();
            for (Month month : Month.values()) {
                if (month.getValue() >= startMonth.getValue()) {
                    monthsMap.put(month.getValue(), japaneseMonthNames[month.getValue()]);
                }
            }

            // 3. Set data as request attributes
            request.setAttribute("years", years);
            request.setAttribute("monthsMap", monthsMap);
            request.setAttribute("currentYear", currentYear);
            request.setAttribute("currentMonth", currentMonth);
            request.setAttribute("currentDay", currentDay);
            
            String[] hours = {"18", "19", "20", "21"};
            String[] minutes = {"00", "15", "30", "45"};
            String[] people = {"1", "2", "3", "4", "5", "6"};
            
            request.setAttribute("hours", hours);
            request.setAttribute("minutes", minutes);
            request.setAttribute("people", people);
            
            int rsvId;
            try {
            	rsvId = Integer.parseInt(request.getParameter("rsvId"));
			} catch (NumberFormatException e) {
				// 取得に失敗した場合、デフォルト値を設定します。
				// If retrieval fails, set a default value.
				rsvId = -1;
			}
			
			try {
				Reserve rsv = Reserve.getReserve(rsvId);
				System.out.println("ReserveUpdateSvl->reserve:"+rsv);
				request.setAttribute("reserve", rsv);
				request.setAttribute("courseList", Course.getOneCourseList());
				url = "../reserveUpdate.jsp";
			} catch (IdealException e) {
				// 例外が発生した場合、エラーメッセージを取得してログインページへ遷移
				// If an exception occurs, get the error message and transition to the login page
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
