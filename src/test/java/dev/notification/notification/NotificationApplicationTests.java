package dev.notification.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NotificationService notificationService;

	@Test
	void contextLoads() {
	}


	@Test
	void testCreateNotification() throws Exception {
		NotificationClass notification = new NotificationClass(/* Initialize notification properties */);
		notification.setNotificationId(1L);
		Mockito.when(notificationService.createNotification(Mockito.any(NotificationClass.class)))
				.thenReturn(notification);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notifications")
						.content(asJsonString(notification))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.notificationId").exists());
	}

	@Test
	void testGetAllNotifications() throws Exception {
		NotificationClass notification = new NotificationClass(/* Initialize notification properties */);

		Mockito.when(notificationService.getAllNotifications())
				.thenReturn(Arrays.asList(notification));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notifications")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].notificationId").value(notification.getNotificationId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(notification.getTitle()))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(notification.getDescription()));
	}

	@Test
	void testGetNotification() throws Exception {
		Long notificationId = 1L;
		NotificationClass notification = new NotificationClass(/* Initialize notification properties */);
		notification.setNotificationId(notificationId);
		Mockito.when(notificationService.getNotification(notificationId))
				.thenReturn(Optional.of(notification));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notifications/{notificationId}", notificationId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.notificationId").value(notificationId));
	}

	@Test
	void testUpdateNotification() throws Exception {
		Long notificationId = 1L;
		NotificationClass updatedNotification = new NotificationClass(/* Initialize updated notification properties */);
		updatedNotification.setNotificationId(1L);
		Mockito.when(notificationService.updateNotification(Mockito.eq(notificationId), Mockito.any(NotificationClass.class)))
				.thenReturn(updatedNotification);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/notifications/{notificationId}", notificationId)
						.content(asJsonString(updatedNotification))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.notificationId").value(notificationId));
	}

	@Test
	void testDeleteNotification() throws Exception {
		Long notificationId = 1L;

		Mockito.when(notificationService.deleteNotification(notificationId))
				.thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/notifications/{notificationId}", notificationId))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
