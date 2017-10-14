package co.appreactor.agendaandroid.modelo.servicio;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import co.appreactor.agendaandroid.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 28/09/17.
 */

public class ArchivadorDom implements IGestorArchivo {

    private final String nombreArchivo = "/archivo_dom.txt";
    private static final String TAG_DOM = "ArchivadorDom";

    public ArchivadorDom() {
        File carpeta = new File(rutaArchivo);
        if (!carpeta.exists()){
            carpeta.mkdir();
            Log.d(TAG_DOM, "Ruta carpeta: "+carpeta.getAbsolutePath());
        }
    }

    @Override
    public void escribir(List<Persona> listaPersonas) throws IOException {
        try {
            Document documento = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // Declaracion de la etiqueta base del xml
            Element elementoLista = documento.createElement("lista");
            // Ciclo para recorrer los objetos del dataset y convertirlos en nodos
            // de xml dentro de la etiqueta lista
            for (Persona personaGuardar : listaPersonas) {
                // Definicion de la etiqueta persona para el contenido del documento
                Element elementoPersona = documento.createElement("persona");

                // definicion de etiquetas de atributos del objeto persona

                // nombre
                Element nombrePersona = documento.createElement("nombre");
                nombrePersona.setTextContent(personaGuardar.getNombre());
                elementoPersona.appendChild(nombrePersona);

                // correo
                Element correoPersona = documento.createElement("correo");
                correoPersona.setTextContent(personaGuardar.getCorreo());
                elementoPersona.appendChild(correoPersona);

                // edad
                Element edadPersona = documento.createElement("edad");
                edadPersona.setTextContent(personaGuardar.getEdad());
                elementoPersona.appendChild(edadPersona);

                // telefono
                Element telefonoPersona = documento.createElement("telefono");
                telefonoPersona.setTextContent(personaGuardar.getTelefono());
                elementoPersona.appendChild(telefonoPersona);

                // direccion
                Element direccionPersona = documento.createElement("direccion");
                direccionPersona.setTextContent(personaGuardar.getDireccion());
                elementoPersona.appendChild(direccionPersona);

                // agregar el elemento persona al la lista del documento xml
                elementoLista.appendChild(elementoPersona);
            }
            // agregar al documento xml el elemento lista con su nivel de
            // agregación
            documento.appendChild(elementoLista);

            // Generar el transformador del documento en memoria al disco
            Transformer megatron = TransformerFactory.newInstance().newTransformer();

            DOMSource origen = new DOMSource(documento);

            StreamResult destino = new StreamResult(new File(rutaArchivo+nombreArchivo));

            megatron.transform(origen, destino);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Persona> leer() {
        List<Persona> listaPersonas = new ArrayList<>();
        try{
            // Parsear a un documento en memoria el archivo con informacion en xml
            Document documento = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new File(rutaArchivo+nombreArchivo));

            // recuperar como lista de nodos la informacion del documento xml
            NodeList nodoListaPersonas = documento.getElementsByTagName("persona");

            // Recorrer los objetos xml de personas con un ciclo for clasico
            for (int i = 0; i < nodoListaPersonas.getLength(); i++){
                // Obtener un nodo de tipo persona de la lista de nodos
                Node nodoPersona = nodoListaPersonas.item(i);
                // Convertir la informacion de un nodo de persona en un objeto java
                // de la clase "Persona"
                Persona personaPorNodo = new Persona();
                // llamado a la conversion por referencia
                convertirNodoPersona(personaPorNodo,nodoPersona);
                // Agregar persona convetida a la lista
                listaPersonas.add(personaPorNodo);
            }

        } catch(ParserConfigurationException | IOException | SAXException e){
            e.printStackTrace();
        }
        return listaPersonas;
    }

    private void convertirNodoPersona(Persona personaPorNodo, Node nodoPersona) {
        // Obtener la lista de nodos que representan los atributos de la clase persona
        NodeList nodoListaAtributos = nodoPersona.getChildNodes();

        for (int i = 0; i < nodoListaAtributos.getLength(); i++){
            // Obtener el nodo del atributo en la posicion del ciclo
            Node nodoAtributo = nodoListaAtributos.item(i);
            // obtener el nombre de la etiqueta del atributo de la persona
            String nombreAtributo = nodoAtributo.getNodeName();
            // obtener el valor de la etiqueda del atributo de la persona
            String valorAtributo = nodoAtributo.getTextContent();
            switch (nombreAtributo){
                case "nombre":
                    personaPorNodo.setNombre(valorAtributo);
                    break;
                case "correo":
                    personaPorNodo.setCorreo(valorAtributo);
                    break;
                case "edad":
                    personaPorNodo.setEdad(valorAtributo);
                    break;
                case "telefono":
                    personaPorNodo.setTelefono(valorAtributo);
                    break;
                case "direccion":
                    personaPorNodo.setDireccion(valorAtributo);
                    break;
            }
        }
    }
}
