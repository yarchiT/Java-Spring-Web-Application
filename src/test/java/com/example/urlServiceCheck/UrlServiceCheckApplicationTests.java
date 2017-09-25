package com.example.urlServiceCheck;

import com.example.urlServiceCheck.domain.UrlCheck;
import com.example.urlServiceCheck.domain.UrlCheckRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlServiceCheckApplication.class)
@WebAppConfiguration
public class UrlServiceCheckApplicationTests {


	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	private MockMvc mockMvc;
	private UrlCheck urlCheck;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private UrlCheckRepository repository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);

		assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.repository.deleteAllInBatch();
		this.urlCheck = repository.save(new UrlCheck("https://stackoverflow.com/", 200));
	}

	@Test
	public void createUrl() throws Exception {
		String urlJson = json(new UrlCheck("https://www.google.com.ua/"));

		this.mockMvc.perform(post("/urls")
				.contentType(contentType)
				.content(urlJson))
				.andExpect(status().isCreated());
	}


	@Test
	public void passJson() throws Exception {
		this.mockMvc.perform(get("/urls"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(urlCheck.getId().intValue())))
				.andExpect(jsonPath("$[0].urlS", is(this.urlCheck.getUrlS())))
				.andExpect(jsonPath("$[0].responseCode", is(this.urlCheck.getResponseCode())));
	}



	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
				o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}