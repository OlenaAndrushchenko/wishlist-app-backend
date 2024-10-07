package dev.olena.wishapp.wishlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.olena.wishapp.user.AuthService;
import dev.olena.wishapp.user.User;
import dev.olena.wishapp.user.UserService;
import dev.olena.wishapp.utils.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private WishlistDTO wishlistDTO;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setUserIdentifier("user123");
        user.setEmail("user@example.com");
        user.setUsername("user123");
        user.setPassword("password");
        wishlistDTO = new WishlistDTO(1L, "Travel", "Travel destinations", true, "shareable-url", "user123", null, null, null);
        
        given(jwtUtil.extractEmail("dummy_token")).willReturn(user.getEmail());
        given(jwtUtil.validateToken("dummy_token", user.getEmail())).willReturn(true);
        given(authService.loadUserByUsername(user.getEmail())).willReturn(user);
        given(userService.getCurrentUser()).willReturn(user);
    }

    @Test
    void createWishlist_ReturnsCreated() throws Exception {
        given(wishlistService.createWishlist(any(WishlistDTO.class), eq(user))).willReturn(wishlistDTO);

        mockMvc.perform(post("/api/v1/wishlists/create")
                .header("Authorization", "Bearer dummy_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wishlistDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value(wishlistDTO.getTitle()));
    }

    @Test
    void readWishlist_ReturnsWishlist() throws Exception {
        given(wishlistService.readWishlist(1L, user)).willReturn(wishlistDTO);

        mockMvc.perform(get("/api/v1/wishlists/{id}", 1L)
                .header("Authorization", "Bearer dummy_token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(wishlistDTO.getTitle()));
    }

    @Test
    void updateWishlist_ReturnsUpdatedWishlist() throws Exception {
        given(wishlistService.updateWishlist(1L, wishlistDTO, user)).willReturn(wishlistDTO);

        mockMvc.perform(put("/api/v1/wishlists/{id}", 1L)
                .header("Authorization", "Bearer dummy_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wishlistDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Travel"));
    }

    @Test
    void deleteWishlist_ReturnsNoContent() throws Exception {
        doNothing().when(wishlistService).deleteWishlist(1L, user);

        mockMvc.perform(delete("/api/v1/wishlists/{id}", 1L)
                .header("Authorization", "Bearer dummy_token"))
            .andExpect(status().isNoContent());
    }

    @Test
    void readWishlists_ReturnsListOfWishlists() throws Exception {
        List<WishlistDTO> wishlists = List.of(wishlistDTO);

        given(wishlistService.readWishlists(user)).willReturn(wishlists);

        mockMvc.perform(get("/api/v1/wishlists")
                .header("Authorization", "Bearer dummy_token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Travel"));
    }

    @Test
    void getPublicWishlist_ReturnsWishlist() throws Exception {
        given(wishlistService.readSharedWishlist("user123", "shareable-url")).willReturn(wishlistDTO);

        mockMvc.perform(get("/api/v1/wishlists/shared/{userIdentifier}/{shareableUrl}", "user123", "shareable-url"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Travel"));
    }
}