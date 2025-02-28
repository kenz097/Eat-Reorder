package servlets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import interfaces.GestoreUtenteDAO;
import model.CheckFormato;
import model.bean.AccountAzienda_Bean;
import model.bean.Prodotto_Bean;
import model.dao.GestoreUtenteDAOImpl;

/**
 * Servlet implementation class DoModificaProdotto
 */
@WebServlet("/DoModificaProdotto")
public class DoModificaProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GestoreUtenteDAO dao = new GestoreUtenteDAOImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DoModificaProdotto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// prendo l'account azienda dalla sessione
		HttpSession session = request.getSession();

		AccountAzienda_Bean azienda = null;
		try {
			azienda = (AccountAzienda_Bean) session.getAttribute("utente");	
		} catch(Exception e) {
			System.err.println("ERROR DETECTED");
			e.printStackTrace();
			response.sendRedirect("ErrorPage.html");
			return;
		}
		
		// se non esiste l'account azienda rimando alla pagina di login
		if (azienda == null) {
			response.sendRedirect("Login.jsp");
			return;
		}
		
		//prendo i parametri dalla richiesta
		Long id = Long.parseLong(request.getParameter("id"));
		
		Float costo= null;
		String nome = request.getParameter("nome");
		String inCost = request.getParameter("prezzo").replace(",", ".");

		try {
			costo = Float.parseFloat(inCost);
		} catch (NumberFormatException e) {
			costo = -1F;
		}
		
		String inPath = request.getParameter("img_path");
		URL path = null;
		try {
			path = new URL(inPath);
		} catch (MalformedURLException e) {
			path= new URL("http://");
		}
		
		String descrizione = request.getParameter("descrizione");

		if (CheckFormato.checkProdotto(nome, path, descrizione, costo)) {
			//prendo il prodotto con il codice id dal listino dell'azienda
			// creo un nuovo prodotto con i dati aggiornati
			Prodotto_Bean newProdotto = new Prodotto_Bean();
			newProdotto.setCodice(id);
			newProdotto.setNome(nome);
			newProdotto.setAzienda(azienda);
			newProdotto.setDescrizione(descrizione);
			newProdotto.setImmagine(path);
			newProdotto.setPrezzo(costo);
			
			Prodotto_Bean prodInListino = azienda.dammiProdotto(id);

			try {
				dao.aggiornaProdotto(azienda, newProdotto);
				prodInListino.modificaDati(newProdotto);
				request.getRequestDispatcher("Listino.jsp").forward(request, response);
				return;
			} catch (SQLException e) {
				System.err.println("ERROR DETECTED");
				e.printStackTrace();
				response.sendRedirect("ErrorPage.html");
				return;
			}

		} else {
			// errore con il formato dei parametri
			String errmessage = ("I dati inseriti nella modifica del prodotto non sono corretti");
			// Redirection to an error page
			request.setAttribute("msg_error", errmessage);
			request.getRequestDispatcher("Listino.jsp").forward(request, response);
			return;
		}
	}

	public void setGestore(GestoreUtenteDAO dao) {
		this.dao=dao;	
	}

}
