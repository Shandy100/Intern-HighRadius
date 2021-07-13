package com.higradius;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class SendData
 */
@WebServlet("/SendData")
public class SendData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Initialize the database 
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		int NO_OF_ROWS_TO_GET = 12;
		
		try {
			Connection conn = GetConnection.connectToDB();
			
			String pageInURL = request.getParameter("page");
			int page = Integer.parseInt(pageInURL) * NO_OF_ROWS_TO_GET;
			
			Statement st = conn.createStatement();
			String sql_statement = "SELECT cust_number, name_customer, doc_id, total_open_amount, due_in_date, clear_date, notes from invoice_details ORDER BY doc_id ASC LIMIT " + page + ", " + NO_OF_ROWS_TO_GET;
			ResultSet rs = st.executeQuery(sql_statement);
			
			ArrayList<InvoiceDetails> data = new ArrayList<>();
			while(rs.next()) {
				InvoiceDetails inv = new InvoiceDetails();
				inv.setNameCustomer(rs.getString("name_customer"));
				inv.setCustNumber(rs.getString("cust_number"));
				inv.setClearDate(rs.getString("clear_date") == null ? "" : rs.getString("clear_date").substring(0, 10));
				inv.setDueInDate(rs.getString("due_in_date"));
				inv.setTotalOpenAmount(rs.getFloat("total_open_amount"));
				inv.setNotes(rs.getString("notes"));
				
				data.add(inv);
			}
			
			Gson gson = new GsonBuilder().serializeNulls().create();
			String invoices = gson.toJson(data);
			out.print(invoices);
			response.setStatus(200);
			out.flush();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
