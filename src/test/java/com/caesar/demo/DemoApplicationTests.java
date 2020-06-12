package com.caesar.demo;

import com.caesar.demo.controller.ApiController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
class DemoApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void givenSqrt() throws Exception{
		Double result = 20.0;

		mvc.perform(get("/api/math/sqrt")
				.param("number","400")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").exists())
				.andExpect(jsonPath("message").value(result))
		;
	}

	@Test
	public void givenEncrypt() throws Exception{
		//Es parte de una cancion :D
		String unencrypted = "y recuerdo que me lo decias que si no cambiaba te perdia como quisiera volver a esos dias"
				+" ahora que soy rico no tengo lo que tenia";
		String encrypted = "d wjhzjwit vzj rj qt ijhnfx vzj xn st hfrgnfgf yj ujwinf htrt vznxnjwf atqajw f jxtx infx "
				+ "fmtwf vzj xtd wnht st yjslt qt vzj yjsnf";

		mvc.perform(get("/api/caesar/encrypt")
				.param("text",unencrypted)
				.param("key","5")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").exists())
				.andExpect(jsonPath("message").value(encrypted))
		;
	}

	@Test
	public void givenEncryptPost() throws Exception{

		String unencrypted = "y recuerdo que me lo decias que si no cambiaba te perdia como quisiera volver a esos dias"
				+" ahora que soy rico no tengo lo que tenia";
		String encrypted = "d wjhzjwit vzj rj qt ijhnfx vzj xn st hfrgnfgf yj ujwinf htrt vznxnjwf atqajw f jxtx infx "
				+ "fmtwf vzj xtd wnht st yjslt qt vzj yjsnf";

		CaesarBody cb = new CaesarBody(unencrypted,5);

		mvc.perform(post("/api/caesar/encrypt")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cb))
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").exists())
				.andExpect(jsonPath("message").value(encrypted))
		;
	}

	@Test
	public void givenCustomEncryptPost() throws Exception{

		String unencrypted = "y recuerdo que me lo decias que si no cambiaba te perdia como quisiera volver a esos dias"
				+" ahora que soy rico no tengo lo que tenia";
		String encrypted = "d wjhzjwit vzj qj pt ijhñfx vzj xñ st hfqgñfgf yj ujwiñf htqt vzñxñjwf atpajw f jxtx iñfx "
				+"fmtwf vzj xtd wñht st yjslt pt vzj yjsñf";
		String alphabet = "abcdefghijklmñnopqrstuvwxyz";

		CaesarBody cb = new CaesarBody(alphabet,unencrypted,5);

		mvc.perform(post("/api/caesar/encrypt/custom")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cb))
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").exists())
				.andExpect(jsonPath("message").value(encrypted))
		;
	}

	@Test
	public void givenDecryptPost() throws Exception{

		String encrypted = "i bomeobny aeo wo vy nomskc aeo cs xy mkwlsklk do zobnsk mywy aescsobk fyvfob k ocyc nskc "
				+"krybk aeo cyi bsmy xy doxqy vy aeo doxsk";
		String decrypted = "y recuerdo que me lo decias que si no cambiaba te perdia como quisiera volver a esos dias"
				+" ahora que soy rico no tengo lo que tenia";

		CaesarBody cb = new CaesarBody(encrypted,10);

		mvc.perform(post("/api/caesar/decrypt")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cb))
				.characterEncoding("utf-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").exists())
				.andExpect(jsonPath("message").value(decrypted))
		;
	}

	@Test
	public void givenDecrypt() throws Exception{
		//Es parte de una cancion :D
		String encrypted = "i bomeobny aeo wo vy nomskc aeo cs xy mkwlsklk do zobnsk mywy aescsobk fyvfob k ocyc nskc "
				+"krybk aeo cyi bsmy xy doxqy vy aeo doxsk";
		String decrypted = "y recuerdo que me lo decias que si no cambiaba te perdia como quisiera volver a esos dias"
				+" ahora que soy rico no tengo lo que tenia";

		mvc.perform(get("/api/caesar/decrypt")
				.param("text",encrypted)
				.param("key","10")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("message").exists())
				.andExpect(jsonPath("message").value(decrypted))
		;
	}
}
