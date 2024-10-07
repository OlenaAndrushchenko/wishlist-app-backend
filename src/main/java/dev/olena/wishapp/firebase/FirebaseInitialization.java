package dev.olena.wishapp.firebase;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Service
@Profile("!test")
public class FirebaseInitialization {
    
    private final String storageBucket;

    @Autowired
    public FirebaseInitialization() {
        this.storageBucket = System.getProperty("STORAGE_BUCKET");
    }

    @PostConstruct
    public void init() {
        try {
            ClassPathResource serviceAccount = new ClassPathResource("firebase.json");

            FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
            .setStorageBucket(storageBucket)
            .build();
                
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
