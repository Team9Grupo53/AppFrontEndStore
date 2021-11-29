package co.edu.mintic.controlador;

import java.util.List;

import co.edu.mintic.mediador.UsuarioMed;

public class UsuarioCtrl {
	
	private UsuarioMed med;

	public UsuarioCtrl() {
		super();		
		med = new UsuarioMed();
	}
	
	/**
     * Medoto generico de creacion del Objeto
     *
     * @param Objeto a crear
     * @throws java.lang.Exception
     */
	public void crear(Object obj) throws Exception{
		med.crear(obj);
	}

    /**
     * Medoto generico de listar
     *
     * @return listado 
     * @throws java.lang.Exception
     */
    public List<Object> listar() throws Exception{
    	return med.listar();
    }

    /**
     * Medoto generico para consultar por id
     *
     * @param id filtro de busqueda
     * @return objeto encontrado
     * @throws java.lang.Exception
     */
    public Object buscarById(int id) throws Exception{
    	return med.buscarById(id);
    }

    /**
     * Medoto generico de modificacion
     *
     * @param objeto a actualizar
     * @throws java.lang.Exception
     */
    public void actualizar(Object obj) throws Exception{
    	med.actualizar(obj);
    }

    /**
     * Medoto generico de eliminacion
     *
     * @param objeto a eliminar
     * @throws java.lang.Exception
     */
    public void eliminar(Object obj) throws Exception{
    	med.eliminar(obj);
    }
    
    public Object loginUsuario(String usuario, String password) throws Exception{    	
    	return med.loginUsuario(usuario,password);
    }
    
	
	
}
