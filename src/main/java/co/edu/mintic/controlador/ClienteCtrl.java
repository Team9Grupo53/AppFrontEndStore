package co.edu.mintic.controlador;

import java.util.List;

import co.edu.mintic.dto.TokenDTO;
import co.edu.mintic.mediador.ClienteMed;





public class ClienteCtrl {

	private ClienteMed med;
	
	public ClienteCtrl() {
		super();
		med = new ClienteMed();
	}
	
	public void crear(Object obj) throws Exception{
		med.crear(obj);
	}
	
	public List<Object> listar(TokenDTO tk) throws Exception{
    	return med.listar(tk);
    }
	
	public Object buscarById(int id,TokenDTO tk) throws Exception{
    	return med.buscarById(id,tk);
    }
	
	public void actualizar(Object obj) throws Exception{
		med.actualizar(obj);
	}
	public void eliminar(Object obj) throws Exception{
		med.eliminar(obj);
	}
	
}
