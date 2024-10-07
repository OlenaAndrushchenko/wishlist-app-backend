package dev.olena.wishapp.firebase;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;

@Service
@Profile("!test")
public class FirebaseStorageService {

    private final Storage storage;
    private final String storageBucket;

    @Autowired
    public FirebaseStorageService(Storage storage) {
        this.storage = storage;
        this.storageBucket = System.getProperty("STORAGE_BUCKET");
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
        BlobId blobId = BlobId.of(storageBucket, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();

        Blob blob = storage.create(blobInfo, multipartFile.getInputStream().readAllBytes());

        blob.createAcl(Acl.of(User.ofAllUsers(), Acl.Role.READER));

        return String.format("https://storage.googleapis.com/%s/%s", blob.getBucket(), blob.getName());
    }
}
