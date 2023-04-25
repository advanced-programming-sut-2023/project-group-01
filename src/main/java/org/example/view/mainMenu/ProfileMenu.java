package org.example.view.mainMenu;

import org.example.model.User;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.ProfileMenuCommands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {

    private final User currentUser;

    public ProfileMenu(User currentUser){
        this.currentUser = currentUser;
    }

    public void run(Scanner scanner) throws IOException, NoSuchAlgorithmException {
        System.out.println("Now you are in Profile Menu!");
        String inputLine;
        Matcher matcher;
        Outputs output;

        while (true){
            inputLine = scanner.nextLine();

            if (ProfileMenuCommands.getMatcher(inputLine,ProfileMenuCommands.FULL_DISPLAY).find())
                System.out.println(fullDisplay());
            else if (ProfileMenuCommands.getMatcher(inputLine,ProfileMenuCommands.DISPLAY_SLOGAN).find())
                System.out.println(showSlogan());
            else if (ProfileMenuCommands.getMatcher(inputLine,ProfileMenuCommands.DISPLAY_HIGHSCORE).find())
                System.out.println(showHighscore());
            else if (ProfileMenuCommands.getMatcher(inputLine,ProfileMenuCommands.DISPLAY_RANK).find())
                System.out.println(showRank());
            else if (ProfileMenuCommands.getMatcher(inputLine,ProfileMenuCommands.REMOVE_SLOGAN).find())
                System.out.println(removeSlogan());
            else if (ProfileMenuCommands.getMatcher(inputLine,ProfileMenuCommands.BACK_TO_MAIN_MENU).find()){
                MainMenu mainMenu = new MainMenu(currentUser);
                mainMenu.run(scanner);
                break;
            }

        }
    }

    private String fullDisplay(){
        String output="";
        output+="Your info :\n";
        output+="username : "+currentUser.getUsername()+"\n";
        output+="nickname : "+currentUser.getNickname()+"\n";
        output+="email : "+currentUser.getEmail()+"\n";
        output+="highscore : "+currentUser.getHighScore()+"\n";
        output+="rank : "+currentUser.getRank()+"\n";
        output+="security question : "+currentUser.getSecurityQuestion()+"\n";
        output+="security question's answer : "+currentUser.getSecurityAnswer()+"\n";
        output+="slogan : ";
        if (currentUser.getSlogan()==null)
            output+="Slogan is empty !";
        else output+=currentUser.getSlogan();
        return output;

    }

    private String showSlogan(){
        if (currentUser.getSlogan()==null)
            return "Slogan is empty !";
        else return currentUser.getSlogan();
    }

    private String showHighscore(){
        return String.valueOf(currentUser.getHighScore());
    }

    private String showRank(){
        return String.valueOf(currentUser.getRank());
    }

    private String removeSlogan(){
        currentUser.setSlogan(null);
        return "Your slogan removed .";
    }

    public void changeChecker(Matcher matcher){

    }

    public void displayChecker(Matcher matcher){

    }


}
