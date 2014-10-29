package com.jorge.twitter.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jorge.twitter.MiniTwitterApp;
import com.jorge.twitter.repository.TokenRepository;
import com.jorge.twitter.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MiniTwitterApp.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("dev")
public class PublicRestControllerTest {

  @Autowired
  private UserRepository  userRepository;
  @Autowired
  private TokenRepository tokenRepository;
  private MockMvc         restUserMockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    PublicRestController publicRestController = new PublicRestController();
    ReflectionTestUtils.setField(publicRestController, "userRepository", userRepository);
    ReflectionTestUtils.setField(publicRestController, "tokenRepository", tokenRepository);
    this.restUserMockMvc = MockMvcBuilders.standaloneSetup(publicRestController).build();
  }

  @Test
  public void testNonAuthenticatedUser() throws Exception {
    restUserMockMvc.perform(post("/public/login?username=jorge")//
        .accept(MediaType.APPLICATION_JSON))//
        .andExpect(status().isOk());

    restUserMockMvc.perform(post("/public/login.xml?username=jorge")//
        .accept(MediaType.APPLICATION_XML))//
        .andExpect(status().isOk());
  }

}
