package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Data;
import org.example.view.enums.Outputs;

public class GameSettingMenuController {
    public static Outputs checkUser(String username) {
        if (Data.findUserWithUsername(username) != null) {
            if (Data.findUserWithUsername(username).getInGame())
                return Outputs.USER_IN_GAME;
            return Outputs.SUCCESS;
        }
        return Outputs.NOT_EXISTING_USERNAME;
    }
}
