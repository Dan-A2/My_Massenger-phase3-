package util;

import database.image.ImageSaver;
import ir.sharif.ap.phase3.model.help.UserCopy;
import ir.sharif.ap.phase3.model.main.User;
import ir.sharif.ap.phase3.util.ImageConvertor;

public class UserCopyCreator {

    public static UserCopy createUser(User user) {
        UserCopy userCopy = new UserCopy(user);
        if (user.getProfileImageId() != null && user.getProfileImageId() > 0) {
            ImageConvertor convertor = new ImageConvertor();
            ImageSaver saver = new ImageSaver();
            userCopy.setProfileImage(convertor.convertByte(convertor.selectImage(saver.getImage(user.getProfileImageId()))));
        }
        return userCopy;
    }

}