package com.ecommerce.Service;

import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Repository.IOrdenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Optional;

@Service
@Primary

public class EmailServiceImp implements IEmailService{


    @Autowired
    IOrdenRepository ordenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String enviarEmail(Integer idOrden){

        String textoEmail = "";

        Optional<OrdenModel> objO = ordenRepository.findById(idOrden);

        if(objO.isPresent()){

            try {

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                String emailCliente = objO.get().getIdUsuario().getCorreo();
                String nombreCliente = objO.get().getIdUsuario().getNombre();
                Long numeroCliente = objO.get().getIdUsuario().getTelefono();
                String direccionEnvio = objO.get().getDireccion();
                String fechaEnvio = objO.get().getFecha();
                String tipoEnvio = objO.get().getTipoEntrega();


                String asuntoCorreo = "Confirmación Orden Compra " + objO.get().getIdOrden() + " - TheGreatEcommerce";
                String htmlBody = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Confirmación de Compra</title>\n" +
                        "    <style>\n" +
                        "        body {\n" +
                        "            font-family: Arial, sans-serif;\n" +
                        "            background-color: #f4f4f4;\n" +
                        "            color: #333;\n" +
                        "            padding: 20px;\n" +
                        "        }\n" +
                        "        .container {\n" +
                        "            max-width: 600px;\n" +
                        "            margin: 0 auto;\n" +
                        "            background-color: silver;\n" +
                        "            padding: 30px;\n" +
                        "            border-radius: 8px;\n" +
                        "            box-shadow: 0 2px 5px rgba(0,0,0,0.1);\n" +
                        "        }\n" +
                        "        h1, p {\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        .order-summary {\n" +
                        "            margin-top: 30px;\n" +
                        "            border-top: 2px solid #ccc;\n" +
                        "            padding-top: 20px;\n" +
                        "        }\n" +
                        "        table {\n" +
                        "            width: 100%;\n" +
                        "            border-collapse: collapse;\n" +
                        "            margin-top: 20px;\n" +
                        "        }\n" +
                        "        table, th, td {\n" +
                        "            border: 1px solid #ddd;\n" +
                        "        }\n" +
                        "        th, td {\n" +
                        "            padding: 12px;\n" +
                        "            text-align: left;\n" +
                        "        }\n" +
                        "        .total {\n" +
                        "            margin-top: 20px;\n" +
                        "            text-align: right;\n" +
                        "        }\n" +
                        "        .footer {\n" +
                        "            margin-top: 30px;\n" +
                        "            text-align: center;\n" +
                        "            color: #888;\n" +
                        "        }\n" +
                        "        .customer-info {\n" +
                        "            color: white;\n" +
                        "        }\n" +
                        "        .container h1 {\n" +
                        "            color: white;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div class=\"container\">\n" +
                        "        <div class=\"customer-info\">\n" +
                        "            <h1>Confirmación de Compra</h1>\n" +
                        "            <h2>Información del Cliente</h2>\n" +
                        "            <p><strong>Nombre: </strong>"+nombreCliente+"</p>\n" +
                        "            <p><strong>Correo Electrónico: </strong>"+emailCliente+"</p>\n" +
                        "            <p><strong>Teléfono: </strong>"+numeroCliente+"</p>\n" +
                        "            <p><strong>Dirección de Envío: </strong>"+direccionEnvio+"</p>\n" +
                        "        </div>\n" +
                        "        <div class=\"order-summary\">\n" +
                        "            <h2>Resumen de la Orden</h2>\n" +
                        "            <table>\n" +
                        "                <thead>\n" +
                        "                    <tr>\n" +
                        "                        <th>Producto</th>\n" +
                        "                        <th>Cantidad</th>\n" +
                        "                        <th>Precio Unitario</th>\n" +
                        "                        <th>Subtotal</th>\n" +
                        "                    </tr>\n" +
                        "                </thead>\n" +
                        "                <tbody>\n" +
                        "                    <tr>\n" +
                        "                        <td>Producto A</td>\n" +
                        "                        <td>2</td>\n" +
                        "                        <td>$25.00</td>\n" +
                        "                        <td>$50.00</td>\n" +
                        "                    </tr>\n" +
                        "                    <tr>\n" +
                        "                        <td>Producto B</td>\n" +
                        "                        <td>1</td>\n" +
                        "                        <td>$35.00</td>\n" +
                        "                        <td>$35.00</td>\n" +
                        "                    </tr>\n" +
                        "                </tbody>\n" +
                        "            </table>\n" +
                        "            <div class=\"total\">\n" +
                        "                <p><strong>Total: $85.00</strong></p>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <div class=\"shipping-details\">\n" +
                        "            <h2>Detalles de Envío</h2>\n" +
                        "            <p><strong>Método de Envío: </strong>"+tipoEnvio+"</p>\n" +
                        "            <p><strong>Fecha de Envío Estimada: </strong>"+fechaEnvio+"</p>\n" +
                        "            <p><strong>Dirección de Envío: </strong>"+direccionEnvio+"</p>\n" +
                        "        </div>\n" +
                        "        <p>¡Gracias por tu compra en nuestra aplicación de ventas! Recibirás un correo de confirmación de envío cuando tu orden sea despachada.</p>\n" +
                        "        <div class=\"footer\">\n" +
                        "            <p>Para consultas, contáctanos a <a href=\"mailto:thegreatecommerce@gmail.com\">thegreatecommerce@gmail.com</a></p>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";



                helper.setTo(emailCliente);
                helper.setSubject(asuntoCorreo);
                helper.setText(htmlBody, true);

                message.setFrom("thegreatecommerce@gmail.com"); // Asegúrate de usar el email configurado

                mailSender.send(message);
            } catch (MessagingException e) {
                // Maneja la excepción de forma adecuada aquí (por ejemplo, logueando el error)
                e.printStackTrace();
                // Puedes lanzar una RuntimeException para que la excepción sea visible
                throw new RuntimeException("Error while sending email", e);
            }

        }




        return textoEmail;
    };
}
