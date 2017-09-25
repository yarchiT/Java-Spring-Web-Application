package com.example.urlServiceCheck;

import com.example.urlServiceCheck.domain.UrlCheck;
import com.example.urlServiceCheck.domain.UrlCheckRepository;
import com.example.urlServiceCheck.service.PingService;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class UrlServiceCheckApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PingService pingService;

	@Autowired
	private UrlCheckRepository repository;

	@After
	public void tearDown() {
		repository.deleteAll();
	}

	@Test
	public void testAddUrl()
			throws Exception {
		String urlString = "https://www.google.com.ua";
		int responseCode = 304;

		given(pingService.pingURL(Mockito.eq(urlString), Mockito.anyInt())).willReturn(responseCode);

		this.mockMvc.perform(post("/urls").contentType(MediaType.APPLICATION_JSON).content(urlString))
				.andExpect(status().isCreated());

		UrlCheck savedUrlCheck = repository.findOne(urlString);
		Assertions.assertThat(savedUrlCheck).isNotNull();
		Assertions.assertThat(savedUrlCheck.getUrlString()).isEqualTo(urlString);
		Assertions.assertThat(savedUrlCheck.getResponseCode()).isEqualTo(responseCode);
	}

	@Test
	public void testGetUrls()
			throws Exception {
		UrlCheck first = repository.save(new UrlCheck("https://stackoverflow.com/", 200));
		UrlCheck second = repository.save(new UrlCheck("https://www.google.com.ua/", 404));

		this.mockMvc.perform(get("/urls"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].urlString", is(first.getUrlString())))
				.andExpect(jsonPath("$[0].responseCode", is(first.getResponseCode())))
				.andExpect(jsonPath("$[1].urlString", is(second.getUrlString())))
				.andExpect(jsonPath("$[1].responseCode", is(second.getResponseCode())));
	}

}