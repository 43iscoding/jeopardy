package jeopardy.volume;

import jeopardy.game.config.GameConfig;
import jeopardy.game.config.IntroConfig;
import jeopardy.game.config.RoundConfig;
import jeopardy.game.config.ThemeConfig;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 8/14/16
 * Time: 3:06 PM
 */
public class NameThatTuna1 extends GameConfig {

    public NameThatTuna1() {
        super(
            new IntroConfig("In what year was the national anthem of Latvia written?", 1873),
            //ROUND 1
            new ThemeConfig("CLASSICAL",
                    new RoundConfig("Moonlight Sonata", "MoonlightSonata"), //0
                    new RoundConfig("Flight Of The Bumblebee", "Bumblebee"), //0
                    new RoundConfig("Rondo Alla Turca (Turkish March)", "TurkishMarch").volume(-4),
                    new RoundConfig("In The Hall Of The Mountain King", "MountainKing").volume(+2),
                    new RoundConfig("Ride Of The Valkyries", "Valkyries").volume(-5)
            ),
            new ThemeConfig("TV SERIES",
                    new RoundConfig("Game Of Thrones", "GameOfThrones").volume(-9),
                    new RoundConfig("Big Bang Theory", "BigBangTheory").volume(-12),
                    new RoundConfig("House MD", "HouseMD").volume(-9),
                    new RoundConfig("Doctor Who", "DoctorWho").volume(-10),
                    new RoundConfig("Twin Peaks", "TwinPeaks").volume(-3)
            ),
            new ThemeConfig("COLORS",
                    new RoundConfig("Deep Purple", "Smoke On The Water", "DeepPurple").highlight("Purple").volume(-9),
                    new RoundConfig("Beatles", "Yellow Submarine", "YellowSubmarine").highlight("Yellow").volume(-5),
                    new RoundConfig("AC/DC", "Back In Black", "BackInBlack").highlight("Black").volume(-10),
                    new RoundConfig("Pink Floyd", "Another Brick In The Wall", "PinkFloyd").highlight("Pink").volume(-3),
                    new RoundConfig("Chris De Burgh", "Lady In Red", "LadyInRed").highlight("Red").volume(-9)
            ).containsTask("a color"),
            new ThemeConfig("DISNEY",
                    new RoundConfig("Frozen", "LetItGo"),
                    new RoundConfig("Lion King", "HakunaMatata").volume(-1),
                    new RoundConfig("Aladdin", "Alladin").volume(+2),
                    new RoundConfig("Mulan", "Mulan").volume(-1),
                    new RoundConfig("Sleeping Beauty", "SleepingBeauty").volume(-4)
            ),
            new ThemeConfig("MOVIES",
                    new RoundConfig("Pirates Of The Carribean", "Pirates").volume(-7),
                    new RoundConfig("Rocky", "Rocky").volume(-4),
                    new RoundConfig("Requiem For A Dream", "Requiem"),
                    new RoundConfig("Terminator", "Terminator").volume(-3),
                    new RoundConfig("Back To The Future", "BackToTheFuture").volume(-4)
            ),
            //ROUND 2
            new ThemeConfig("TV SERIES II",
                    new RoundConfig("X-Files", "XFiles").volume(-6),
                    new RoundConfig("Friends", "Friends").volume(-8),
                    new RoundConfig("Sherlock", "Sherlock").volume(-10),
                    new RoundConfig("Breaking Bad", "BreakingBad").volume(-4),
                    new RoundConfig("Firefly", "Firefly").volume(-5)
            ),
            new ThemeConfig("GAMES",
                    new RoundConfig("Super Mario Bros", "Mario").volume(-3),
                    new RoundConfig("The Legend Of Zelda", "Zelda").volume(-7),
                    new RoundConfig("Portal", "StillAlive").volume(-3),
                    new RoundConfig("Doom", "Doom").volume(-8),
                    new RoundConfig("Elder Scrolls III: Morrowind", "Morrowind").volume(-2)
            ),
            new ThemeConfig("NUMBERS",
                    new RoundConfig("The White Stripes", "Seven Nation Army", "SevenNationArmy").highlight("Seven").volume(-6),
                    new RoundConfig("One Republic", "Counting Stars", "OneRepublic").highlight("One").volume(-7),
                    new RoundConfig("Vanessa Carlton", "Thousand Miles", "ThousandMiles").highlight("Thousand").volume(-3),
                    new RoundConfig("U2", "With Or Without You", "WithOrWithoutYou").highlight("2").volume(-1),
                    new RoundConfig("Depeche Mode", "Little Fifteen", "LittleFifteen").highlight("Fifteen").volume(-6)
            ).containsTask("a number"),
            new ThemeConfig("NOTES",
                    new RoundConfig("Journey", "Don't Stop Believing", "DontStop").highlight("DO").volume(-1),
                    new RoundConfig("Rihanna", "Rehab", "Rehab").highlight("RE").volume(-4),
                    new RoundConfig("Beatles", "Michelle", "Michelle").highlight("MI").volume(-7),
                    new RoundConfig("Metallica", "Fade To Black", "FadeToBlack").highlight("FA").volume(-4),
                    new RoundConfig("Deep Purple", "Soldier Of Fortune", "SoldierOfFortune").highlight("SOL").volume(-3)
            ).containsTask("a note"),
            new ThemeConfig("MOVIES II",
                    new RoundConfig("Harry Potter", "HarryPotter").volume(+2),
                    new RoundConfig("Titanic", "Titanic").volume(-3),
                    new RoundConfig("Pulp Fiction", "PulpFiction").volume(-8),
                    new RoundConfig("Matrix", "Matrix").volume(-6),
                    new RoundConfig("Forrest Gump", "ForrestGump")
            ),
            //ROUND 3
            new ThemeConfig("CHEMICAL",
                    new RoundConfig("Nirvana", "Lithium", "Lithium").highlight("Lithium").volume(-2),
                    new RoundConfig("Sting", "Fields Of Gold", "FieldsOfGold").highlight("Gold").volume(-4),
                    new RoundConfig("David Guetta feat Sia", "Titanium", "Titanium").highlight("Titanium").volume(-13),
                    new RoundConfig("Nickelback", "Hero", "Nickelback").highlight("Nickel").volume(-11),
                    new RoundConfig("Black Sabbath", "Iron Man", "IronMan").highlight("Iron").volume(+1)
            ),
            new ThemeConfig("MOVIES III",
                    new RoundConfig("Ghostbusters", "Ghostbusters").volume(-9),
                    new RoundConfig("Godfather", "Godfather").volume(-5),
                    new RoundConfig("Ghost", "Ghost").volume(-4),
                    new RoundConfig("Inception", "Inception").volume(-1),
                    new RoundConfig("Star Wars: Episode V - The Empire Strikes Back", "StarWars").volume(-2)
            ),
            new ThemeConfig("GAMES II",
                    new RoundConfig("Tetris", "Tetris").volume(-11),
                    new RoundConfig("Chrono Trigger", "ChronoTrigger").volume(-4),
                    new RoundConfig("Diablo", "Diablo").volume(-7),
                    new RoundConfig("Final Fantasy X", "FinalFantasy").volume(-4),
                    new RoundConfig("Starcraft", "Starcraft").volume(-6)
            ),
            new ThemeConfig("ANIMALS",
                    new RoundConfig("The Eagles", "Hotel California", "HotelCalifornia").highlight("Eagles").volume(-2),
                    new RoundConfig("Survivor", "Eye Of The Tiger", "EyeOfTheTiger").highlight("Tiger").volume(-7),
                    new RoundConfig("David Guetta feat Sia", "She Wolf", "SheWolf").highlight("Wolf").volume(-12),
                    new RoundConfig("Katy Perry", "Dark Horse", "DarkHorse").highlight("Horse").volume(-7),
                    new RoundConfig("Seal", "Kiss From A Rose", "KissFromARose").highlight("Seal").volume(-5)
            ).containsTask("animal"),
            new ThemeConfig("WEATHER",
                    new RoundConfig("AC/DC", "Thunderstruck", "Thunderstruck").highlight("Thunder").volume(-3),
                    new RoundConfig("Boney M.", "Sunny", "Sunny").highlight("Sunny").volume(-12),
                    new RoundConfig("A-ha", "Crying In The Rain", "CryingInTheRain").highlight("Rain").volume(-8),
                    new RoundConfig("30 Seconds To Mars", "Hurricane", "Hurricane").highlight("Hurricane").volume(-5),
                    new RoundConfig("Darude", "Sandstorm", "Sandstorm").highlight("Sandstorm").volume(+2)
            ).containsTask("a weather condition"));
    }
}
