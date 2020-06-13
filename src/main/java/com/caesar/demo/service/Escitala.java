package com.caesar.demo.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Random;

/**
 * Clase que representa una escítala para el cifrado y descifrado de mensajes de texto.
 *
 * @author Alberto Dominguez Matamoros
 */
public class Escitala {
    /** Número de caras de la escítala. */
    private int caras;

    /**
     * Constructor con inicialización explícita del número de caras de la Escítala.
     *
     * @param numCaras
     *        número de caras de la escítala
     * @throws IllegalArgumentException
     *         si el parámetro no es un entero positivo mayor que 1
     */
    public Escitala( String numCaras ) {
        this.caras = StringUtils.isNotBlank(numCaras)? Integer.parseInt(numCaras):5;
    }

    /**
     * Cifra una cadena de texto.
     * <p>
     * <strong>¡Atención!</strong> Esta función es no determinista.
     * <p>
     * Además, es importante tener en cuenta que la longitud de la cadena de texto retornada es
     * igual al primer múltiplo del número de caras de la escítala igual o superior a la longitud de
     * la frase a cifrar.
     *
     * @param frase
     *        frase a cifrar
     * @return La cadena de texto cifrada.
     * @see <a href="http://es.wikipedia.org/wiki/Algoritmo_no_determinista">Algoritmo no
     *      determinista</a>
     */
    public String encrypt( final String frase ) {
        String result = null;
        if (isValid( frase )) {
            final char[][] array = new char[caras][calculaColumnas( frase.length() )];

            Escitala.rellenarArray( frase, array );

            result = Escitala.toText( array );
        }
        return result;
    }

    /**
     * Descifra una cadena de texto previamente cifrada por una escítala compatible.
     * <p>
     * La frase en texto claro devuelta puede que tenga al final caracteres adicionales a los que
     * tenía el texto original.
     *
     * @param frase
     *        frase a descifrar.
     * @return la frase descifrada.
     */
    public String decrypt( final String frase ) {
        String result = null;
        if (isValid( frase )) {
            // Para desencriptar intercambiamos filas x columnas
            final char[][] array = new char[calculaColumnas( frase.length() )][caras];

            Escitala.rellenarArray( frase, array );

            result = Escitala.toText( array );
        }
        return result;
    }

    /**
     * Rellena el array bidimensional de caracteres pasado como argumento con la frase especificada.
     * <p>
     * El array es rellenado de izquierda a derecha y de arriba a abajo.
     *
     * @param frase
     *        frase con la que rellenar el array.
     * @param array
     *        array a rellenar
     * @see #toString()
     */
    private static void rellenarArray(	final String frase,
                                          final char[][] array ) {
        int currentPos = 0;

        for (int fila = 0; fila < array.length; fila++) {
            for (int columna = 0; columna < array[fila].length; columna++) {
                array[fila][columna] = currentPos < frase.length() ? frase.charAt( currentPos++ ) : Escitala
                        .aleatorio();
            }
        }
    }

    /**
     * Genera un carácter aleatorio.
     * <p>
     * Función de utilidad que genera un único carácter aleatorio. Utilizada internamente para
     * rellenar la tabla cuando la longitud de la frase a cifrar no es un múltiplo del número de
     * caras.
     *
     * @return un carácter aleatorio.
     */
    private static char aleatorio() {
        final int MIN_CHAR = '0';
        final int MAX_CHAR = 'z';
        char ch;
        final Random random = new Random();

        do {
            ch = (char) (random.nextInt( MAX_CHAR - MIN_CHAR + 1 ) + MIN_CHAR);
        }
        while (!Character.isLetterOrDigit( ch ));

        return ch;
    }

    /**
     * Convierte el array bidimensional pasado como parámetro en un <code>String</code>.
     * <p>
     * Puede considerarse la función inversa de {@link Escitala#rellenarArray(String, char[][])}: el
     * array es explorado de arriba hacia abajo y de izquierda a derecha.
     *
     * @param array
     *        array bidimensional a convertir en una cadena.
     * @return una cadena de texto representando el array pasado como argumento.
     */
    private static String toText( final char[][] array ) {
        //Ya que no trabajamos con hilos creamos un StringBuilder que es más eficiente en estos casos
        final StringBuilder sb = new StringBuilder( array.length * array[0].length );

        for (int columna = 0; columna < array[0].length; columna++) {
            for (final char[] fila : array) {
                sb.append( fila[columna] );
            }
        }
        return sb.toString();
    }

    /**
     * Valida si la frase pasada como argumento es <em>compatible</em> con esta escítala.
     *
     * @param frase
     *        frase a validar para esta escítala.
     * @return <code>true</code> si la <code>frase</code> es <em>compatible</em> con la escítala,
     *         <code>false</code> en caso contrario.
     */
    private boolean isValid( final String frase ) {

        return frase != null && frase.length() > caras;
    }

    /**
     * Calcula (y retorna) el número de columnas necesarias para almacenar una frase de una longitud
     * dada.
     *
     * @param lenFrase
     *        longitud de la frase para la que es necesario calcular el número de columnas.
     * @return el número de columnas que ha de tener el array que almacenará la frase
     */
    private int calculaColumnas( final int lenFrase ) {
        return lenFrase % caras == 0 ? lenFrase / caras : lenFrase / caras + 1;
    }

    /**
     * Retorna el número de caras de la escítala.
     *
     * @return el número de caras de la escítala.
     */
    public int getCaras() {
        return caras;
    }
}
