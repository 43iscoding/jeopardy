package jeopardy;

import jeopardy.game.GameConfig;
import jeopardy.game.RoundConfig;
import jeopardy.game.ThemeConfig;
import jeopardy.game.ui.MainController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by XLIII on 2015-12-10.
 */
public class Launcher {

    public static Map<String, String> SKYPE_PLAYERS = new LinkedHashMap<String, String>() {{
        //STUPID BOTS
        //put("miss.gf54", "ZeGoattt");
        //put("miss.gf53", "BizBony");
        //put("miss.gf52", "MissCoookiez");

        // KRABE CHAT
        //put("sara_michael_pb", "KateiChg"); PLAYED
        //put("armen66642", "Армен");
        //put("sergleo_21213", "Сергей");
        //put("ihtiandrei", "IhtiAndrei");
        //put("phantomscadman", "Phantom");
        //put("dark_doctor", "Dark Doctor");
        //put("bogps123", "Oleg");
        //put("feakalakvendi", "Alexandr");
        //put("leinaps", "Leinaps");

        //put("quietguest123", "Nickolay"); PLAYED



        //MISC

        //put("lotivecsorks", "VitBuk"); //PLAYED
        //put("arsenicum91", "Shurara"); //PLAYED
        //put("angel_eyes57", "Dashutka"); //PLAYED
    }};

    public static Map<String, String> DISCORD_PLAYERS = new LinkedHashMap<String, String>() {{
        //STUPID BOTS
        //put("miss.gf54", "ZeGoattt");
        //put("miss.gf53", "BizBony");
        //put("miss.gf52", "MissCoookiez");

        // KRABE CHAT
        //put("sara_michael_pb", "KateiChg"); PLAYED
        //put("armen66642", "Армен");
        //put("sergleo_21213", "Сергей");
        //put("ihtiandrei", "IhtiAndrei");
        //put("phantomscadman", "Phantom");
        //put("dark_doctor", "Dark Doctor");
        //put("bogps123", "Oleg");
        //put("feakalakvendi", "Alexandr");
        //put("leinaps", "Leinaps");

        //put("quietguest123", "Nickolay"); PLAYED



        //MISC

        put("VitBuk", "VitBuk"); //PLAYED
        put("XLIII", "XLIII"); //PLAYED
        //put("angel_eyes57", "Dashutka"); //PLAYED
    }};

    public static GameConfig cfg = new GameConfig(
            //ROUND 1
            new ThemeConfig("CLASSICAL",
                    new RoundConfig("Moonlight Sonata", "MoonlightSonata"),
                    new RoundConfig("Flight Of The Bumblebee", "Bumblebee"),
                    new RoundConfig("Rondo Alla Turca (Turkish March)", "TurkishMarch"),
                    new RoundConfig("In The Hall Of The Mountain King", "MountainKing"),
                    new RoundConfig("Ride Of The Valkyries", "Valkyries")
            ),
            new ThemeConfig("TV SERIES",
                    new RoundConfig("Game Of Thrones", "GameOfThrones"),
                    new RoundConfig("Big Bang Theory", "BigBangTheory"),
                    new RoundConfig("House MD", "HouseMD"),
                    new RoundConfig("Doctor Who", "DoctorWho"),
                    new RoundConfig("Twin Peaks", "TwinPeaks")
            ),
            new ThemeConfig("COLORS",
                    new RoundConfig("Deep Purple", "Smoke On The Water", "DeepPurple").highlight("Purple"),
                    new RoundConfig("Beatles", "Yellow Submarine", "YellowSubmarine").highlight("Yellow"),
                    new RoundConfig("AC/DC", "Back In Black", "BackInBlack").highlight("Black"),
                    new RoundConfig("Pink Floyd", "Another Brick In The Wall", "PinkFloyd").highlight("Pink"),
                    new RoundConfig("Chris De Burgh", "Lady In Red", "LadyInRed").highlight("Red")
            ).containsTask("a color"),
            new ThemeConfig("DISNEY",
                    new RoundConfig("Frozen", "LetItGo"),
                    new RoundConfig("Lion King", "HakunaMatata"),
                    new RoundConfig("Aladdin", "Alladin"),
                    new RoundConfig("Mulan", "Mulan"),
                    new RoundConfig("Sleeping Beauty", "SleepingBeauty")
            ),
            new ThemeConfig("MOVIES",
                    new RoundConfig("Pirates Of The Carribean", "Pirates"),
                    new RoundConfig("Rocky", "Rocky"),
                    new RoundConfig("Requiem For A Dream", "Requiem"),
                    new RoundConfig("Terminator", "Terminator"),
                    new RoundConfig("Back To The Future", "BackToTheFuture")
            ),
            //ROUND 2
            new ThemeConfig("TV SERIES II",
                    new RoundConfig("X-Files", "XFiles"),
                    new RoundConfig("Friends", "Friends"),
                    new RoundConfig("Sherlock", "Sherlock"),
                    new RoundConfig("Breaking Bad", "BreakingBad"),
                    new RoundConfig("Firefly", "Firefly")
            ),
            new ThemeConfig("GAMES",
                    new RoundConfig("Super Mario Bros", "Mario"),
                    new RoundConfig("The Legend Of Zelda", "Zelda"),
                    new RoundConfig("Portal", "StillAlive"),
                    new RoundConfig("Doom", "Doom"),
                    new RoundConfig("Elder Scrolls III: Morrowind", "Morrowind")
            ),
            new ThemeConfig("NUMBERS",
                    new RoundConfig("The White Stripes", "Seven Nation Army", "SevenNationArmy").highlight("Seven"),
                    new RoundConfig("One Republic", "Counting Stars", "OneRepublic").highlight("One"),
                    new RoundConfig("Vanessa Carlton", "Thousand Miles", "ThousandMiles").highlight("Thousand"),
                    new RoundConfig("U2", "With Or Without You", "WithOrWithoutYou").highlight("2"),
                    new RoundConfig("Depeche Mode", "Little Fifteen", "LittleFifteen").highlight("Fifteen")
            ).containsTask("a number"),
            new ThemeConfig("NOTES",
                    new RoundConfig("Journey", "Don't Stop Believing", "DontStop").highlight("DO"),
                    new RoundConfig("Rihanna", "Rehab", "Rehab").highlight("RE"),
                    new RoundConfig("Beatles", "Michelle", "Michelle").highlight("MI"),
                    new RoundConfig("Metallica", "Fade To Black", "FadeToBlack").highlight("FA"),
                    new RoundConfig("Deep Purple", "Soldier Of Fortune", "SoldierOfFortune").highlight("SOL")
            ).containsTask("a note"),
            new ThemeConfig("MOVIES II",
                    new RoundConfig("Harry Potter", "HarryPotter"),
                    new RoundConfig("Titanic", "Titanic"),
                    new RoundConfig("Pulp Fiction", "PulpFiction"),
                    new RoundConfig("Matrix", "Matrix"),
                    new RoundConfig("Forrest Gump", "ForrestGump")
            ),
            //ROUND 3
            new ThemeConfig("CHEMICAL",
                    new RoundConfig("Nirvana", "Lithium", "Lithium").highlight("Lithium"),
                    new RoundConfig("Sting", "Fields Of Gold", "FieldsOfGold").highlight("Gold"),
                    new RoundConfig("David Guetta feat Sia", "Titanium", "Titanium").highlight("Titanium"),
                    new RoundConfig("Nickelback", "Hero", "Nickelback").highlight("Nickel"),
                    new RoundConfig("Black Sabbath", "Iron Man", "IronMan").highlight("Iron")
            ),
            new ThemeConfig("MOVIES III",
                    new RoundConfig("Ghostbusters", "Ghostbusters"),
                    new RoundConfig("Godfather", "Godfather"),
                    new RoundConfig("Ghost", "Ghost"),
                    new RoundConfig("Inception", "Inception"),
                    new RoundConfig("Star Wars: Episode V - The Empire Strikes Back", "StarWars")
            ),
            new ThemeConfig("GAMES II",
                    new RoundConfig("Tetris", "Tetris"),
                    new RoundConfig("Chrono Trigger", "ChronoTrigger"),
                    new RoundConfig("Diablo", "Diablo"),
                    new RoundConfig("Final Fantasy X", "FinalFantasy"),
                    new RoundConfig("Starcraft", "Starcraft")
            ),
            new ThemeConfig("ANIMALS",
                    new RoundConfig("The Eagles", "Hotel California", "HotelCalifornia").highlight("Eagles"),
                    new RoundConfig("Survivor", "Eye Of The Tiger", "EyeOfTheTiger").highlight("Tiger"),
                    new RoundConfig("David Guetta feat Sia", "She Wolf", "SheWolf").highlight("Wolf"),
                    new RoundConfig("Katy Perry", "Dark Horse", "DarkHorse").highlight("Horse"),
                    new RoundConfig("Seal", "Kiss From A Rose", "KissFromARose").highlight("Seal")
            ).containsTask("animal"),
            new ThemeConfig("WEATHER",
                    new RoundConfig("AC/DC", "Thunderstruck", "Thunderstruck").highlight("Thunder"),
                    new RoundConfig("Boney M.", "Sunny", "Sunny").highlight("Sunny"),
                    new RoundConfig("A-ha", "Crying In The Rain", "CryingInTheRain").highlight("Rain"),
                    new RoundConfig("30 Seconds To Mars", "Hurricane", "Hurricane").highlight("Hurricane"),
                    new RoundConfig("Darude", "Sandstorm", "Sandstorm").highlight("Sandstorm")
            ).containsTask("a weather condition")
    );

    public static void main(String[] args) {
        new MainController();
    }
}
