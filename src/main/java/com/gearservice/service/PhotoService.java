package com.gearservice.service;

import com.gearservice.model.cheque.Photo;
import com.gearservice.repositories.mongo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Class PhotoService is service, that handle PhotoController
 * Use @Autowired for connect to necessary repositories
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final UserService userService;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, UserService userService) {
        this.photoRepository = photoRepository;
        this.userService = userService;
    }

    /**
     * Method replaceFullnameToActual replace name of user from login to fullname
     * @param photo is Photo object
     * @param users is map of login-fullaname of users
     * @return this Photo after editing
     */
    private static Photo replaceFullnameToActual(Photo photo, Map<String, String> users) {
        photo.setUsername(users.get(photo.getUsername()));
        return photo;
    }

    /**
     * Method uploadImage async take uploaded images and save it to mongoDB
     * @param file is photo array of bytes
     * @param chequeID is id of cheque, that should contains this photo
     * @param username is name of creator
     */
    public void uploadImage(MultipartFile file, Long chequeID, String username) {

        if(!file.isEmpty()) {
            Photo photo = new Photo();
            try {
                photo.setBytes(file.getBytes());
            } catch (IOException e) {e.printStackTrace();}
            photo.setName(file.getOriginalFilename());
            photo.setContentType(file.getContentType());
            photo.setChequeId(chequeID.toString());
            photo.setUsername(username);
            photo.setAddDate(OffsetDateTime.now());
            photoRepository.save(photo);
        }
    }

    /**
     * Method getListOfPhotoExcludeBytesFromCheque get list of photo without arrays of bytes
     * @param chequeID is id of cheque, that contains photos
     * @return list of photos without arrays of bytes
     */
    public List<Photo> getListOfPhotoExcludeBytesFromCheque(String chequeID) {
        Map<String, String> users = userService.getUsernameFullnameMap();
        return photoRepository.findByChequeIdExcludeBytes(chequeID)
                .stream()
                .map(photo -> PhotoService.replaceFullnameToActual(photo, users))
                .collect(toList());
    }

    /**
     * Method getPhotoByID return only photo's array of bytes as response entity
     * @param photoID is id of photo
     * @return photo's array of bytes as response entity
     */
    public ResponseEntity<byte[]> getPhotoByID(String photoID) {
        Photo photo = photoRepository.findById(photoID).orElseThrow(EntityNotFoundException::new);

        return ResponseEntity
                .ok()
                .contentLength(photo.getBytes().length)
                .contentType(MediaType.parseMediaType(photo.getContentType()))
                .body(photo.getBytes());
    }

    /**
     * Method deletePhotoByID delete photo by id from DB
     * @param photoID is id photo, that should be removed
     */
    public void deletePhotoByID(String photoID) {photoRepository.deleteById(photoID);}

}
