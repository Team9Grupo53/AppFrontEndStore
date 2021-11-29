package co.edu.mintic.controlador;

import java.util.List;

import co.edu.mintic.mediador.VentaMed;

public class VentaCtrl {

	private VentaMed med;

	public VentaCtrl() {
		super();
		med = new VentaMed();
	}

	public void crear(Object obj) throws Exception {
		med.crear(obj);
	}

	public List<Object> listar() throws Exception {
		return med.listar();
	}
	
	public List<Object> listarReporte() throws Exception {
		return med.listarReporte();
	}

	public Object buscarById(int id) throws Exception {
		return med.buscarById(id);
	}
	
	public Long obtenerConsecutivo() throws Exception {
		return med.obtenerConsecutivo();
	}

	public void actualizar(Object obj) throws Exception {
		med.actualizar(obj);
	}

	public void eliminar(Object obj) throws Exception {
		med.eliminar(obj);
	}
}
