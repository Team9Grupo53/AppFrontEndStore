package co.edu.mintic.controlador;

import java.util.List;

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
	
	public List<Object> listar() throws Exception{
    	return med.listar();
    }
	
	public Object buscarById(int id) throws Exception{
    	return med.buscarById(id);
    }
	
	public void actualizar(Object obj) throws Exception{
		med.actualizar(obj);
	}
	public void eliminar(Object obj) throws Exception{
		med.eliminar(obj);
	}
	
}
