package controller;

import java.io.IOException;

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
import model.TableLoc;

/**
 * 予約の登録・更新・削除処理を制御するサーブレット。
 * リクエストの'mode'パラメータに基づいて適切な処理（INSERT/UPDATE/DELETE）を実行します。
 * Servlet that controls the insertion, update, and deletion processes for reservations.
 * It executes the appropriate process (INSERT/UPDATE/DELETE) based on the 'mode' parameter in the request.
 *
 * @author モッタハイメ
 * @version 1.0.0
 * @see javax.servlet.http.HttpServlet
 */
@WebServlet("/reserve/ReserveOperationSvl")
public class ReserveOperationSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final int INSERT = 11;
	public static final int UPDATE = 12;
	public static final int DELETE = 13;
       
    /**
     * コンストラクタ。
     * Constructor.
     * @see HttpServlet#HttpServlet()
     */
    public ReserveOperationSvl() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * HTTP POSTリクエストを処理します。
     * 'mode'に応じて、予約の登録、更新、または削除を実行します。
	 * Processes HTTP POST requests.
     * It executes reservation insertion, update, or deletion based on the 'mode'.
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
		Reserve rsv = null;

		// ログイン状態を確認
		// Check for login status
		if (session == null || session.getAttribute("userInfo") == null) {
			url = "/home.jsp";
			message = "このページにアクセスするにはログインが必要です。"; // You must be logged in to access this page.
		} else {
			
			int mode, rsvId, rsvYy, rsvMm, rsvDd, rsvHh, rsvMi, usrId, person, courseId, tableId;
			
			try {				
				mode = Integer.parseInt(request.getParameter("mode"));				
			} catch (NumberFormatException e) {
				// 取得に失敗した場合、デフォルト値を設定します。
				// If retrieval fails, set a default value.
				mode = 0;
			}	
			
			System.out.println("ReserveOperationSvl->doPost->mode:"+mode);
			System.out.println("rsvYy:"+request.getParameter("rsvYy"));
			System.out.println("rsvMm:"+request.getParameter("rsvMm"));
			System.out.println("rsvDd:"+request.getParameter("rsvDd"));
			System.out.println("rsvHh:"+request.getParameter("rsvHh"));
			System.out.println("rsvMi:"+request.getParameter("rsvMi"));
			System.out.println("person:"+request.getParameter("person"));
			System.out.println("usrId:"+request.getParameter("usrId"));
			
			try {				
				rsvYy = Integer.parseInt(request.getParameter("rsvYy"));
				rsvMm = Integer.parseInt(request.getParameter("rsvMm"));
				rsvDd = Integer.parseInt(request.getParameter("rsvDd"));
				rsvHh = Integer.parseInt(request.getParameter("rsvHh"));
				rsvMi = Integer.parseInt(request.getParameter("rsvMi"));
				person = Integer.parseInt(request.getParameter("person"));
				usrId = Integer.parseInt(request.getParameter("usrId"));
			} catch (NumberFormatException e) {
				// 取得に失敗した場合、デフォルト値を設定します。
				// If retrieval fails, set a default value.
				rsvId = rsvYy = rsvMm = rsvDd = rsvHh = rsvMi = usrId = person = tableId = 0;
			}
			try {
				courseId = Integer.parseInt(request.getParameter("courseId"));
				System.out.println("ReserveOperationSvl->courseId:"+courseId);							
			} catch (NumberFormatException e) {
				// 取得に失敗した場合、デフォルト値を設定します。
				// If retrieval fails, set a default value.
				courseId = 0;
			}	           
			
			
			switch (mode) {
				case INSERT:
					// 登録処理
					// Insertion process
					rsv = new Reserve();
					rsv.setRsvYy(rsvYy);
					rsv.setRsvMm(rsvMm);
					rsv.setRsvDd(rsvDd);
					rsv.setRsvHh(rsvHh);
					rsv.setRsvMi(rsvMi);
					rsv.setPerson(person);
					rsv.setCourseId(courseId);
					rsv.setUsrId(usrId);
					
					try {						
						Course localCourse = Course.getCourse(courseId);
						rsv.setCourseName(localCourse.getCourseName());
						
						// Convert the form data into a proper date and time object
					    String rsvDate = rsvYy + "-" + rsvMm + "-" + rsvDd;
					    String rsvTime = rsvHh + ":" + rsvMi + ":00";						
						TableLoc table = Reserve.insertChk(rsvDate+" "+rsvTime, person);
						
						System.out.println("ReserveOperationSvl->INSERT->table:"+table);
						if(table != null) {
							rsv.setTableId(table.getTableId());
							rsv.setTableName(table.getTableName());
							Reserve.insert(rsv);
							request.setAttribute("rsvId", rsv.getRsvId());
							url = "../reserveCompletion.jsp";
						}else {
							throw new IdealException(IdealException.ERR_NO_NOT_VACANCY_EXCEPTION);
						}
						
					} catch (IdealException e) {
						message = e.getMsg();
				        // Set the message and the 'rsv' object on the request
				        request.setAttribute("msg", message);
				        request.setAttribute("reserve", rsv);
				        
				        rd = request.getRequestDispatcher("/reserve/ReserveInsertSvl");
				        rd.forward(request, response);
				        return; 
					}
					request.setAttribute("reserve", rsv);
					System.out.println("ReserveOperationSvl->INSERT->rsv:"+rsv);
					break;
				case DELETE:
					rsvId = Integer.parseInt(request.getParameter("rsvId"));
					// 削除処理
					// Deletion process
					try {
						rsv = Reserve.getReserve(rsvId);
						Reserve.delete(rsv);
					} catch (IdealException e) {
						message = e.getMsg();
				        // Set the message and the 'rsv' object on the request
				        request.setAttribute("msg", message);
				        request.setAttribute("reserve", rsv);
				        
				        rd = request.getRequestDispatcher("/reserve/ReserveDeleteSvl");
				        rd.forward(request, response);
				        return; 
					}						
					url = "/reserve/ReserveListSvl";
					break;				
										
				case UPDATE:											
					rsv = new Reserve();
					try {
						Course localCourse = Course.getCourse(courseId);
						rsv.setCourseName(localCourse.getCourseName());
					} catch (IdealException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}						
					
					System.out.println("ReserveOperationSvl->Update->rsvId:"+request.getParameter("rsvId"));
					rsvId = Integer.parseInt(request.getParameter("rsvId"));
					// 更新処理
					// Update process
					rsv = new Reserve();
					rsv.setRsvId(rsvId);
					rsv.setRsvYy(rsvYy);
					rsv.setRsvMm(rsvMm);
					rsv.setRsvDd(rsvDd);
					rsv.setRsvHh(rsvHh);
					rsv.setRsvMi(rsvMi);
					rsv.setPerson(person);
					rsv.setCourseId(courseId);
					rsv.setUsrId(usrId);
					
					try {							
						// Convert the form data into a proper date and time object
					    String rsvDate = rsvYy + "-" + rsvMm + "-" + rsvDd;
					    String rsvTime = rsvHh + ":" + rsvMi + ":00";						
						TableLoc table = Reserve.updateChk(rsvId,rsvDate+" "+rsvTime, person);
						
						if(table != null) {
							rsv.setTableId(table.getTableId());
							rsv.setTableName(table.getTableName());
							Reserve.update(rsv);
							request.setAttribute("rsvId", rsv.getRsvId());
							url = "../reserveCompletion.jsp";
						}else {
							throw new IdealException(IdealException.ERR_NO_NOT_VACANCY_EXCEPTION);
						}
						
					} catch (IdealException e) {
						message = e.getMsg();
				        // Set the message and the 'rsv' object on the request
				        request.setAttribute("msg", message);
				        request.setAttribute("reserve", rsv);
				        
				        rd = request.getRequestDispatcher("/reserve/ReserveUpdateSvl");
				        rd.forward(request, response);
				        return; 
					}
					request.setAttribute("reserve", rsv);
					break;
				default:
					// その他のモードの場合
					// For other modes
					break;
				}	
		}

		// メッセージをリクエストスコープに格納し、指定されたURLにフォワード
		// Store the message in the request scope and forward to the specified URL
		request.setAttribute("msg", message);
		rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}
