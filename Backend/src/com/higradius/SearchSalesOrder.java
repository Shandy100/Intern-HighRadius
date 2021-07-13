package com.higradius;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class SearchSalesOrder
 */
@WebServlet("/SearchSalesOrder")
public class SearchSalesOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchSalesOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		int NO_OF_ROWS_TO_GET = 12;
		
		try {
			Connection conn = GetConnection.connectToDB();
			
			String searchKeyword = request.getParameter("searchKeyword");
			String page = request.getParameter("page");
			
			Statement st = conn.createStatement();
			String sql_statement = "SELECT * FROM invoice_details WHERE doc_id LIKE '" + searchKeyword + "%' LIMIT " + page +"," + NO_OF_ROWS_TO_GET;
			ResultSet rs = st.executeQuery(sql_statement);
			
			ArrayList<InvoiceDetails> data = new ArrayList<>();
			while(rs.next()) {
				InvoiceDetails inv = new InvoiceDetails();
				inv.setNameCustomer(rs.getString("name_customer"));
				inv.setCustNumber(rs.getString("cust_number"));
				inv.setDueInDate(rs.getString("due_in_date"));
				inv.setTotalOpenAmount(rs.getFloat("total_open_amount"));
				
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
