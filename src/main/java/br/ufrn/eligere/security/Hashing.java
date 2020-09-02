/*
 * Universidade Federal do Rio Grande do Norte
 * Superintendência de Informática
 * Diretoria de Sistemas
 * Equipe Orbitais
 *
 * É proibido usar, copiar, modificar, mesclar, publicar, distribuir, sublicenciar e / ou vender cópias
 * desse Software sem estar de acordo com os termos da cooperação da UFRN.
 *
 * O aviso de copyright acima deve ser incluído em todas as cópias ou partes substanciais do Software.
 *
 * AuditorCodigoFonte
 * br.ufrn.eligere.security
 * Hashing
 * 01/09/2020 11:15
 */
package br.ufrn.eligere.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class responsible for generating the hash used in the election system
 *
 * @author Jadson Santos - jadson@info.ufrn.br
 * @version 1.0
 * @since 01/09/2020
 */
public class Hashing {

    /**
     * Return a hash of 64 character used to validate information of Eligere System
     * @param dado
     * @return
     */
    public static String toSHA256(String dado) {
        return toSHA(dado, "256");
    }


    private static String toSHA(String dado, String algorithm) {

        if( ! algorithm.equals("256")  && ! algorithm.equals("512") && ! algorithm.equals("1024")) throw new IllegalArgumentException("Wrong Algorithm passed");

        StringBuffer sb = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-"+algorithm);

            md.update(dado.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return sb.toString();
    }

}

