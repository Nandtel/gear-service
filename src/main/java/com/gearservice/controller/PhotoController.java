package com.gearservice.controller;

import com.gearservice.model.cheque.Photo;
import com.gearservice.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * Class PhotoController is controller, that handle requests of photos.
 * Use @Autowired for connect to necessary services
 * Use RequestMapping for handle request from the client-side
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@RestController
public class PhotoController {

    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);
    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @RequestMapping(value = "/api/upload-image", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void uploadImage(@RequestParam("file") MultipartFile photo,
                            @RequestParam("chequeID") Long chequeID,
                            @RequestParam("username") String username,
                            Principal principal) {
        photoService.uploadImage(photo, chequeID, username);
        logger.info("User " + principal.getName().toUpperCase() + " has added photo to cheque " + chequeID);
    }

    @RequestMapping(value = "/api/photo/{photoID}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPhotoByID(@PathVariable String photoID) {
        return photoService.getPhotoByID(photoID);
    }

    @RequestMapping(value = "/api/photo/{chequeID}/{photoID}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePhotoByID(@PathVariable Long chequeID, @PathVariable String photoID, Principal principal) {
        photoService.deletePhotoByID(photoID);
        logger.info("User " + principal.getName().toUpperCase() + " has removed  photo from cheque " + chequeID);
    }

    @RequestMapping(value = "/api/photo/cheque/{chequeID}", method = RequestMethod.GET)
    public List<Photo> getListOfPhotoExcludeBytesFromCheque(@PathVariable String chequeID) {
        return photoService.getListOfPhotoExcludeBytesFromCheque(chequeID);
    }


}
