package com.proyect.apiequipo.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.proyect.apiequipo.user.model.User;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@Log
public class JwtProvider {

	public static final String TOKEN_TYPE = "JWT";
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";

	// INYECTAMOS AQUI LAS PROPIEDADES QUE TENEMOS EN NUESTRO ARCHIVO PROPERTIES
	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.duration}")
	private int jwtLifeInDays;

	private JwtParser jwtParser;

	private SecretKey secretKey;

	// CUANDO SPRING TERMINE DE CREAR EL BEAN, EJECUTARÁ ESTE MÉTODO UNA VEZ, POR
	// ELLO
	// UTILIZAMOS LA ANOTACIÓN POSTCONSTRUCT
	@PostConstruct
	public void init() {

		secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes()); // OBTENEMOS EL SECRETO
		jwtParser = Jwts.parserBuilder() // EL PARSER SE ENCARGARÁ TANTO DE PRODUCIR COMO DE VALIDAR LOS TOKENS
				.setSigningKey(secretKey)// INDICAMOS LA CLAVE AL PARSERBUILDER
				.build();

	}

	// MÉTODO PARA CREAR UN TOKEN.
	public String generateToken(Authentication authentication) {

		User user = (User) authentication.getPrincipal();

		// CALCULAMOS LA EXPIRACIÓN DEL TOKEN
		Date tokenExpirationDateTime = Date.from(LocalDateTime.now()// COGEMOS EL MOMENTO ACTUAL EN EL CUAL SE CREA EL
																	// TOKEN
				.plusDays(jwtLifeInDays) // ESTABLECEMOS LA VIDA DE ESTE
				.atZone(ZoneId.systemDefault()) // ZONA HORARIA POR DEFECTO DEL SISTEMA
				.toInstant()// TRANSFORMACIÓN A INSTANTE
		);

		// DEVOLVEMOS EL TOKEN
		return Jwts.builder().setHeaderParam("typ", TOKEN_TYPE).setSubject(user.getIdUser().toString())// CAMPO QUE
																										// INCLUYE LA
																										// MANERA DE
																										// IDENTIFICAR
																										// EL USUARIO
				.setIssuedAt(new Date()) // INDICAMOS QUE EL TOKEN SE ACABA DE GENERAR
				.setExpiration(tokenExpirationDateTime)// LA FECHA DE EXPIRACION ES LA QUE HEMOS CREADO JUSTO ARRIBA
				.signWith(secretKey)// FIRMAMOS EL TOKEN CON EL SECRETO
				.compact();// COMPACTAMOS EL TOKEN COMO UNA CADENA DE CARACTERES
	}

	public boolean validateToken(String token) {

		try {
			// PARSEAMOS LOS CLAIMS(INFO + PRIVILEGIOS DEL USUARIO) DE NUESTRO TOKEN Y AL
			// HACERLO, YA ESTAMOS REALIZANDO UNA VALIDACIÓN DEBIDO A QUE SI NO
			// PUEDE PARSEARLO NOS LANZARÁ UNA DE LAS EXCEPCIONES QUE TENEMOS DEFINIDAS A
			// CONTINUACIÓN.
			jwtParser.parseClaimsJws(token);
			return true;

			// PROBLEMA CON LA FIRMA TOKEN NO ESTE BIEN FORMADO EL TOKEN HAYA EXPIRADO
			// || TOKEN NO SOPORTADO.
		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
				| IllegalArgumentException ex) {
			log.info("Error con el token " + ex.getMessage());
		}

		return false;
	}

	// MÉTODO PARA OBTENER EL UUID DE UN TOKEN JWT
	public UUID getUserIdFromJwtToken(String token) {
		// PROPORCIONAMOS UN TOKEN A NUESTRO PARSER Y OBTENEMOS EL CUERPO Y EL SUBJECT
		// DEL TOKEN
		// PARA RESCATAR EL ID DEL USUARIO Y A PARTIR DE AHÍ CONSTRUIR EL UUID.
		return UUID.fromString(jwtParser.parseClaimsJws(token).getBody().getSubject());
	}
}