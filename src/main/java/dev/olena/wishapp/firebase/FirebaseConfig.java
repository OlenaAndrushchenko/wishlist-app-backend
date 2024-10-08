package dev.olena.wishapp.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Profile("!test")
public class FirebaseConfig {

    @Bean
    public Storage firebaseStorage() throws IOException {
        var serviceAccount = new ClassPathResource("firebase.json").getInputStream();
        return StorageOptions.newBuilder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
            .getService();
    }
}