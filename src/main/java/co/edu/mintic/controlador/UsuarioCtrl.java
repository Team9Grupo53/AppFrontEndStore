package co.edu.mintic.controlador;

import java.util.List;

import co.edu.mintic.dto.TokenDTO;
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
    public List<Object> listar(TokenDTO tk) throws Exception{
    	return med.listar(tk);
    }

    /**
     * Medoto generico para consultar por id
     *
     * @param id filtro de busqueda
     * @return objeto encontrado
     * @throws java.lang.Exception
     */
    public Object buscarById(int id,TokenDTO tk) throws Exception{
    	return med.buscarById(id,tk);
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
