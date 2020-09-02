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
 * br.ufrn.eligere.auditoria
 * AuditoriaCodigoFonte
 * 01/09/2020 11:11
 */
package br.ufrn.eligere.auditoria;

import br.ufrn.eligere.security.Hashing;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Audit the code of eligere using the same code of application.
 *
 * Every one can use this code do varify if the hash of application is correct.
 *
 * @author Jadson Santos - jadson@info.ufrn.br
 * @version 1.0
 * @since 01/09/2020
 */
public class AuditoriaCodigoFonte {

    /**
     * Same hash generation used by system.
     * @param files
     * @return
     */
    public String calculaHashCodigoAuditavel(File[] files, String timeFactor) {

        StringBuilder builder = new StringBuilder();

        try {
            if(files != null) {
                for (File clazzFile : files) {

                    InputStream stream = new FileInputStream(clazzFile);

                    byte[] dotclass = IOUtils.toByteArray(stream);
                    builder.append(new String(dotclass, "UTF-8"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Hashing.toSHA256(builder.toString()+timeFactor);
    }

}

